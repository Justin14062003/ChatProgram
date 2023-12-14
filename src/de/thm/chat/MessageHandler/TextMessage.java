package de.thm.chat.MessageHandler;

import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

/**
 Die Klasse TextMessage erweitert die abstrakte Klasse Message und stellt Methoden
* zum Senden von Textnachrichten im Chat-System bereit.
*/

public class TextMessage extends Message
{

    
    /**
    * Konstruktor für ImageMessage.
    * 
    * @param server   Der Chat-Server, mit dem kommuniziert wird.
    * @param session Aus der aktuellen Session werden Username und Passwort übermittelt
    */

    public TextMessage(BasicTHMChatServer server, Session session)
    {
        super(server, session);
    }
    
    
    /** 
    * Diese Methode sendet Textnachrichten an eine oder mehrere Empfänger.
    * @param text
    * @param receiver
    */
    public void sendText(String text, String[] receiver)
    {

        try
        {
            for (int i = 0; i < receiver.length; i++)
             // Sendet die Textnachricht an jeden Empfänger im Array.

            {
                this.server.sendTextMessage(this.session.getSessionUserName(), this.session.getSessionPassword(), receiver[i], text);
            }
        }
        catch(Exception e){
             // Behandelt mögliche Ausnahmen und gibt den Fehler aus.

            //e.printStackTrace();
            System.out.println("Nachricht konnte nicht gesendet werden");
        }   
    }
}
