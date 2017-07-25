/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.math;

/**
 *
 * @author raarle
 */
public class Point2D {
    protected double x;
    protected double y;
    
    public Point2D() {
        
    }
    
    public Point2D(double x, double y) {
        this.setLocation(x, y);
    }
    
    public Point2D(Point2D point) {
        this.setLocation(point);
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setLocation(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    
    public double distance(double x, double y) {
        return distance(this.getX(), this.getY(), x, y);
    }
    
    public double distance(Point2D point) {
        return distance(this.getX(), this.getY(), point.getX(), point.getY());
    }
    
    static double distance(double x1, double y1, double x2, double y2) {
        x2 -= x1;
        y2 -= y1;
        return java.lang.Math.sqrt(x2 * x2 + y2 * y2);
    }
    
    public void move(double distance, double angle) {
        this.setX(this.getX() + (distance * java.lang.Math.cos(angle)));
        this.setY(this.getY() + (distance * java.lang.Math.sin(angle)));
    }
    
    @Override
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
}
