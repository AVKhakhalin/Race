package Race;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Car implements Runnable
{
    private static int CARS_COUNT = 0;
    private Race race;
    private int speed;
    private String name;

    CyclicBarrier cyclicBarrier;
    CyclicBarrier finishBarrier;
    public static String winnerName = "";
    private long counterDistantPoints = 1;
    private long counterStagePoints = 1;
    private long frameLength;

    public String getName()
    {
        return name;
    }

    public int getSpeed()
    {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier _cyclicBarrier, CyclicBarrier _finishBarrier, long _frameLength)
    {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = _cyclicBarrier;
        this.finishBarrier = _finishBarrier;
        this.frameLength = _frameLength;
    }
    @Override
    public void run()
    {
        try
        {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < race.getStages().size(); i++)
        {
            race.getStages().get(i).go(this);
        }

        try
        {
            if (winnerName.length() == 0)
            {
                winnerName = name;
                System.out.println("        " + name + " ДОСТИГ ФИНИША ПЕРВЫМ!");
            }
            finishBarrier.await();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getWinnerName()
    {
        return winnerName;
    }

    public long getFrameLength()
    {
        return frameLength;
    }

    public void setCounterDistantPoints(long _counterDistantPoints)
    {
        this.counterDistantPoints = _counterDistantPoints;
    }

    public long getCounterDistantPoints()
    {
        return counterDistantPoints;
    }

    public void setCounterStagePoints(long _counterStagePoints)
    {
        this.counterStagePoints = _counterStagePoints;
    }

    public long getCounterStagePoints()
    {
        return counterStagePoints;
    }
}
