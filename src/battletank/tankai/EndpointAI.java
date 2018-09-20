/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battletank.tankai;

import battletank.endpoint.Command;

/**
 *
 * @author raarle
 */
public class EndpointAI implements TankAI {
    protected Command command;
    
    public void setCommand(Command command) {
        this.command = command;
    }
    
    @Override
    public void tick(battletank.objects.Tank tank) {
        if (command == null) {
            return;
        }
        
        double distance = 0;
        
        if (command.move.equals("forward") || command.move.equals("forwards")) {
            distance = tank.getMaxMoveSpeed();
        } else if (command.move.equals("backward") || command.move.equals("backwards")) {
            distance = -tank.getMaxMoveSpeed();
        }
        
        double heading = tank.getHeading();
        if (command.turn.equals("left")) {
            heading -= tank.getMaxTurnSpeed();
        } else if (command.turn.equals("right")) {
            heading += tank.getMaxTurnSpeed();
        }
        
        tank.move(distance, heading);
        
        if (command.fire) {
            tank.shoot();
        }
    }
}
