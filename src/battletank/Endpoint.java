
package battletank;

import battletank.endpoint.*;
import battletank.math.Point2D;
import battletank.objects.Tank;
import battletank.tankai.TankAI;
import com.google.gson.Gson;
import javax.swing.JFrame;


public class Endpoint implements Runnable {
    protected Arena arena;
    protected WebServer server;
    protected int port = 9001;
    
    protected boolean useGui = true;
    protected JFrame mainWindow;
    protected ArenaPanel arenaPanel;
    protected PlayersPanel playersPanel;
    
    public Endpoint() {
    }
    
    public Arena getArena() {
        return arena;
    }
    
    public void init() {
        arena = new Arena(600, 400);
        server = new WebServer(this, port);
        
        if (useGui) {
            showGui();
        }
    }
    
    public void showGui() {
        playersPanel = new PlayersPanel(arena);
        playersPanel.setSize(200, arena.getHeight());
        playersPanel.setLocation(0, 0);

        arenaPanel = new ArenaPanel(arena);
        arenaPanel.setSize(arena.getWidth(), arena.getHeight());
        arenaPanel.setLocation(playersPanel.getWidth(), 0);

        mainWindow = new JFrame("BattleTank Endpoint");
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
        server.start();
    }
    
    public void refresh() {
        if (!useGui) {
            return;
        }
        
        System.out.println(".");

        arenaPanel.setFrame(arena.getCurrentFrame());
        playersPanel.setFrame(arena.getCurrentFrame());
        mainWindow.repaint();
    }
    
    public static void main(String[] args) {
        Endpoint app = new Endpoint();
        app.init();
        app.start();
    }
    
    static class Person {
       public String name;
       int[] values;
    }
}
