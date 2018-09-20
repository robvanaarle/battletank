/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.endpoint;

import battletank.objects.Tank;

/**
 *
 * @author raarle
 */
public class Player {
    public int id;
    public String name;
    public Shell[] shells;
    public double heading;
    public int x;
    public int y;
    
    static public Player fromTank(Tank tank) {
        Player player = new Player();
        
        player.shells = new Shell[tank.getShells().size()];
        for (int i = 0; i < player.shells.length; i++) {
            player.shells[i] = Shell.fromBattletankShell(tank.getShells().get(i));
        }
        
        player.name = tank.getPlayer().getName();
        player.heading = tank.getHeading();
        player.x = (int) tank.getLocation().getX();
        player.y = (int) tank.getLocation().getY();
        
        return player;
    }
}
