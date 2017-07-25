/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.math.Box;
import java.util.ArrayList;

/**
 *
 * @author raarle
 */
public class Arena {
    protected int width;
    protected int height;
    protected ArrayList<battletank.objects.Object> objects = new ArrayList<>();
    protected Box box;
    //protected ArrayList<ArrayList<battletank.objects.Object>> history = new ArrayList<>();
    
    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.box = new Box(0, 0, width, height);
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public battletank.objects.Object[] getObjects() {
        battletank.objects.Object[] objects = new battletank.objects.Object[this.objects.size()];
        return this.objects.toArray(objects);
    }
    
    public void addObject(battletank.objects.Object object) {
        object.setArena(this);
        this.objects.add(object);
    }
    
    public void removeObject(battletank.objects.Object object) {
        this.objects.remove(object);
    }
    
    public Box toBox() {
        return this.box;
    }
    
    public void tick() {
        for (int i = 0; i < this.objects.size(); i++) {
            battletank.objects.Object object = this.objects.get(i);
            object.tick();
        }
    }
}
