/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.objects;

import battletank.math.Point2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author raarle
 */
public class Tank extends Object implements battletank.math.Circle {
    protected String name;
    protected double width = 40;
    protected double maxMoveSpeed = 1;
    protected double maxTurnSpeed = 0.1;
    protected ArrayList<Shell> shells = new ArrayList<>();
    
    public Tank(String name) {
        this.setName(name);
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void shoot() {
        this.shells.add(new Shell(this));
    }
    
    public double getWidth() {
        return this.width;
    }
    
    @Override
    public double getRadius() {
        return this.getWidth()/2.0;
    }
    
    public double getMaxMoveSpeed() {
        return this.maxMoveSpeed;
    }
    
    public double getMaxTurnSpeed() {
        return this.maxTurnSpeed;
    }
    
    public ArrayList<Shell> getShells() {
        return this.shells;
    }
    
    @Override
    public void move(double distance, double heading) {
        // respect max move speed
        if (distance > 0) {
            distance = Math.min(distance, this.getMaxMoveSpeed());
        } else {
            distance = Math.max(distance, -this.getMaxMoveSpeed());
        }
        
        // respect max turn speed
        double headingDiff = normalizeHeading(heading - this.getHeading());
        if (headingDiff > Math.PI) {
            headingDiff -= battletank.math.Math.DOUBLE_PI;
            headingDiff = Math.max(headingDiff, -this.getMaxTurnSpeed());
        } else {
            headingDiff = Math.min(headingDiff, this.getMaxTurnSpeed());
        }
        
        heading = this.getHeading() + headingDiff;
        
        // set new heading and move in that direction
        this.setHeading(heading);
        super.move(distance, heading);
        
        // clip against the arena
        this.getArena().toBox().clip(this);
    }
    
    private int i;
    @Override
    public void tick() {
        i++;
        
        double headingDiff = 0;
        int frame = i % 200;
        if (frame >= 190 && frame < 200) {
            headingDiff = this.maxTurnSpeed;
        }
        
        //double headingDiff = this.maxTurnSpeed;
        //headingDiff -= (i%1000) * (this.maxTurnSpeed/200.0);
        
        this.move(1, this.heading+headingDiff);
        if (this.shells.size() == 0) {
            this.shoot();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        
        int x = (int)Math.round(this.getLocation().getX());
        int y = (int)Math.round(this.getLocation().getY());
        int width = (int)Math.round(this.getWidth());
        
        // vehicle
        g2.setColor(Color.cyan);
        g2.fillOval(x - width/2, y - width/2, width, width);
        
        // turret
        width = width/2;
        g2.setColor(Color.blue);
        g2.fillOval(x - width/2, y - width/2, width, width);
        
        //tube
        Point2D tubeEnd = new Point2D(x, y);
        tubeEnd.move(width * 4 / 3, this.getHeading());
        int tx = (int)Math.round(tubeEnd.getX());
        int ty = (int)Math.round(tubeEnd.getY());
        g2.setStroke(new BasicStroke(width/3));
        g2.drawLine(x, y, tx, ty);
    }
}
