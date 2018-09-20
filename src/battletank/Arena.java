package battletank;

import battletank.math.Box;
import java.util.ArrayList;

public class Arena {
    protected int width;
    protected int height;
    protected Box box;
    
    protected ArrayList<Frame> frames = new ArrayList<>();
    protected int maxFrames = 500;
    
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
        
        // delete old frames and objects
        while (this.frames.size() > this.maxFrames) {
            Frame firstFrame = this.frames.get(0);
            for (battletank.objects.Object object : firstFrame.getObjects()) {
                object.destroy();
            }
            
            this.frames.remove(0);
        }
        
        battletank.objects.Object[] objects = this.currentFrame.getObjects();
        for (battletank.objects.Object object : objects) {
            object.tick();
        }
    }
    
    public ArrayList<Frame> getFrames() {
        return this.frames;
    }
    
    public Frame getCurrentFrame() {
        return this.currentFrame;
    }
}
