package de.thm.chat.MessageHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;
/**
* Eine Unterklasse von Message, die das Versenden von Bildern ermöglicht.
*/

public class ImageMessage extends Message {
    /**
    * Konstruktor für ImageMessage.
    * 
    * @param server   Der Chat-Server, mit dem kommuniziert wird.
    * @param session Aus der aktuellen Session werden Username und Passwort übermittelt
    */

    public ImageMessage(BasicTHMChatServer server, Session session)
    
    {
        super(server, session);
    /**
     * Versendet ein Bild an eine Liste von Empfängern.
     * 
     * @param image_path Der Dateipfad des Bildes.
     * @param receiver   Eine Liste von Benutzern, die das Bild erhalten sollen.
     */
    }
    // Methode für Versand eines Bildes an einem von Empfängern

    public void sendImage(String image_path, String[] receiver)
    {
        
        try
        {
            // FileInputStream für das Bild erstellen
            FileInputStream fileInputStream = new FileInputStream(image_path);
            String imageFormat = image_path.substring((image_path.length() - 4), image_path.length());
            // Überprüfen, ob das Bild größer als 1 MB ist                          
            if (new File(image_path).length()/1048576 >= 1)
            {
                throw new IOException();
            }
            // Überprüfen, ob das Bildformat png oder jpg ist
            else if (!(imageFormat.equals("png") || imageFormat.equals("jpg")))
            {
                throw new IOException();
            }

            try
            {
                // Bild an jeden Empfänger senden
                for (int i = 0; i < receiver.length; i++)
                {
                    this.server.sendImageMessage(this.session.getSessionUserName(), this.session.getSessionPassword(), receiver[i], "image/png", fileInputStream);
                }
                fileInputStream.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
   
        }
        catch(IOException e){
            // Fehler für zu große Bilder oder ungültige Formate
            System.out.println("Bild ist zu groß (max 1 MB) oder Dateiformat (nur png und jpg zulässig) ist falsch.");
        }
        catch(Exception e)
        {
            // Fehler für falsch angegebene oder nicht existierende Bildpfade
            System.out.println("Bild wurde nicht korrekt angegeben oder exstiert nicht. Bitte in der Form 'path/image.imagetype' angeben z.b. /bilder/bild.png");
        }
        
    }

}
