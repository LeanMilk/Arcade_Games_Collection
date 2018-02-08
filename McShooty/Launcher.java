import java.awt.*;

public class Launcher {
  double angle = Math.PI/2;
  public void draw(Graphics g) {
    g.setColor(Color.BLACK);
    g.drawOval(315 - 90, 900 - 90, 180, 180);
    int x = (int) (315 + Math.cos(angle) * 90);
    int y = (int) (900 - Math.sin(angle) * 90);
    g.fillOval(x - 5, y - 5, 10, 10);
    g.drawLine((int) (315 + Math.cos(angle) * 600), (int) (900 - Math.sin(angle) * 600), 315, 900);
  }
  public void biubiu(Circle c) {
    c.dx = Math.cos(angle) * 8;
    c.dy = -Math.sin(angle) * 8;
  }
  
}