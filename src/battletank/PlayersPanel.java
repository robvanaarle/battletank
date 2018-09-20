package battletank;

import battletank.math.Point2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import battletank.objects.Object;
import battletank.objects.*;

public class PlayersPanel extends JPanel {
    protected Arena arena;
    protected Frame frame;
    
    protected int padding = 8;
    
    protected ArrayList<Player> players = new ArrayList<>();
    protected ArrayList<Tank> playerTanks = new ArrayList<>();
    
    public PlayersPanel(Arena arena) {
        this.arena = arena;
        
        // find players
        Object[] tanks = arena.getObjects(battletank.objects.Tank.class);
        for (Object tank: tanks) {
            this.players.add(((Tank)tank).getPlayer());
            this.playerTanks.add((Tank)tank);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.frame == null) {
            return;
        }
        
        // fill background
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        // Save the next instance of each tank. 
        for (int i = 0; i < this.playerTanks.size(); i++) {
            if (this.playerTanks.get(i).getNext() != null) {
                this.playerTanks.set(i, (Tank)this.playerTanks.get(i).getNext());
            }
        }
        
        // update player tanks with the tank in frame. If a tank has been
        // removed, the last known instance is used in the overview
        for (Object tank : this.frame.getObjects(Tank.class)) {
            int index = this.players.indexOf(((Tank)tank).getPlayer());
            this.playerTanks.set(index, (Tank)tank);
        }
        
        // paint each player's tank, name and damage
        for (int i = 0; i < this.players.size(); i++) {
            Player player = this.players.get(i);
            Tank tank = this.playerTanks.get(i);
            
            // paint the tank
            int blockHeight = 2 * (this.padding + (int)Math.ceil(tank.getRadius()));
            
            int tankX = this.padding + (int)Math.ceil(tank.getRadius());
            int tankY = (i * blockHeight) + (this.padding + (int)Math.ceil(tank.getRadius()));
            Point2D location = tank.getLocation();
            tank.setLocation(new Point2D(tankX, tankY));
            tank.paint(g);
            tank.setLocation(location);
            
            // draw player name
            g.setColor(Color.BLACK);
            g.drawString(player.getName(), 2*(this.padding + (int)Math.ceil(tank.getRadius())), i*blockHeight + 20);
            
            // draw health
            int health = 100 - ((tank.getDamage() * 100)) / tank.getMaxDamage();
            g.drawString("health: " + health + "%", 2*(this.padding + (int)Math.ceil(tank.getRadius())), i*blockHeight + 40);
        }
    }
    
    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
