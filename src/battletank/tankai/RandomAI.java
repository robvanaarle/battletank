package battletank.tankai;
import java.util.Random;

public class RandomAI implements TankAI {
    private long tickIndex = 0;
    private Random random = new Random();
    
    
    public RandomAI() {
    }
    
    @Override
    public void tick(battletank.objects.Tank tank) {
        this.tickIndex++;
        
        double headingDiff = this.random.nextDouble() * tank.getMaxTurnSpeed();
        
        if (this.random.nextInt(2) == 1) {
          headingDiff *= -1;
        }
        
        tank.move(tank.getMaxMoveSpeed(), tank.getHeading()+headingDiff);
        
        tank.shoot();
    }
}
