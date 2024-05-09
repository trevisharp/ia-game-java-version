import java.util.Random;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;

import javax.swing.Timer;
import javax.swing.JComponent;

public class Screen extends JComponent
{
    ArrayList<Player> players = new ArrayList<Player>();
    ArrayList<Point> foods = new ArrayList<Point>();
    ArrayList<Bomb> bombs = new ArrayList<Bomb>();
    int frame = 0;
    Timer timer;
    Random rand = new Random();

    public Screen()
    {
        timer = new Timer(25, e -> {
            repaint();
        });
        timer.start();
    }

    public <T> void AddPlayer(Class<T> clazz)
    {
        Point randomPoint = new Point(
            rand.nextInt(getWidth()), 
            rand.nextInt(getHeight())
        );
        try {
            players.add((Player)
                clazz.getDeclaredConstructors()[0].newInstance(randomPoint)
            );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        g.setColor(Color.white);
        frame++;
        
        for (Player player : players)
        {
            if (!player.IsBroken())
                player.Loop(g, .025f, players, foods, bombs, frame);
        }
        
        if (foods.size() < 40)
            foods.add(
                new Point(rand.nextInt(getWidth()), rand.nextInt(getHeight()))
            );
        for (Point food : foods)
        {
            g.setColor(Color.orange);
            g.fillOval(
                (int)food.getX() - 3,
                (int)food.getY() - 3,
                 6, 6
            );
        }
        
        for (int i = 0; i < bombs.size(); i++)
        {
            Bomb bomb = bombs.get(i);
            Boolean dontremove = bomb.Loop(g, .025f, players);
            if (!dontremove)
            {
                bombs.remove(i);
                i--;
            }
        }
    }   
}