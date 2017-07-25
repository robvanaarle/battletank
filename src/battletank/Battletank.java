/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.math.Point2D;
import battletank.objects.*;
import javax.swing.JFrame;
/**
 *
 * @author raarle
 */
public class Battletank implements Runnable {

    protected JFrame frame;
    protected Panel panel;
    protected Tank tank;
    protected Arena arena;
    
    public Battletank() {
        
    }
    
    public void init() {
        arena = new Arena(600, 400);
        tank = new Tank("Rob");
        tank.setLocation(new Point2D(30, 30));
        arena.addObject(tank);
        
        frame = new JFrame("BattleTank");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(arena.getWidth()+200, arena.getHeight()+200);
        frame.setLayout(null);
        
        panel = new Panel(arena);
        panel.setSize(arena.getWidth(), arena.getHeight());
        panel.setLocation(100, 100);
        
        frame.add(panel);
        frame.setVisible(true);
    }
    
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
    
    public void run() {
        for (int i = 0; i < 1000; i++) {
            arena.tick();
            frame.repaint();
            
            try {
                Thread.currentThread().join(5);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Battletank app = new Battletank();
        app.init();
        app.start();
    }
    
}
