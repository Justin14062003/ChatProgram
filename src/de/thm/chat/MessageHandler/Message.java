package de.thm.chat.MessageHandler;

import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

/**
* Die Klasse Message stellt Methoden zum Abrufen und Formatieren von Chat-Nachrichten bereit.
*/
public class Message
{
    /** @param numMessages Anzahl der maximalen Anzahl an Nachrichten die angezeigt werden soll */
    public static final String ANSI_RESET = "\u001B[0m"; 
    public static String ANSI_COLOR = " \u001B[32m"; 
    
    protected BasicTHMChatServer server; 
    protected Session session;

    /**
    * Konstruktor für die Message-Klasse.
    *
    * @param server   Der Chat-Server, mit dem kommuniziert wird.
    * @param session Aus der aktuellen Session werden Username und Passwort übermittelt
    */
    public Message(BasicTHMChatServer server , Session session)
    {
        this.server = server;
        this.session = session;
    }
    
    /** 
     * Ruft eine bestimmte Anzahl von Nachrichten ab und gibt sie formatiert aus
     * @param numMessages Anzahl der maximalen Anzahl an Nachrichten die angezeigt werden soll
     */
    public void getManyMessage(int numMessages)
    {
        String[] messages;
        try 
        {
            if (numMessages <= 100 )
            {
                messages = this.server.getMostRecentMessages(this.session.getSessionUserName(), this.session.getSessionPassword());
            }
            else
            {
                messages = this.server.getMessages(this.session.getSessionUserName(), this.session.getSessionPassword(), 0);
            }
            
            if(messages.length < numMessages)
            {
                numMessages = messages.length;
            }
            System.out.println("Es werden " + numMessages + " Nachrichten angezeigt.");
            for (int i = messages.length - numMessages - 1 ; i < messages.length; i++)
            {
                prettyMessages(messages[i]);
            }
        } 
        catch (Exception e) 
        {
            //e.printStackTrace();
            System.out.println("Nachrichten konnten nicht ausgegeben werden");
        }
        
    }
    public String[] getManyMessageRaw(int numMessages)
    {
        String[] messages;
        try
        {
            if (numMessages <= 100 )
            {
                messages = this.server.getMostRecentMessages(this.session.getSessionUserName(), this.session.getSessionPassword());
            }
            else
            {
                messages = this.server.getMessages(this.session.getSessionUserName(), this.session.getSessionPassword(), 0);
            }

            if(messages.length < numMessages)
            {
                numMessages = messages.length;
            }

            System.out.println("Es werden " + numMessages + " Nachrichten angezeigt.");
            String[] finalMessage = new String[0];
            for (int i = messages.length - numMessages - 1 ; i < messages.length; i++)
            {
                finalMessage = messages;
            }

            return finalMessage;
        }
        catch (Exception e)
        {
            //e.printStackTrace();
            System.out.println("Nachrichten konnten nicht ausgegeben werden");
        }
        return null;
    }

    
    /**
    * Ruft Nachrichten ab, die nach einer bestimmten ID gesendet wurden, und gibt sie formatiert aus.
    * @param messageId Die Nachrichten-ID als Referenzpunkt.
    */
    public void getMessagesAfterID(long messageId)
    {
        String[] messages;
        try 
        {
            messages = this.server.getMessages(this.session.getSessionUserName(), this.session.getSessionPassword(), messageId);
            System.out.println("Es werden " + messages.length + " Nachrichten angezeigt.");
            for (int i = 0; i < messages.length; i++)
            {
                prettyMessages(messages[i]);
            }
        } 
        catch (Exception e) 
        {
            //e.printStackTrace();
            System.out.println("Nachricht konnten nicht ausgegeben werden");
        } 
    }

   
    /**
    * Formatiert eine Nachricht und gibt sie in der Konsole aus.
    * @param message Die zu formatierende Nachricht.
    */
    private void prettyMessages(String message)
    {
        String prettyMessageHead = "";
        int loopCounter = message.indexOf("|");
        /**Datum zum Message Header hinzufügen*/
        prettyMessageHead = "gesendet am "+ message.substring(loopCounter+1, loopCounter + 11); 
        /**Uhrzeit zum Message Header hinzufügen*/
        prettyMessageHead = prettyMessageHead +" um " + message.substring(loopCounter + 12, loopCounter + 20) +  " Uhr ";
        /**ID zum Message Header hinzufügen*/
        prettyMessageHead = prettyMessageHead + "(ID: " + message.substring(0, loopCounter) + "):";
        
        loopCounter = message.indexOf("|", loopCounter+1);
        int loopCounter2 = message.indexOf("|", loopCounter+1);

        if (message.substring(loopCounter+1, loopCounter+4).equals("out"))
        {
            loopCounter = message.indexOf("|", loopCounter2 + 1);
            prettyMessageHead = "\nAn " + message.substring(loopCounter2 + 1, loopCounter) + " " + prettyMessageHead;
            ANSI_COLOR = "\u001B[32m";
        }

        else
        {
            loopCounter = message.indexOf("|", loopCounter2 + 1);
            prettyMessageHead = "\nVon " + message.substring(loopCounter2 + 1, loopCounter) + " " + prettyMessageHead + ":";
            ANSI_COLOR = "\u001B[33m";

        }

        if (message.substring(loopCounter+1, loopCounter+4).equals("img")){
            loopCounter = message.indexOf("|", loopCounter + 1);
            loopCounter = message.indexOf("|", loopCounter + 1);
        }

        loopCounter = message.indexOf("|", loopCounter + 1);
        String prettyText = message.substring(loopCounter+1, message.length());
        System.out.println(ANSI_COLOR + prettyMessageHead + ANSI_RESET);
        System.out.println(prettyText);
    }
}