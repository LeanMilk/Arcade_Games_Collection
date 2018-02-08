import java.awt.*;
import java.awt.event.*;

interface World {
  public void draw(Graphics g);
  public void update();
  public boolean hasEnded();
  public void mouseClicked(MouseEvent e);
  public void mouseMoved(MouseEvent e);
}
