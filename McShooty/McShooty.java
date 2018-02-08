import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class McShooty implements World {
  Circle bullet, shadow;
  Launcher launcher;
  ArrayList<Circle> circles;
  ArrayList<Point> points;
  int tiktok;
  
  public McShooty() {
    
    this.points = new ArrayList<Point>();
    for (int j = 0; j < 17; j++) {
      for (int i = 0; i < 10; i++) {
        this.points.add(new Point(45 - 15*(Math.pow(-1, j)) + i * 60,
                                  30 + Math.sqrt(2700) * j));
      }
    }
    
    
    this.bullet = new Circle(315, 900, (int) (Math.random()*4));
    this.bullet.giveColor();
    this.bullet.original = false;
    
    this.circles = new ArrayList<Circle>();
//    for (int p = 0; p < 4; p++) {
  //    for (int g = 0; g < 10; g++) {
    //    this.circles.add(new Circle(45 - 15*(Math.pow(-1, p)) + g * 60,
      //                              30 + Math.sqrt(2700) * p,
      //                              0, 0, 0, (int) (Math.random()*4)));
    //  }
  //  }
    
    this.launcher = new Launcher();
    this.shadow = makeGhost();
    
  }
  
  
  public void draw(Graphics g) {
    this.bullet.draw(g);
    this.shadow.draw(g);
    for (Circle c : this.circles) {
      c.giveColor();
      c.draw(g);
    }
    this.launcher.draw(g);
    g.drawLine(0, 810, 630, 810);
    g.setColor(Color.WHITE);
    g.fillOval((int) (this.shadow.x - 28),
               (int) (this.shadow.y - 28),
               56, 56);
    g.setColor(Color.BLACK);
    g.drawRect(0, 0, 630, 900);
  }
  public void update() {
    if (this.landed(this.bullet)) {
      this.touchdown();
    }
    else
      this.bullet.move();
  }
  
  public boolean hasEnded() {
    return false;
  }
  
  public void mouseMoved(MouseEvent e) {
    double x = e.getX(), y = e.getY();
    if (y <= 960) {
      if (x < 322.5) {
        this.launcher.angle = Math.atan((960 - y)/(x - 322.5)) + (Math.PI);
      }
      else {
        this.launcher.angle = Math.atan((960 - y)/(x - 322.5));
      }
    }
    else {
    }
    this.shadow = makeGhost();
  }
  
  public void mouseClicked(MouseEvent e) {
    if (tiktok == 0) {
      this.launcher.biubiu(bullet);
      tiktok = 1;
    }
    else {
      tiktok = 1;
    }
  }
  
  public static void main(String[] args) {
    BigBang game = new BigBang(1, new McShooty());
    JFrame frame = new JFrame("Bob McShooty v3.1");
    frame.getContentPane().add( game );
    frame.addMouseMotionListener( game );
    frame.addMouseListener( game );
    frame.setVisible(true);
    frame.setSize(660, 990);
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    game.start();
  }
  
  void touchdown() {
    this.bullet.stop();
    this.moveTo(this.bullet);
    this.circles.add(this.bullet);
    for (Circle c : this.circles)
      c.neighbors(this.circles);
    this.purgeOne();
    this.purgeTwo();
    this.bullet = new Circle(315, 900, (int)(Math.random()*4));
    this.bullet.giveColor();
    this.bullet.original = false;
    this.shadow = makeGhost();
    tiktok = 0;
  }
  
  boolean hit(Circle cc) {
    for (Circle c : this.circles) {
      if (!cc.hit(c)) continue;
      if (cc.k == c.k) {
        c.original = false;
      }
      return true;
    }
    return false;
  }
  
  void moveTo(Circle c) {
    for (Point p : this.points) {
      c.newSpot(p);
    }
  }
  
  boolean landed(Circle c) {
    return (c.y <= 30) || hit(c);
  }
  
  
  public void purgeOne() {
    for (Circle c : this.circles)
      c.zap = false; 
    ArrayList<Circle> result = new ArrayList<Circle>(); 
    for (Circle c : this.circles)
      if (c.n >= 2 && c.original == false)
        c.zap = true;
    for (Circle c1 : circles)
      for (Circle c2 : circles)
        if (c1.distanceTo(c2) <= 65 && c1.k == c2.k)
          if (c1.zap || c2.zap) 
            c1.zap = c2.zap = true; 
    for (Circle c : this.circles)
      if (!c.zap)
        result.add(c); 
    this.circles = result;
  }
  
  public void purgeTwo() {
    for (Circle c : this.circles)
      c.safe = false;
    ArrayList<Circle> result = new ArrayList<Circle>();
    for (Circle c : this.circles)
      if (c.y <= 35) // connected to the ceiling
        c.safe = true;
    for (Circle c1 : circles)
      for (Circle c2 : circles)
        if (c1.distanceTo(c2) <= 65)
          if (c1.safe || c2.safe)
            c1.safe = c2.safe = true;
    for (Circle c : this.circles)
      if (c.safe)
        result.add(c);
    this.circles = result;
  }
  
  public Circle makeGhost() {
    Circle ghost = new Circle(315, 900, this.bullet.k);
    
    ghost.giveColor();
    
    this.launcher.biubiu(ghost);
    
    while ( !landed(ghost) ) {
      ghost.move();
    }
    
    moveTo(ghost);
    
    
    return ghost;
  }
  
  
}
