package battletank.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Shell extends Object implements battletank.math.Circle {
    protected Tank origin;
    protected double speed = 4;
    protected double width = 10;
    protected int damage = 1;
    
    public Shell(Tank origin) {
        this.origin = origin;
        this.setHeading(origin.getHeading());
        this.setLocation(origin.getLocation());
        this.getLocation().move(origin.getWidth()/2.0 + this.getWidth()/2.0, origin.heading);
    }
    
    public Tank getOrigin() {
        return this.origin;
    }
    
    public double getSpeed() {
        return this.speed;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    @Override
    public double getRadius() {
        return this.getWidth()/2.0;
    }
    
    public void delete() {
        this.getOrigin().getShells().remove(this);
        this.getArena().removeObject(this);
    }
    
    @Override
    public void tick() {
        // move to new location
        this.move(this.getSpeed(), this.getHeading());
        
        // check if shell is in playfield
        if (this.getArena().toBox().isOutside(this)) {
            this.delete();
        }
        
        // check if shell hit a tank
        Object[] tanks = arena.getObjects(Tank.class);
        for (Object object : tanks) {
            Tank tank = (Tank) object;
            if (tank.getLocation().distance(this.getLocation()) <= (this.getRadius() + tank.getRadius())) {
                // hit tank
                tank.hit(this.damage);
                
                //remove shell
                this.delete();
            }
        }
        
        
    }
    
    @Override
    protected battletank.objects.Object copy() {
        Tank origin = (Tank)this.getOrigin().getNext();
        if (origin == null) {
            origin = (Tank)this.getOrigin();
        }
        
        
        Shell copy = new Shell(origin);
        origin.shells.add(copy);
        return copy;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        int x = (int)Math.round(this.getLocation().getX());
        int y = (int)Math.round(this.getLocation().getY());
        
        // base
        g2.setColor(this.getOrigin().getPlayer().getPrimaryColor());
        int width = (int)Math.round(this.getWidth());
        g2.fillOval(x - width/2, y - width/2, width, width);
        
        // inside
        g2.setColor(this.getOrigin().getPlayer().getSecondaryColor());
        width = (int)Math.round(this.getWidth() * 0.6);
        g2.fillOval(x - width/2, y - width/2, width, width);
    }
}
