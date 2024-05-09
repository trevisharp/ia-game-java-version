import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class Bomb
{
    private Point location;
    private Point speed;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Point getSpeed() {
        return speed;
    }

    public void setSpeed(Point speed) {
        this.speed = speed;
    }

    public Boolean Loop(Graphics g, float dt, ArrayList<Player> allplayer)
    {
        g.setColor(Color.black);
        g.fillOval(
            (int)location.getX() - 2,
            (int)location.getY() - 2,
             4, 4
        );

        location = location.sum(speed.mul(dt));
        for (Player player : allplayer)
        {
            if (player.IsBroken())
                continue;
            
            Rectangle rect = new Rectangle(
                (int)player.getLocation().getX() - 25, 
                (int)player.getLocation().getY() - 25,
                50, 50
            );
            if (rect.contains(location.getX(), location.getY()))
            {
                player.ReciveDamage(this.location);
                return false;
            }
        }

        return true;
    }
}