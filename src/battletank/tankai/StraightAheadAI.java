package battletank.tankai;

public class StraightAheadAI implements TankAI {
    @Override
    public void tick(battletank.objects.Tank tank) {
        tank.move(tank.getMaxMoveSpeed(), tank.getHeading());
    }
}
