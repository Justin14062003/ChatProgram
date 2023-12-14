package de.thm.chat;

import java.util.Scanner;
import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;
/**
* Die Hauptklasse des Chat-Anwendungsprogramms.
*/
public class Main {
    /**
    * Die aktuelle Benutzersitzung im Chat.
    */
    public static Session session;
    /**
    * Der Server für den Chat.
    */
    public static BasicTHMChatServer server;
    /**
    * Ein Scanner-Objekt für die Benutzereingabe.
    */
    private static Scanner input;
    /**
    * Der Einstiegspunkt des Chat-Anwendungsprogramms.
    * Erstellt einen Chat-Server, initialisiert die Benutzersitzung und ruft das Hauptmenü auf.
    *
    * @param args Die Befehlszeilenargumente (werden nicht verwendet).
    */
    public static void main(String[] args)
    {
        //EiP_Projekt3
        server = new BasicTHMChatServer();
        input = new Scanner(System.in);

        session = initSession();

        Menu.chatMenu();

    }

    /**
    * Initialisiert eine Benutzersitzung durch Abfrage von Benutzername und Passwort.
    *
    * @return Eine neue Benutzersitzung mit den angegebenen Benutzerdaten.
    */
    private static Session initSession()
    {
        System.out.println("Geben sie ihren Benutzernamen an!");
        String name = input.nextLine();
        System.out.println("Geben sie ihr Passwort an!");
        String password = input.nextLine();


        return new Session(name, password);
    }

}