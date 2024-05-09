import java.awt.Color;

public class DonPlayer extends Player
{
    public DonPlayer(Point location)
    {
        super(location, Color.red, Color.blue, "Don");
    }

    int searchindex = 0;
    int frame = 0;
    int points = 0;

    @Override
    protected void loop()
    {
        frame++;
        if (getEnergy() < 10 || frame % 10 == 0)
            return;
        
        if (getEntitiesInStrongSonar() == 0)
        {
            StrongSonar();
            points = getPoints();
            return;
        }

        if (getEntitiesInAccurateSonar().size() == 0)
        {
            AccurateSonar();
            return;
        }

        if (getFoodsInInfraRed().size() == 0)
        {
            InfraRedSensorTo(getEntitiesInAccurateSonar().get(searchindex++ % getEntitiesInAccurateSonar().size()));
            return;
        }
        
        MoveTo(getFoodsInInfraRed().get(0));
        if (getPoints() != points)
        {
            StartTurbo();
            StrongSonar();
            StopMove();
            ResetInfraRed();
            ResetSonar();
        }
    }
}