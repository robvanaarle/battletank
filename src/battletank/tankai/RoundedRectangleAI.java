package battletank.tankai;

public class RoundedRectangleAI implements TankAI {
    private int tickIndex = 0;
    private int shootCountdown = 0;
    private boolean inverse = false;
    
    
    public RoundedRectangleAI(boolean inverse) {
        this.inverse = inverse;
    }
    
    @Override
    public void tick(battletank.objects.Tank tank) {
        this.tickIndex++;
        
        double headingDiff = 0;
        int frame = this.tickIndex % 200;
        if (frame >= 150 && frame < 200) {
            headingDiff = Math.PI/4/50;
        }
        
        if (this.inverse) {
            headingDiff *= -1;
        }
        
        tank.move(tank.getMaxMoveSpeed(), tank.getHeading()+headingDiff);

        this.shootCountdown--;
        if (this.shootCountdown < 0) {
            this.shootCountdown = 20;
            
            tank.shoot();
        }
        
    }
}
