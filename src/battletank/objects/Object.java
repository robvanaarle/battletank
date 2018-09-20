package battletank.objects;

import battletank.Arena;
import battletank.math.Point2D;
import java.awt.Graphics;

abstract public class Object {
    protected Point2D location;
    protected double heading;
    protected Arena arena;
    
    protected Object previous = null;
    protected Object next = null;
    
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
    
    public Object getPrevious() {
        return this.previous;
    }
    
    public Object getNext() {
        return this.next;
    }
    
    public Object next() {
        Object copy = this.copy();
        copy.setArena(this.getArena());
        copy.setLocation(new Point2D(this.getLocation()));
        copy.setHeading(this.getHeading());
        
        copy.previous = this;
        this.next = copy;
        return copy;
    }
    
    abstract public void tick();
    
    abstract public void paint(Graphics g);
    
    abstract protected battletank.objects.Object copy();
    
    public void destroy() {
        if (this.previous != null) {
            this.previous.next = null;
        }
        
        if (this.next != null) {
            this.next.previous = null;
        }
    }
    
    @Override
    public String toString() {
        return "[loc:" + this.getLocation().toString() + ", h:" + this.getHeading() + "]";
    }
}
