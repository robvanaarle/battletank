/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author raarle
 */
public class Shell extends Object implements battletank.math.Circle {
    protected Tank origin;
    protected double speed = 4;
    protected double width = 10;
    
    public Shell(Tank origin) {
        this.origin = origin;
        this.setHeading(origin.getHeading());
        this.setLocation(origin.getLocation());
        this.getLocation().move(origin.getWidth()/2.0 + this.getWidth()/2.0, origin.heading);
        
        origin.getArena().addObject(this);
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
        
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.red);
        
        int x = (int)Math.round(this.getLocation().getX());
        int y = (int)Math.round(this.getLocation().getY());
        int width = (int)Math.round(this.getWidth());
        
        g2.fillOval(x - width/2, y - width/2, width, width);
    }
}
