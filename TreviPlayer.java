import java.awt.Color;

public class TreviPlayer extends Player
{
    public TreviPlayer(Point location)
    {
        super(location, Color.green, Color.blue, "Trevisan");
    }

    int i = 0;
    Point enemy = null;
    boolean isloading = false;

    @Override
    protected void loop()
    {
        StartTurbo();
        enemy = 
            getEnemiesInInfraRed().size() > 0 ?
            getEnemiesInInfraRed().get(0) : null;

        if (getEnergy() < 10)
        {
            StopMove();
            isloading = true;
            enemy = null;
        }

        if (getEnergy() > 60)
            isloading = false;

        if (isloading)
            return;

        if (enemy == null) {
            InfraRedSensor(5f * i++);
            return;
        }
        
        InfraRedSensorTo(enemy);
        float dx = enemy.getX() - getLocation().getX();
        float dy = enemy.getY() - getLocation().getY();
        if (dx * dx + dy * dy >= 300f * 300f)
            MoveTo(enemy);
        else
        {
            StopMove();
            if (i++ % 5 == 0)
                ShootTo(enemy);
        }
    }
}