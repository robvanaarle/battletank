/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank;

import battletank.math.Point2D;
import battletank.objects.*;
import java.util.ArrayList;
import javax.swing.JFrame;
/**
 *
 * @author raarle
 */
public class Battletank implements Runnable {

    protected JFrame frame;
    protected Panel panel;
    protected Arena arena;
    
    public Battletank() {
        
    }
    
    public void init() {
        arena = new Arena(600, 400);
        Tank tank1 = new Tank(new Player("Rob"), new battletank.tankai.RoundedRectangleAI(false));
        tank1.setLocation(new Point2D(30, 30));
        arena.addObject(tank1);
        
        //Tank tank2 = new Tank(new Player("Max"), new battletank.tankai.PassiveAI());
        Tank tank2 = new Tank(new Player("Max"), new battletank.tankai.RoundedRectangleAI(true));
        tank2.setLocation(new Point2D(200, 30));
        tank2.setHeading(Math.PI);
        arena.addObject(tank2);
        
        Tank tank3 = new Tank(new Player("Rob2"), new battletank.tankai.RoundedRectangleAI(false));
        tank3.setLocation(new Point2D(200, 200));
        tank3.setHeading(Math.PI);
        arena.addObject(tank3);
        
        Tank tank4 = new Tank(new Player("Max2"), new battletank.tankai.RoundedRectangleAI(true));
        tank4.setLocation(new Point2D(30, 200));
        arena.addObject(tank4);
        
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
        //panel.setFrame(arena.);
        
        // generate
        for (int i = 0; i < 1000; i++) {
            arena.tick();
            
            panel.setFrame(arena.currentFrame);
            
            frame.repaint();
            
            try {
                Thread.currentThread().join(5);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        
        // replay
        ArrayList<Frame> frames = arena.getFrames();
        for (Frame f : frames) {
            panel.setFrame(f);
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
