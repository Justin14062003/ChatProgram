package de.thm.chat.ChatHandler;

import de.thm.chat.MessageHandler.ImageMessage;
import de.thm.chat.MessageHandler.TextMessage;
import de.thm.chat.SessionHandler.Session;
import de.thm.oop.chat.base.server.BasicTHMChatServer;

import java.io.IOException;

public class ChatPartner extends Chat {

    public static void partnerAnschreiben(BasicTHMChatServer run, Session session, String[] partner, boolean img, String message)  {

        TextMessage textMessage = new TextMessage(run, session);
        ImageMessage imageMessage = new ImageMessage(run, session);
        if(img) {
            imageMessage.sendImage(message, partner);
        } else {
            textMessage.sendText(message, partner);
        }
    }
}
