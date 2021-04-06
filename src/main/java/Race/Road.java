package Race;

public class Road extends Stage
{
    private long ns;
    private long counter1;

    public Road(int length)
    {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c)
    {
        try
        {
            System.out.println(c.getName() + " начал этап: " + description);
//            Thread.sleep(length / c.getSpeed() * 1000);
            counter1 = 0;
            c.setCounterStagePoints(1);
            while (Math.round(c.getCounterStagePoints() * 2.606) < length)
            {
                ns = System.nanoTime();
                counter1++;
                if (counter1 % (c.getSpeed() - 19) == 0)
                {
                    c.setCounterStagePoints(c.getCounterStagePoints() + 1);
                    c.setCounterDistantPoints(c.getCounterDistantPoints() + 1);
                }
                ns += c.getFrameLength();
                Thread.sleep(Math.max(0, (ns - System.nanoTime()) / 10000000));
            }
            System.out.println(c.getName() + " закончил этап: " + description);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
