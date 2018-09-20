package battletank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ArenaPanel extends JPanel {
    protected Arena arena;
    protected Frame frame;
    
    public ArenaPanel(Arena arena) {
        this.arena = arena;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(0, 0, arena.getWidth(), arena.getHeight());
        
        if (this.frame != null) {
            for (battletank.objects.Object object : this.frame.getObjects()) {
                object.paint(g);
            }
        }
    }
    
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
