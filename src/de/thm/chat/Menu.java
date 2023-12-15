package de.thm.chat;

import de.thm.chat.ChatHandler.ChatGruppe;
import de.thm.chat.ChatHandler.ChatPartner;
import de.thm.chat.MessageHandler.Message;
import de.thm.chat.hamster.astar.AStern;
import de.thm.chat.hamster.astar.Hamster;
import de.thm.chat.hamster.astar.Territorium;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Die Klasse Menu stellt Methoden zum Anzeigen und Interagieren mit dem Chat-Menü bereit.
 */
public class Menu{

    /**
     * Zeigt das Haupt-Chat-Menü an und ermöglicht dem Benutzer die Auswahl verschiedener Aktionen.
     * Beendet die Schleife, wenn die Benutzersitzung nicht mehr aktiv ist.
     */
    public static void chatMenu()
    {
        Scanner navInput = new Scanner(System.in);

        while (Main.session.isSessionActive())
        {
            System.out.println("Was wollen Sie tun?");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Nachricht an eine Person --> 1");
            System.out.println("Nachricht an eine Gruppe --> 2");
            System.out.println("Meine Nachrichten abrufen --> 3");
            System.out.println("Chatgruppe erstellen --> 4");
            System.out.println("Chatgruppen ausgeben --> 5");
            System.out.println("Einen Hamster Steuern --> 6");
            System.out.println("Session beenden --> 7");

            try
            {
                int input = navInput.nextInt();

                if (input >= 1 && input <= 7)
                {
                    SelectFromInput(input);

                }
                else
                {
                    System.out.println("Bitte geben Sie eine Zahl zwischen 1 und 4 an!");
                }
            } catch (InputMismatchException e)
            {
                System.out.println("Bitte geben sie eine Zahl an");
                navInput.nextLine();
            }
        }
        navInput.close();
    }
    /**
     * Verarbeitet die Benutzereingabe basierend auf der ausgewählten Option im Hauptmenü.
     *
     * @param input Die ausgewählte Option im Hauptmenü.
     */
    private static void SelectFromInput(int input)
    {
        Scanner userInput = new Scanner(System.in);
        boolean imageTyp;
        switch (input)
        {
            case 1: //person
                System.out.println("An wen möchten Sie schreiben:");


                String inputPartner = userInput.nextLine();

                String[] partner = new String[]{inputPartner};

                System.out.println("Geben Sie ihre Nachricht ein:");
                String text = userInput.nextLine();

                System.out.println("Wird ein Bild mit Pfad verschickt? (Ja oder Nein)");
                imageTyp = Objects.equals(userInput.nextLine(), "Ja");

                ChatPartner.partnerAnschreiben(Main.server, Main.session, partner, imageTyp, text);
                break;

            case 2: //gruppe
                System.out.println("Geben Sie den Namen der Gruppe an");
                String groupName = userInput.nextLine();

                System.out.println("Geben Sie ihre Nachricht ein:");
                String groupMessage = userInput.nextLine();

                System.out.println("Wird ein Bild mit Pfad verschickt? (Ja oder Nein)");
                 imageTyp = Objects.equals(userInput.nextLine(), "Ja");

                ChatGruppe.gruppeAnschreiben(Main.server, Main.session, groupName, imageTyp, groupMessage);

                break;

            case 3:
                getMessageFromUserInput();
                break;

            case 4:
                Scanner scan = new Scanner(System.in);
                List<String> teilnehmer = new ArrayList<>();
                String groupUserInput;
                System.out.println("Bitte gib die Gruppenmitglieder *nacheinander* ein. Drücke Enter ohne eingabe um fortzufahren.");
                while(scan.hasNextLine()) {
                    groupUserInput = scan.nextLine();
                    if (groupUserInput.isEmpty()) {
                        break;
                    }
                    teilnehmer.add(groupUserInput);
                }
                System.out.println("Bitte gebe einen Namen für die Gruppe ein.");
                String nameGruppe = scan.nextLine();
                ChatGruppe gruppe = new ChatGruppe(nameGruppe, teilnehmer);
                System.out.println("Es wurde erfolgreich eine Gruppe angelegt :)");
                break;

            case 5:
                List<String> chatgruppen = ChatGruppe.gruppenAusgeben();
                System.out.println("Folgende Gruppen existieren:");
                System.out.println(chatgruppen.toString());

            case 6:
                System.out.println("Wie heisst der Hamster?");
                String name = userInput.nextLine();
                String[] nameHamster = new String[]{name};
                ChatPartner.partnerAnschreiben(Main.server, Main.session, nameHamster,false, "init");
                Message message = new Message(Main.server, Main.session);
                Hamster standard = new Hamster(nameHamster);

                //Message message = new Message(Main.server, Main.session);
                String[] messageString;
                try
                {
                    TimeUnit.SECONDS.sleep(5);
                    messageString = message.getManyMessageRaw(2);
                }
                catch (Exception e)
                {
                    System.out.println("Nachricht vom Hamster konnte nicht abgerufen werden");
                    messageString = null;
                }

                for (int i= 0; i < messageString.length ;i++)
                {
                    System.out.println(messageString[i]);
                }

                //System.out.println(messageString.toString());

                Territorium territorium = new Territorium();
                territorium.GenerateTerritoriumFromMessage();
                AStern test = new AStern(standard);
                test.hamsterNutztAStern(standard);
                break;

            case 7: //quit
                Main.session.CloseSession();
                userInput.close();
                System.exit(0); // closes the program
                break;
        }


    }

    /**
     * Sammelt die Benutzereingabe, um Nachrichten basierend auf bestimmten Kriterien abzurufen.
     */
    private static void getMessageFromUserInput()
    {
        Scanner userInput = new Scanner(System.in);

        System.out.println("Was möchten sie Wählen");
        System.out.println("Bestimmte Anzahl an Nachrichten --> 1");
        System.out.println("Bestimmte Nachricht anhand der Nachricht ID --> 2");
        Message message = new Message(Main.server, Main.session);

        boolean intInput = true;
        while (intInput)
        {
            try
            {
                int input = userInput.nextInt();
                intInput = false;

                boolean getMessage = true;
                while (getMessage)
                {
                    switch (input)
                    {
                        case 1:
                            System.out.println("Wie viele Nachrichten sollen angezeigt werden?");
                            try
                            {
                                int messageCount = userInput.nextInt();
                                message.getManyMessage(messageCount);
                                getMessage = false;
                            }
                            catch (InputMismatchException e)
                            {
                                System.out.println("Bitte geben Sie eine Zahl an");
                                userInput.nextLine();
                            }

                            break;

                        case 2:
                            System.out.println("Geben Sie die Nachrichten ID ein?");
                            try
                            {
                                long messageID = userInput.nextLong();
                                message.getMessagesAfterID(messageID);
                                getMessage = false;
                            }
                            catch (InputMismatchException e)
                            {
                                System.out.println("Bitte geben Sie eine Zahl an");
                                userInput.nextLine();
                            }
                            break;

                        default:
                            System.out.println("Bitte geben Sie eine Zahl zwischen 1 und 2 ein?");
                            break;
                    }
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println("Bitte geben Sie eine Zahl an");
                userInput.nextLine();
            }
        }

        userInput.close();
    }

}