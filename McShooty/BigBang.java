import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class BigBang extends JComponent 
  implements ActionListener, MouseListener, MouseMotionListener {
  Timer timer;
  World world;
  BigBang(int delay, World world) {
    timer = new Timer(delay, this);
    this.world = world;
  }
  public void start() {
    timer.start();
  }
  BigBang(World world) {
    this(1000, world);
  }
  public void paintComponent(Graphics g) {
    world.draw(g);
  }
  public void actionPerformed(ActionEvent e) {
    world.update();
    if (world.hasEnded())
      timer.stop();
    this.repaint();
  }
  public void keyPressed(KeyEvent e) { }
  public void keyTyped(KeyEvent e) { }
  public void keyReleased(KeyEvent e) { }
  
  public void mouseMoved(MouseEvent e) {
    world.mouseMoved(e);
    this.repaint();
  }
  public void mouseDragged(MouseEvent e) { }
  
  public void mouseEntered(MouseEvent e) { }
  public void mouseExited(MouseEvent e) { }
  public void mousePressed(MouseEvent e) { }
  public void mouseReleased(MouseEvent e) { }
  public void mouseClicked(MouseEvent e) {
    world.mouseClicked(e);
    this.repaint();
  }
  
}
