package Race;

// Блок для работы со списками
import java.util.ArrayList;
import java.util.Arrays;

// Блок для считывания информации из файлов
import java.io.*;
import java.util.Scanner;

// Блок для поворота изображения
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MainApp
{
    public static final int SIZE_WIDTH = 916;  // 18 пикселей занимают бордюры в сумме слева и справа
    public static final int SIZE_HEIGHT = 555; // 45 пикселей занимают бордюры в сумме сниз у и сверху

    public static Image background;
    public static Image redCar;
    public static Image blueCar;
    public static Image greenCar;
    public static Image yellowCar;
    public static long lastTime;
    public static long curTime;
    public static float deltaTime;
    public static long counter_Red = 1;
    public static long counter_Blue = 1;
    public static long counter_Green = 1;
    public static long counter_Yellow = 1;

    public static JFrame frame;

    public static final float KOEF_SCALE = 0.87f;
    public static float[][] coordDates_Red;
    public static float[][] coordDates_Blue;
    public static float[][] coordDates_Yellow;
    public static float[][] coordDates_Green;

    public static float[] deltaCoordDates_Red = new float[2];
    public static float[] deltaCoordDates_Blue = new float[2];
    public static float[] deltaCoordDates_Yellow = new float[2];
    public static float[] deltaCoordDates_Green = new float[2];

    public static float angle_Red = 0f;
    public static float angle_Blue = 0f;
    public static float angle_Yellow = 0f;
    public static float angle_Green = 0f;

    public static final int CARS_COUNT = 4;

    public static void main(String[] args)
    {
        //region Считывание информации из файлов
        String fileName = "/resArrayCoord_Red.txt";
        coordDates_Red = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Red, KOEF_SCALE);
        fileName = "/resArrayCoord_Blue.txt";
        coordDates_Blue = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Blue, KOEF_SCALE);
        fileName = "/resArrayCoord_Yellow.txt";
        coordDates_Yellow = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Yellow, KOEF_SCALE);
        fileName = "/resArrayCoord_Green.txt";
        coordDates_Green = new float[(int) determNumberFileDates(fileName)][2];
        readDatesFromFile(fileName, coordDates_Green, KOEF_SCALE);

        // Установка дельт
        deltaCoordDates_Red[0] = coordDates_Red[0][0] - 816 + 3;
        deltaCoordDates_Red[1] = coordDates_Red[0][1] - 145 + 10;
        deltaCoordDates_Blue[0] = coordDates_Blue[0][0] - 823;
        deltaCoordDates_Blue[1] = coordDates_Blue[0][1] - 148;
        deltaCoordDates_Yellow[0] = coordDates_Yellow[0][0] - 828;
        deltaCoordDates_Yellow[1] = coordDates_Yellow[0][1] - 151;
        deltaCoordDates_Green[0] = coordDates_Green[0][0] - 834;
        deltaCoordDates_Green[1] = coordDates_Green[0][1] - 154;
        //endregion

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerLocationX = (int) ((screenSize.width - SIZE_WIDTH) / 2);
        int centerLocationY = (int) ((screenSize.height - SIZE_HEIGHT) / 2);

        frame = new JFrame("Домашнее задание №13 студента GeekBrains Хахалина Андрея Владимировича");
        JFrame.setDefaultLookAndFeelDecorated(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(centerLocationX, centerLocationY);
        frame.setSize(SIZE_WIDTH, SIZE_HEIGHT);

        // Создаём панель
        JPanel totalGUI = new JPanel();
        totalGUI.setBackground(new Color(200, 203, 255)); // смена фона окна
        totalGUI.setLayout(null);
        frame.add(totalGUI);

        try
        {
            redCar = ImageIO.read(MainApp.class.getResource("/car_red.png"));
            blueCar = ImageIO.read(MainApp.class.getResource("/car_blue.png"));
            greenCar = ImageIO.read(MainApp.class.getResource("/car_green.png"));
            yellowCar = ImageIO.read(MainApp.class.getResource("/car_yellow.png"));
            background = ImageIO.read(MainApp.class.getResource("/Background.jpg"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        newPanel.setSize(SIZE_WIDTH, SIZE_HEIGHT);
        frame.add(newPanel);
        frame.pack();
        frame.setVisible(true);

        // Блок гонок
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++)
        {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }
        for (int i = 0; i < cars.length; i++)
        {
            new Thread(cars[i]).start();
        }
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }

    //region Создание панели с объектами
    public static JPanel newPanel = new JPanel(null)
    {
        @Override
        public Dimension getPreferredSize()
        {
            return frame.getSize();
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    };
    public static void onRepaint(Graphics g)
    {
        curTime = System.nanoTime();
        deltaTime = (curTime - lastTime) * 0.000000001f;
        lastTime = curTime;
//        System.out.println(deltaTime);
        if (deltaTime > 0.0006)
        {
            counter_Red++;
            counter_Blue++;
            counter_Green++;
            counter_Yellow++;
        }
        if (counter_Red >= coordDates_Red.length)
        {
            counter_Red = 1;
        }
        if (counter_Blue >= coordDates_Blue.length)
        {
            counter_Blue = 1;
        }
        if (counter_Green >= coordDates_Green.length)
        {
            counter_Green = 1;
        }
        if (counter_Yellow >= coordDates_Yellow.length)
        {
            counter_Yellow = 1;
        }

        // Отображение фона
        g.drawImage(background,0,0,null);

        // Расчёт углов
        angle_Red = (float) Math.atan2(coordDates_Red[(int) counter_Red][1] - coordDates_Red[(int) counter_Red - 1][1], coordDates_Red[(int) counter_Red][0] - coordDates_Red[(int) counter_Red - 1][0]);
        angle_Blue = (float) Math.atan2(coordDates_Blue[(int) counter_Blue][1] - coordDates_Blue[(int) counter_Blue - 1][1], coordDates_Blue[(int) counter_Blue][0] - coordDates_Blue[(int) counter_Blue - 1][0]);
        angle_Green = (float) Math.atan2(coordDates_Green[(int) counter_Green][1] - coordDates_Green[(int) counter_Green - 1][1], coordDates_Green[(int) counter_Green][0] - coordDates_Green[(int) counter_Green - 1][0]);
        angle_Yellow = (float) Math.atan2(coordDates_Yellow[(int) counter_Yellow][1] - coordDates_Yellow[(int) counter_Yellow - 1][1], coordDates_Yellow[(int) counter_Yellow][0] - coordDates_Yellow[(int) counter_Yellow - 1][0]);

        // Прорисовка машин
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform origXform = g2.getTransform(); // получаем текущее значение
        AffineTransform newXform = (AffineTransform)(origXform.clone()); // клонируем текущее значение

        newXform.rotate(angle_Red + Math.PI / 2, (int) (coordDates_Red[(int) counter_Red][0] - deltaCoordDates_Red[0]) + redCar.getWidth(null) / 2, (int) (coordDates_Red[(int) counter_Red][1] - deltaCoordDates_Red[1]) + redCar.getHeight(null) / 2);
//        g2.rotate(angle_Red + Math.PI / 2, (int) (coordDates_Red[(int) counter_Red][0] - deltaCoordDates_Red[0]) + redCar.getWidth(null) / 2, (int) (coordDates_Red[(int) counter_Red][1] - deltaCoordDates_Red[1]) + redCar.getHeight(null) / 2);
        g2.setTransform(newXform);
        g2.drawImage(redCar, (int) (coordDates_Red[(int) counter_Red][0] - deltaCoordDates_Red[0]), (int) (coordDates_Red[(int) counter_Red][1] - deltaCoordDates_Red[1]), null);
        g2.setTransform(origXform);

        origXform = g2.getTransform();
        newXform = (AffineTransform)(origXform.clone());
        newXform.rotate(angle_Blue + Math.PI / 2,  (int) (coordDates_Blue[(int) counter_Blue][0] - deltaCoordDates_Blue[0]) + blueCar.getWidth(null) / 2, (int) (coordDates_Blue[(int) counter_Blue][1] - deltaCoordDates_Blue[1]) + blueCar.getHeight(null) / 2);
        g2.setTransform(newXform);
        g2.drawImage(blueCar, (int) (coordDates_Blue[(int) counter_Blue][0] - deltaCoordDates_Blue[0]), (int) (coordDates_Blue[(int) counter_Blue][1] - deltaCoordDates_Blue[1]), null);
        g2.setTransform(origXform);

        origXform = g2.getTransform();
        newXform = (AffineTransform)(origXform.clone());
        newXform.rotate(angle_Yellow + Math.PI / 2, (int) (coordDates_Yellow[(int) counter_Yellow][0] - deltaCoordDates_Yellow[0]) + yellowCar.getWidth(null) / 2, (int) (coordDates_Yellow[(int) counter_Yellow][1] - deltaCoordDates_Yellow[1]) + yellowCar.getHeight(null) / 2);
        g2.setTransform(newXform);
        g2.drawImage(yellowCar, (int) (coordDates_Yellow[(int) counter_Yellow][0] - deltaCoordDates_Yellow[0]), (int) (coordDates_Yellow[(int) counter_Yellow][1] - deltaCoordDates_Yellow[1]), null);
        g2.setTransform(origXform);

        origXform = g2.getTransform();
        newXform = (AffineTransform)(origXform.clone());
        newXform.rotate(angle_Green + Math.PI / 2, (int) (coordDates_Green[(int) counter_Green][0] - deltaCoordDates_Green[0]) + greenCar.getWidth(null) / 2, (int) (coordDates_Green[(int) counter_Green][1] - deltaCoordDates_Green[1]) + greenCar.getHeight(null) / 2);
        g2.setTransform(newXform);
        g2.drawImage(greenCar, (int) (coordDates_Green[(int) counter_Green][0] - deltaCoordDates_Green[0]), (int) (coordDates_Green[(int) counter_Green][1] - deltaCoordDates_Green[1]), null);
        g2.setTransform(origXform);
    }
    //endregion

    //region Метод для считывания информации из файла
    public static void readDatesFromFile(String fileName, float[][] toArray, float koefScale)
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
                    toArray[(int) numberDates][0] = getFloat(line) / koefScale;
                    toArray[(int) numberDates][1] = getFloat(line.substring(line.indexOf(" ", 0) + 1, line.length())) / koefScale;
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
    public static float getFloat(String inpString)
    {
        Scanner scanner = new Scanner(inpString);
        float floatNumber = -1f;
        if (scanner.hasNextFloat())
        {
            floatNumber = scanner.nextFloat();
        }
        scanner = null;
        return floatNumber;
    }
    //endregion
}
