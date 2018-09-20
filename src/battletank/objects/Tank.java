package battletank.objects;

import battletank.Player;
import battletank.math.Point2D;
import battletank.tankai.TankAI;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Tank extends Object implements battletank.math.Circle {
    
    protected double width = 40;
    protected double maxMoveSpeed = 1;
    protected double maxTurnSpeed = 0.1;
    protected int maxDamage = 200;
    protected int maxShellCount = 2;
    protected int reloadTicks = 20;
    
    protected Player player;
    protected ArrayList<Shell> shells = new ArrayList<>();
    protected int damage = 0;
    protected TankAI ai;
    protected int reloadTimeout = 0;
    
    public Tank(Player player, TankAI ai) {
        this.player = player;
        this.ai = ai;
    }
    
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public TankAI getTankAI() {
        return this.ai;
    }
    
    public void shoot() {
        
        // don't shoot when the maximum number of shells has been reached
        if (this.shells.size() >= this.maxShellCount) {
            return;
        }
        
        if (this.reloadTimeout > 0) {
            return;
        }
        
        
        Shell shell = new Shell(this);
        this.shells.add(shell);
        this.getArena().addObject(shell);
        this.reloadTimeout = reloadTicks;
    }
    
    public void hit(int damage) {
        this.damage += damage;
        
        if (this.damage >= this.maxDamage) {
            this.delete();
        }
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
    
    public int getDamage() {
        return this.damage;
    }
    
    public int getMaxDamage() {
        return this.maxDamage;
    }
    
    public int getMaxShellCount() {
        return this.maxShellCount;
    }
    
    public int getReloadTicks() {
        return this.reloadTicks;
    }
    
    public int getReloadTimeout() {
        return this.reloadTimeout;
    }
    
    public void delete() {
        this.getArena().removeObject(this);
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
        
        Point2D oldLocation = new Point2D(this.getLocation());
        
        // set new heading and move in that direction
        this.setHeading(heading);
        super.move(distance, heading);
        
        // collision detection against other tanks
        Object[] tanks = arena.getObjects(Tank.class);
        for (Object object : tanks) {
            Tank tank = (Tank)object;
            
            if (tank == this) {
                continue;
            }
            
            tank.getBox().collisionDetectOutside(oldLocation, this);
        }
        
        // collision detection against the arena
        this.getArena().toBox().collisionDetectInside(this);
    }
    
    public battletank.math.Box getBox() {
        double x = this.getLocation().getX();
        double y = this.getLocation().getY();
        double r = this.getRadius();
        
        return new battletank.math.Box(x-r, y-r, x+r, y+r);
    }
    
    @Override
    public void tick() {
        if (this.reloadTimeout > 0) {
            this.reloadTimeout--;
        }
        this.ai.tick(this);
    }
    
    @Override
    protected battletank.objects.Object copy() {
        Tank copy = new Tank(this.getPlayer(), this.ai);
        copy.damage = this.damage;
        copy.reloadTimeout = this.reloadTimeout;
        
        return copy;
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        
        
        int x = (int)Math.round(this.getLocation().getX());
        int y = (int)Math.round(this.getLocation().getY());
        int width = (int)Math.round(this.getWidth());
        
        // vehicle
        g2.setColor(this.getPlayer().getPrimaryColor());
        g2.fillOval(x - width/2, y - width/2, width, width);
        
        // turret
        width = width/2;
        g2.setColor(this.getPlayer().getSecondaryColor());
        g2.fillOval(x - width/2, y - width/2, width, width);
        
        // tube
        Point2D tubeEnd = new Point2D(x, y);
        tubeEnd.move(width * 4 / 3, this.getHeading());
        int tx = (int)Math.round(tubeEnd.getX());
        int ty = (int)Math.round(tubeEnd.getY());
        g2.setStroke(new BasicStroke(width/3));
        g2.drawLine(x, y, tx, ty);
    }
}
