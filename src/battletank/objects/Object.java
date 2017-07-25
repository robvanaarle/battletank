package battletank.objects;

import battletank.Arena;
import battletank.math.Point2D;
import java.awt.Graphics;

abstract public class Object {
    protected Point2D location;
    protected double heading;
    protected Arena arena;
    
    public Object() {
        this.location = new Point2D();
        this.heading = 0;
    }

    public Arena getArena() {
        return this.arena;
    }
    
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    
    public void setLocation(Point2D location) {
        this.location = new Point2D(location);
    }
    
    public Point2D getLocation() {
        return this.location;
    }
    
    public void setHeading(double heading) {
        this.heading = normalizeHeading(heading);
    }
    
    static public double normalizeHeading(double heading) {
        while(heading < 0) {
            heading += battletank.math.Math.DOUBLE_PI;
        }
        
        while (heading > battletank.math.Math.DOUBLE_PI) {
            heading -= battletank.math.Math.DOUBLE_PI;
        }
        
        return heading;
    } 
    
    public double getHeading() {
        return this.heading;
    }
    
    public void move(double distance, double heading) {
        this.getLocation().move(distance, heading);
    }
    
    abstract public void tick();
    
    abstract public void paint(Graphics g);
    
    @Override
    public String toString() {
        return "[loc:" + this.getLocation().toString() + ", h:" + this.getHeading() + "]";
    }
}
