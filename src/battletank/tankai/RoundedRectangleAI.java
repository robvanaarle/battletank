package battletank.tankai;

public class RoundedRectangleAI implements TankAI {
    private long tickIndex = 0;
    private boolean inverse = false;
    
    
    public RoundedRectangleAI(boolean inverse) {
        this.inverse = inverse;
    }
    
    @Override
    public void tick(battletank.objects.Tank tank) {
        this.tickIndex++;
        
        double headingDiff = 0;
        long frame = this.tickIndex % 200;
        if (frame >= 150 && frame < 200) {
            headingDiff = Math.PI/4/50;
        }
        
        if (this.inverse) {
            headingDiff *= -1;
        }
        
        tank.move(tank.getMaxMoveSpeed(), tank.getHeading()+headingDiff);
        
        tank.shoot();
    }
}
