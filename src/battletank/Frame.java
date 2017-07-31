/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import java.util.ArrayList;

/**
 *
 * @author raarle
 */
public class Frame {
    protected ArrayList<battletank.objects.Object> objects = new ArrayList<>();
    
    public battletank.objects.Object[] getObjects(Class<?> filter) {
        ArrayList<battletank.objects.Object> filtered = new ArrayList<>();
        for (battletank.objects.Object o : this.objects) {
            if (filter.isInstance(o)) {
                filtered.add(o);
            }
        }
        
        battletank.objects.Object[] objects = new battletank.objects.Object[filtered.size()];
        return filtered.toArray(objects);
    }
    
    public battletank.objects.Object[] getObjects() {
        battletank.objects.Object[] objects = new battletank.objects.Object[this.objects.size()];
        return this.objects.toArray(objects);
    }
    
    public void removeObject(battletank.objects.Object object) {
        this.objects.remove(object);
    }
    
    public void addObject(battletank.objects.Object object) {
        this.objects.add(object);
    }
    
    public Frame next() {
        Frame next = new Frame();
        for (battletank.objects.Object o : this.objects) {
            next.addObject(o.next());
        }
        
        return next;
    }
    
}
