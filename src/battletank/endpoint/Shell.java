/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.endpoint;

/**
 *
 * @author raarle
 */
public class Shell {
    int x;
    int y;
    
    static public Shell fromBattletankShell(battletank.objects.Shell bShell) {
        Shell result = new Shell();
        result.x = (int) bShell.getLocation().getX();
        result.y = (int) bShell.getLocation().getY();
        
        return result;
    }
}
