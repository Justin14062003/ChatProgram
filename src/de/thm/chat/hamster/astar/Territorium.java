package de.thm.chat.hamster.astar;

public class Territorium
{
    private static String[][] map;
    private static int AnzahlSpalten;
    private static int AnzahlReihen;


    public static String[] territorium = {"territorium: 14 10 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 x 0 0 0 0 0 0 0 0 0 0 ! 0 0 x 0 0 0 0 0 0 0 0 x 0 0 0 0 x 0 0 0 0 0 0 0 0 x 0 0 0 0 x 0 0 0 0 0 0 0 0 x 0 0 0 0 x 0 0 0 0 0 0 0 0 x 0 0 0 0 x 0 0 0"};

public Territorium()
    {

    }
    public static int getAnzahlKoerner(int j, int i)
    {
        if(map[j][i].equals("!"))
            return 1;
        return 0;
    }

    public static boolean mauerDa(int j, int i)
    {
        if(map[j][i].equals("x"))
            return true;
        else
            return false;
    }

    public static int getAnzahlSpalten()
    {
        return AnzahlSpalten;
    }

    public static int getAnzahlReihen()
    {
        return AnzahlReihen;
    }

    public static void GenerateTerritoriumFromMessage()//String[] message
    {
        String test = territorium[0];
        String[] messageSplit = test.split(" ");

        for (int i= 0; i < messageSplit.length ;i++)
        {
            System.out.println(messageSplit[i]);
        }
        AnzahlReihen = Integer.parseInt(messageSplit[2]);
        AnzahlSpalten = Integer.parseInt(messageSplit[1]);

        map = new String[AnzahlReihen][AnzahlSpalten];

        //first elem at 3
        int x = 0;
        int y = 0;
        for (int i = 3; i < messageSplit.length;i++)
        {

            map[y][x] = messageSplit[i];

            x++;

            if (x % AnzahlSpalten == 0)
            {
                y++;
                x = 0;
            }


        }

    }
}
