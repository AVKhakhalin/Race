package Race;

import java.io.*;
import java.util.Scanner;

public class MainApp
{
    public static void main(String[] args)
    {
        InputStream in = MainApp.class.getResourceAsStream("/resArrayCoord_Red.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = "";
        long numberDates = 0;
        try
        {
            while (line != null)
            {
                line = reader.readLine();
                if (line != null)
                {
                    numberDates++;
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        float[][] coordDates = new float[(int) numberDates][2];

        in = MainApp.class.getResourceAsStream("/resArrayCoord_Red.txt");
        reader = new BufferedReader(new InputStreamReader(in));
        numberDates = 0;
        line = "";
        try
        {
            while (line != null)
            {
                line = reader.readLine();
                if (line != null)
                {
                    coordDates[(int) numberDates][0] = getFloat(line);
                    coordDates[(int) numberDates][1] = getFloat(line.substring(line.indexOf(" ", 0) + 1, line.length()));
                    System.out.println(coordDates[(int) numberDates][0] + "; " + coordDates[(int) numberDates][1]);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static float getFloat(String inpString)
    {
        Scanner scanner = new Scanner(inpString);
        float floatNumber = -1f;
        if (scanner.hasNextFloat())
        {
            floatNumber = scanner.nextFloat();
        }
        return floatNumber;
    }
}
