package de.thm.chat.ChatHandler;

import de.thm.chat.MessageHandler.ImageMessage;
import de.thm.chat.MessageHandler.Message;
import de.thm.chat.MessageHandler.TextMessage;
import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatGruppe extends Chat {

    String nameGruppe;
    List<String> teilnehmer;

    static List<ChatGruppe> gruppenListe = new ArrayList<>();

    public ChatGruppe (String nameGruppe, List<String> teilnehmer ){
        this.nameGruppe = nameGruppe;
        this.teilnehmer = teilnehmer;

        gruppenListe.add(this);
    }

    public static void gruppeAnschreiben(BasicTHMChatServer run, Session session, String nameGruppe, boolean img, String message)  {
        int pos = 0;

        TextMessage textMessage = new TextMessage(run, session);
        ImageMessage imageMessage = new ImageMessage(run, session);
        for (ChatGruppe gruppe: gruppenListe) {
            if(nameGruppe.equalsIgnoreCase(gruppe.nameGruppe)) {
                ChatGruppe anGruppe = gruppenListe.get(pos);
                String[] receiver = new String[anGruppe.teilnehmer.size()];
                if (img) {
                    imageMessage.sendImage(message, receiver);
                } else {
                    textMessage.sendText(message, receiver);
                }
                return;
            }else {
                pos++;
            }
        }
        System.out.println("Die angegebene Gruppe existiert nicht! \nTipp: Lass dir alle existierenden Gruppen ausgeben :)");

    }

    public static List<String> gruppenAusgeben() {
        List<String> gruppenNamen = new ArrayList<>();
        for(ChatGruppe gruppe: gruppenListe){
            gruppenNamen.add(gruppe.nameGruppe);
        }
        return gruppenNamen;
    }
}
