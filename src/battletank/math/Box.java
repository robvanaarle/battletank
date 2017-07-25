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
            (y+radius) <= y1 ||
            (y-radius) >= y2
        );
    }
    
    public void clip(Circle circle) {
        double x = circle.getLocation().getX();
        double y = circle.getLocation().getY();
        double radius = circle.getRadius();
        
        x = java.lang.Math.max(x, x1+radius);
        x = java.lang.Math.min(x, x2-radius);
        y = java.lang.Math.max(y, y1+radius);
        y = java.lang.Math.min(y, y2-radius);
        
        circle.setLocation(new Point2D(x, y));
    }
}
