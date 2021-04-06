package Race;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage
{
    Semaphore semaphore;
    private long ns;
    private long counter1;

    public Tunnel(int _length, Semaphore _semaphore)
    {
//        this.length = 80;
        this.length = _length;
        this.description = "Тоннель " + length + " метров";
        this.semaphore = _semaphore;
    }

    @Override
    public void go(Car c)
    {
        try
        {
            try
            {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
//                Thread.sleep(length / c.getSpeed() * 1000);
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
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                System.out.println(c.getName() + " закончил этап: " + description + " !!!DELTHIS " + c.getCounterStagePoints() + " " + c.getCounterDistantPoints());
                semaphore.release();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
