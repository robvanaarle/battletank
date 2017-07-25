package battletank;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

public class Panel extends JPanel {
    protected Arena arena;
    
    public Panel(Arena arena) {
        this.arena = arena;
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(0, 0, arena.getWidth(), arena.getHeight());
        
        battletank.objects.Object[] objects = this.arena.getObjects();
        for (int i = 0; i < objects.length; i++) {
            objects[i].paint(g);
        }
        
        //

        //Line2D line = new Line2D.Double(-10, -10, 600, 600);
        //
        //g2.setStroke(new BasicStroke(10));
        //g2.draw(line);
     }
}
