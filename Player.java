import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Player
{
    public Player(Point location, Color primary, Color secundary, String name)
    {
        this.location = location;
        this.primarycolor = primary;
        this.secundarycolor = secundary;
        this.name = name;
    }

    private Color primarycolor = Color.blue;
    private Color secundarycolor = Color.blue;
    private String name = "robo sem nome";
    private Boolean isBroked = false;
    private Integer points = 0;

    private Float energy = 100f;
    private Float maxEnergy = 100f;
    private Float energyRegeneration = 2f;
    private Float life = 100f;
    private Float maxLife = 100f;
    private Float lifeRegeneration = 2f;

    public Integer getPoints() {
        return points;
    }
    public Color getPrimaryColor() {
        return primarycolor;
    }
    public Color getSecundaryColor() {
        return secundarycolor;
    }
    public String getName() {
        return name;
    }
    public Boolean IsBroken() {
        return isBroked;
    }
    public Float getEnergy() {
        return energy;
    }
    public Float getMaxEnergy() {
        return maxEnergy;
    }
    public Float getEnergyRegeneration() {
        return energyRegeneration;
    }
    public Float getLife() {
        return life;
    }
    public Float getMaxLife() {
        return maxLife;
    }
    public Float getLifeRegeneration() {
        return lifeRegeneration;
    }

    private ArrayList<Point> entitiesInAccurateSonar = new ArrayList<Point>();
    public ArrayList<Point> getEntitiesInAccurateSonar() {
        return entitiesInAccurateSonar;
    }

    private int entitiesInStrongSonar = 0;
    public int getEntitiesInStrongSonar() {
        return entitiesInStrongSonar;
    }

    private ArrayList<Point> enemiesInInfraRed = new ArrayList<Point>();
    public ArrayList<Point> getEnemiesInInfraRed() {
        return enemiesInInfraRed;
    }

    private ArrayList<Point> foodsInInfraRed = new ArrayList<Point>();
    public ArrayList<Point> getFoodsInInfraRed() {
        return foodsInInfraRed;
    }

    private Point location;
    public Point getLocation() {
        return location;
    }

    private Point velocity = new Point();
    public Point getVelocity() {
        return velocity;
    }

    private Point lastDamage = null;
    public Point getLastDamage() {
        return lastDamage;
    }

    private Boolean accuratesonaron = false;
    private Boolean strongsonaron = false;
    private Boolean infraredsensoron = false;
    private Point infraredsensorpoint = null;
    private Boolean shooting = false;
    private Point shootingpoint = null;
    private Boolean infrareset = false;
    private Boolean sonarreset = false;
    private Boolean turbo = false;
    private Boolean moving = false;

    public void Broke() {
        this.isBroked = true;
    }

    public void AccurateSonar() {
        this.accuratesonaron = true;
    }

    public void StrongSonar() {
        this.strongsonaron = true;
    }

    public void InfraRedSensorTo(Point point)
    {
        this.infraredsensoron = true;
        this.infraredsensorpoint = point;
    }

    public void InfraRedSensor(Point direction)
    {
        this.infraredsensoron = true;
        this.infraredsensorpoint = location.sum(direction);
    }

    public void InfraRedSensor(float angle)
    {
        Point direction = new Point(
            (float)Math.cos(angle * (2 * Math.PI) / 360f),
            (float)Math.sin(angle * (2 * Math.PI) / 360f)
        );
        InfraRedSensor(direction);
    }

    public void ShootTo(Point point)
    {
        shooting = true;
        shootingpoint = point;
    }

    public void Shoot(Point direction)
    {
        shooting = true;
        shootingpoint = location.sum(direction);
    }

    public void MoveTo(Point position)
    {
        float dx = position.getX() - location.getX();
        float dy = position.getY() - location.getY();
        float mod = (float)Math.sqrt(dx * dx + dy * dy);
        velocity = new Point(dx / mod, dy / mod);
        moving = true;
    }

    public void StartMove(Point direction)
    {
        float mod = (float)Math.sqrt(
            direction.getX() * direction.getX() + 
            direction.getY() * direction.getY()
        );
        if (mod == 0) {
            velocity = new Point();
            return;
        }
        velocity = new Point(
            2 * direction.getX() / mod,
            2 * direction.getY() / mod
        );
        moving = true;
    }
    
    public void StartMove(float angle)
    {
        Point direction = new Point(
            (float)Math.cos(angle * (2 * Math.PI) / 360f),
            (float)Math.sin(angle * (2 * Math.PI) / 360f)
        );
        StartMove(direction);
    }

    public void StartTurbo()
    {
        turbo = true;
    }

    public void StopTurbo()
    {
        turbo = false;
    }

    public void StopMove()
    {
        velocity = new Point();
        moving = false;
    }

    public void ResetInfraRed()
    {
        infrareset = true;
    }

    public void ResetSonar()
    {
        sonarreset = true;
    }

    public void Draw(Graphics g)
    {
        Color darkpc = new Color(
            4 * this.primarycolor.getRed() / 10,
            4 * this.primarycolor.getGreen() / 10,
            4 * this.primarycolor.getBlue() / 10
        );
        Color darksc = new Color(
            4 * this.secundarycolor.getRed() / 10,
            4 * this.secundarycolor.getGreen() / 10,
            4 * this.secundarycolor.getBlue() / 10
        );

        g.setColor(darkpc);
        g.fillOval(
            (int)location.getX() - 20, 
            (int)location.getY() - 20, 
            40, 40
        );

        g.setColor(primarycolor);
        g.fillArc(
            (int)location.getX() - 20,
            (int)location.getY() - 20,
            40, 40, 0,
            (int)(360 * life / 100)
        );
        
        g.setColor(darksc);
        g.fillOval(
            (int)location.getX() - 10, 
            (int)location.getY() - 10, 
            20, 20
        );

        g.setColor(secundarycolor);
        g.fillArc(
            (int)location.getX() - 10,
            (int)location.getY() - 10,
            20, 20, 0,
            (int)(360 * energy / 100)
        );
        
        g.setColor(Color.black);
        g.drawOval(
            (int)location.getX() - 20, 
            (int)location.getY() - 20, 
            40, 40
        );

        g.drawString(name, 
            (int)location.getX() - 40,
            (int)location.getY() - 40
        );
    }

    public void ReciveDamage(Point bomb)
    {
        life -= 15;
        this.lastDamage = bomb;
    }

    public void Loop(Graphics g, float dt, 
        ArrayList<Player> allplayers,
        ArrayList<Point> allfoods, 
        ArrayList<Bomb> allbombs, int i)
    {
        allplayers = new ArrayList<>(
            allplayers
            .stream()
            .filter(p -> !p.isBroked)
            .collect(Collectors.toList())
        );
        float dx, dy;
        loop();
        Draw(g);

        if (accuratesonaron)
        {
            accuratesonaron = false;
            entitiesInAccurateSonar.clear();
            energy -= 10 * dt;

            for (Player player : allplayers)
            {
                if (player == this)
                    continue;
                dx = player.location.getX() - this.location.getX();
                dy = player.location.getY() - this.location.getY();
                if (dx * dx + dy * dy < 200 * 200) // 200 pixels
                {
                    entitiesInAccurateSonar.add(player.location);
                }
            }
            for (Point food : allfoods)
            {
                dx = food.getX() - this.location.getX();
                dy = food.getY() - this.location.getY();
                if (dx * dx + dy * dy < 200 * 200) // 200 pixels
                {
                    entitiesInAccurateSonar.add(food);
                }
            }
            
            g.setColor(Color.blue);
            g.drawOval(
                (int)location.getX() - 400 / 2,
                (int)location.getY() - 400 / 2,
                400, 400
            );
        }
        
        if (strongsonaron)
        {
            strongsonaron = false;
            energy -= 10 * dt;
            entitiesInStrongSonar = 0;
            for (Player player : allplayers)
            {
                if (player == this)
                    continue;
                dx = player.location.getX() - this.location.getX();
                dy = player.location.getY() - this.location.getY();
                if (dx * dx + dy * dy < 400 * 400) // 400 pixels
                {
                    entitiesInStrongSonar++;
                }
            }
            for (Point food : allfoods)
            {
                dx = food.getX() - this.location.getX();
                dy = food.getY() - this.location.getY();
                if (dx * dx + dy * dy < 400 * 400) // 400 pixels
                {
                    entitiesInStrongSonar++;
                }
            }
            
            g.setColor(Color.darkGray);
            g.drawOval(
                (int)location.getX() - 800 / 2,
                (int)location.getY() - 800 / 2,
                800, 800
            );
        }

        if (infraredsensoron)
        {
            infraredsensoron = false;
            enemiesInInfraRed.clear();
            foodsInInfraRed.clear();

            energy -= 10 * dt;
            float isdx = infraredsensorpoint.getX() - this.location.getX(),
                  isdy = infraredsensorpoint.getY() - this.location.getY();
            Point line = new Point(
                infraredsensorpoint.getX() - this.location.getX(),
                infraredsensorpoint.getY() - this.location.getY()
            );
            line = line.mul(1 / (float)Math.sqrt(isdx * isdx + isdy * isdy));
            
            for (Player player : allplayers)
            {
                if (player == this)
                    continue;
                Point r = player.location;
                float dist = (float)Math.sqrt(
                    (r.getX() - this.location.getX()) * (r.getX() - this.location.getX()) +
                    (r.getY() - this.location.getY()) * (r.getY() - this.location.getY())
                );
                Point finalP = this.location.sum(line.mul(dist));
                float finaldist = (float)Math.sqrt(
                    (r.getX() - finalP.getX()) * (r.getX() - finalP.getX()) +
                    (r.getY() - finalP.getY()) * (r.getY() - finalP.getY())
                );
                if (finaldist < 50f)
                    enemiesInInfraRed.add(r);
            }
            
            for (Point food : allfoods)
            {
                Point r = food;
                float dist = (float)Math.sqrt(
                    (r.getX() - location.getX()) * (r.getX() - location.getX()) +
                    (r.getY() - location.getY()) * (r.getY() - location.getY())
                );
                Point finalP = location.sum(line.mul(dist));
                float finaldist = (float)Math.sqrt(
                    (r.getX() - finalP.getX()) * (r.getX() - finalP.getX()) +
                    (r.getY() - finalP.getY()) * (r.getY() - finalP.getY())
                );
                if (finaldist < 50f)
                    foodsInInfraRed.add(r);
            }
            g.setColor(Color.red);
            Point finalRed = this.location.sum(line.mul(2000f));
            g.drawLine(
                (int)this.location.getX(), (int)this.location.getY(),
                (int)finalRed.getX(), (int)finalRed.getY()
            );
            infraredsensorpoint = null;
        }

        if (shooting)
        {
            shooting = false;
            energy -= 20 * dt;
            Point dir = new Point(
                shootingpoint.getX() - this.location.getX(),
                shootingpoint.getY() - this.location.getY()
            );
            float mod = (float)Math.sqrt(dir.getY() * dir.getY() + dir.getX() * dir.getX());
            dir = dir.mul(50f / mod);

            Bomb bomb = new Bomb();
            bomb.setLocation(this.location.sum(dir));
            bomb.setSpeed(dir.mul(3f));
            allbombs.add(bomb);
        }

        if (infrareset)
        {
            infrareset = false;
            enemiesInInfraRed.clear();
            foodsInInfraRed.clear();
        }

        if (sonarreset)
        {
            sonarreset = false;
            entitiesInStrongSonar = 0;
            entitiesInAccurateSonar.clear();
        }

        for (int f = 0; f < allfoods.size(); f++)
        {
            Point food = allfoods.get(f);
            float fdx = food.getX() - this.location.getX(),
                  fdy = food.getY() - this.location.getY();
            float fdist = (float)Math.sqrt(fdx * fdx + fdy * fdy);
            if (fdist < 10f)
            {
                allfoods.remove(f);
                f--;
                this.points++;
                this.lifeRegeneration += .5f;
                this.energyRegeneration += .5f;
            }
        }

        location = location.sum(velocity.mul((turbo ? 2f : 1f) * 50f * dt));
        energy += energyRegeneration * dt;
        if (moving)
        {
            energy -= 2 * dt;
            if (turbo)
            energy -= 2 * dt;
        }
        if (energy > maxEnergy)
            energy = maxEnergy;
        
        life += lifeRegeneration * dt;
        if (life > maxLife)
            life = maxLife;
        
        if (energy < 0 || life < 0)
            Broke();
    }
    
    protected abstract void loop();
}
