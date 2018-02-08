import java.awt.*;
import java.util.*;

public class Circle {
  boolean safe;
  boolean zap;
  boolean original = true;
  double x, y, dx, dy;
  int n, k;
  int radius = 30;
  Color color;

  public Circle(double x, double y, int k) {
    this.x = x;     // x-coor
    this.y = y;     // y-coor
    this.k = k;     // represents color
  }
  public void draw(Graphics g) {
    g.setColor(this.color);
    g.fillOval((int)(x - 30), (int)(y - 30), 60, 60);
    g.setColor(Color.BLACK);
    g.drawOval((int)(x - 30), (int)(y - 30), 60, 60);
    if (y <= 30) {
      g.setColor(Color.WHITE);
    } else {
      g.setColor(Color.BLACK);
    }
    g.drawString(""+n, (int) (x-3), (int) (y));
    if (this.original == true) {
      g.drawString("T", (int) (x-3), (int) (y+12));
    } else {
      g.drawString("F", (int) (x-3), (int) (y+12));
    }
    
    if (this.zap == true) {
      g.drawString("T", (int) (x+12), (int) (y+12));
    } else {
      g.drawString("F", (int) (x+12), (int) (y+12));
    }
    
  }
  public void move() {
    this.x += dx;
    this.y += dy;
    if (this.x - 30 < 0) {
      this.dx *= -1;
    }
    if (this.x + 30 > 600) {
      this.dx *= -1;
    }
  }
  
  public void stop() {
    this.dx = 0;
    this.dy = 0;
  }
  
  public double distanceTo(Circle c) {
    return Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
  }
  
  public boolean hit(Circle c) {
    return this.distanceTo(c) <= 50;
  }
  
  public void giveColor() {
    if (this.k == 0) {color = Color.RED;}
    if (this.k == 1) {color = Color.YELLOW;}
    if (this.k == 2) {color = Color.BLUE;}
    if (this.k == 3) {color = Color.GREEN;}
  }
  
  
  public void newSpot(Point p) {
    if (Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2)) <= 31) {
    this.x = p.x;
    this.y = p.y;
    }
  }
  
  public void neighbors(ArrayList<Circle> circles) {
    this.n = 0;
    for (Circle c : circles) {
      if (c != this && c.distanceTo(this) <= 65 && c.k == this.k) {
        this.n += 1;
        if (this.original == false || this.original == false)
          c.original = this.original = false;
      }

    }
  }
  
}