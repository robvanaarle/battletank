/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.math.Box;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

/**
 *
 * @author raarle
 */
public class Arena {
    protected int width;
    protected int height;
    protected Box box;
    
    protected ArrayList<Frame> frames = new ArrayList<>();
    protected Frame currentFrame = null;
    
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.box = new Box(0, 0, width, height);
        this.currentFrame = new Frame();
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public battletank.objects.Object[] getObjects(Class<?> filter) {
        return this.currentFrame.getObjects(filter);
    }
    
    public battletank.objects.Object[] getObjects() {
        return this.currentFrame.getObjects();
    }
    
    public void addObject(battletank.objects.Object object) {
        object.setArena(this);
        this.currentFrame.addObject(object);
    }
    
    public void removeObject(battletank.objects.Object object) {
        this.currentFrame.removeObject(object);
    }
    
    public Box toBox() {
        return this.box;
    }
    
    public void tick() {
        // save a copy of the current frame
        this.frames.add(this.currentFrame);
        
        // create the next frame
        this.currentFrame = this.currentFrame.next();
        
        battletank.objects.Object[] objects = this.currentFrame.getObjects();
        for (battletank.objects.Object object : objects) {
            object.tick();
        }
    }
    
    public ArrayList<Frame> getFrames() {
        return this.frames;
    }
}
