package battletank.math;


public class Box {
    protected double x1, x2, y1, y2;
    
    public Box(double x1, double y1, double x2, double y2) {
        this.x1 = java.lang.Math.min(x1, x2);
        this.x2 = java.lang.Math.max(x1, x2);
        this.y1 = java.lang.Math.min(y1, y2);
        this.y2 = java.lang.Math.max(y1, y2);
    }
    
    public boolean isOutside(Circle circle) {
        double x = circle.getLocation().getX();
        double y = circle.getLocation().getY();
        double radius = circle.getRadius();
        
        return (
            (x+radius) < x1 ||
            (x-radius) > x2 ||
            (y+radius) < y1 ||
            (y-radius) > y2
        );
    }
    
    public void collisionDetectInside(Circle circle) {
        double x = circle.getLocation().getX();
        double y = circle.getLocation().getY();
        double radius = circle.getRadius();
        
        x = java.lang.Math.max(x, x1+radius);
        x = java.lang.Math.min(x, x2-radius);
        y = java.lang.Math.max(y, y1+radius);
        y = java.lang.Math.min(y, y2-radius);
        
        circle.setLocation(new Point2D(x, y));
    }
    
    public void collisionDetectOutside(Point2D oldLocation, Circle circle) {
       
        if (this.isOutside(circle)) {
            return;
        }
        
        double oldX = oldLocation.getX();
        double oldY = oldLocation.getY();
        double newX = circle.getLocation().getX();
        double newY = circle.getLocation().getY();
        double radius = circle.getRadius();

        
        double x = newX;
        double y = newY;
        
        
        
        if (oldX <= (x1-radius) && newX >= (x1-radius)) {
            x = x1-radius;
        } else if (oldX >= (x2+radius) && newX <= (x2+radius)) {
            x = x2+radius;
        }
       
        if (oldY <= (y1-radius) && newY >= (y1-radius)) {
            y = y1-radius;
        } else if (oldY >= (y2+radius) && newY <= (y2+radius)) {
            y = y2+radius;
        }

        
        circle.setLocation(new Point2D(x, y));
    }
}
