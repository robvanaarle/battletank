package battletank;

import battletank.math.Point2D;
import battletank.objects.*;
import battletank.tankai.TankAI;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Battletank implements Runnable {

    protected JFrame mainWindow;
    protected ArenaPanel arenaPanel;
    protected PlayersPanel playersPanel;
    protected Arena arena;
    
    public Battletank() {
        
    }
    
    public void init() {
        arena = new Arena(1000, 800);
        Player player;
        TankAI tankAI;
        
        player = new Player("Rob");
        //tankAI = new battletank.tankai.RoundedRectangleAI(false);
        //tankAI = new battletank.tankai.StraightAheadAI();
        tankAI = new battletank.tankai.RandomAI();
        Tank tank1 = new Tank(player, tankAI);
        tank1.setLocation(new Point2D(30, 30));
        tank1.setHeading(Math.PI/4);
        arena.addObject(tank1);
        
        player = new Player("Geert-Jan", Color.BLACK, Color.RED);
        tankAI = new battletank.tankai.RoundedRectangleAI(true);
        //tankAI = new battletank.tankai.PassiveAI();
        Tank tank2 = new Tank(player, tankAI);
        tank2.setLocation(new Point2D(230, 220));
        tank2.setHeading(Math.PI);
        arena.addObject(tank2);
        
        player = new Player("Bas", Color.ORANGE, Color.BLACK);
        //tankAI = new battletank.tankai.StraightAheadAI();
        //tankAI = new battletank.tankai.RoundedRectangleAI(false);
        //tankAI = new battletank.tankai.PassiveAI();
        tankAI = new battletank.tankai.RandomAI();
        //tankAI = new battletank.tankai.tankAI();
        
        //try {
        //    ((battletank.tankai.NetworkAI)tankAI).accept(9000, 0);
        //} catch (Exception e) {
        //    System.out.println(e);
        //}
        
        Tank tank3 = new Tank(player, tankAI);
        tank3.setLocation(new Point2D(170, 220));
        tank3.setHeading(Math.PI);
        arena.addObject(tank3);
        
        player = new Player("Pieter", Color.GREEN, Color.MAGENTA);
        tankAI = new battletank.tankai.RoundedRectangleAI(true);
        //tankAI = new battletank.tankai.PassiveAI();
        Tank tank4 = new Tank(player, tankAI);
        tank4.setLocation(new Point2D(30, 200));
        arena.addObject(tank4);
        
        playersPanel = new PlayersPanel(arena);
        playersPanel.setSize(200, arena.getHeight());
        playersPanel.setLocation(0, 0);
        
        arenaPanel = new ArenaPanel(arena);
        arenaPanel.setSize(arena.getWidth(), arena.getHeight());
        arenaPanel.setLocation(playersPanel.getWidth(), 0);
        
        mainWindow = new JFrame("BattleTank");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(arenaPanel.getWidth()+playersPanel.getWidth(), arenaPanel.getHeight()+20);
        mainWindow.setLayout(null);
        
        mainWindow.add(playersPanel);
        mainWindow.add(arenaPanel);
        mainWindow.setVisible(true);
    }
    
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        // generate
        for (int i = 0; i < 30000; i++) {
            if (i % 100 == 0) {
                System.out.print(i + "...");
            }
            
            //arena.tick();
            
            arenaPanel.setFrame(arena.getCurrentFrame());
            playersPanel.setFrame(arena.getCurrentFrame());
            mainWindow.repaint();
            
            try {
                Thread.currentThread().join(5);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        
        /*
        // replay
        ArrayList<Frame> frames = arena.getFrames();
        for (Frame f : frames) {
            arenaPanel.setFrame(f);
            playersPanel.setFrame(arena.getCurrentFrame());
            mainWindow.repaint();
            
            try {
                Thread.currentThread().join(5);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }*/
    }
    
    public static void main(String[] args) {
        Battletank app = new Battletank();
        app.init();
        app.start();
    }
    
}
