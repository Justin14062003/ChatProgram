package de.thm.chat.hamster.astar;

import de.thm.chat.ChatHandler.ChatPartner;
import de.thm.chat.Main;

public class Hamster
{
    private int Blickrichtung = 1;
    private String[] Name;
    public Hamster(String[] name)
    {
        this.Name = name;
    }

    public void nimm() //wird nicht gebraucht
    {

    }

    public int getReihe()
    {
        return 0;
    }

    public int getSpalte()
    {
        return 0;
    }

    public void vor()
    {
        ChatPartner.partnerAnschreiben(Main.server, Main.session, this.Name,false, "v");
    }

    public int getBlickrichtung() //kann so nicht genutzt werden, da der Hamster keine blickrichtung zurückgibt vom chat (muss berechnet werden)
    {
        // blickrichtung ist standart nach osten
        return this.Blickrichtung;
    }

    public void linksUm()
    {
        ChatPartner.partnerAnschreiben(Main.server, Main.session, this.Name,false, "linksUm");

        if (this.Blickrichtung - 1 == -1)
            this.Blickrichtung = 3;
        else
            this.Blickrichtung--;

    }

    public String[] GetName()
    {
        return this.Name;
    }
}
