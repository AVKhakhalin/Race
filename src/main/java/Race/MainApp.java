package Race;

import java.io.*;
import java.util.Scanner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp
{

    public static void main(String[] args)
    {
        // Считывание информации из файлов
        String fileName = "/resArrayCoord_Red.txt";
        float[][] coordDates_Red = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Red);
        fileName = "/resArrayCoord_Blue.txt";
        float[][] coordDates_Blue = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Blue);
        fileName = "/resArrayCoord_Yellow.txt";
        float[][] coordDates_Yellow = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Yellow);
        fileName = "/resArrayCoord_Green.txt";
        float[][] coordDates_Green = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Green);
    }

    //region Метод для считывания информации из файла
    public static void readDatesFromFile(String fileName, float[][] toArray)
    {
        String line = "";

        InputStream in = MainApp.class.getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        long numberDates = 0;
        line = "";
        try
        {
            while (line != null)
            {
                line = reader.readLine();
                if ((line != null) && (numberDates < toArray.length))
                {
                    toArray[(int) numberDates][0] = getFloat(line);
                    toArray[(int) numberDates][1] = getFloat(line.substring(line.indexOf(" ", 0) + 1, line.length()));
                    numberDates++;
                }
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static long determNumberFileDates(String fileName)
    {
        InputStream in = MainApp.class.getResourceAsStream(fileName);
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
        return numberDates;
    }
    //endregion

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
