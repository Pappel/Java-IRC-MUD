package com.IRC;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MessageSender {
    
    private static BotCore core;
    private static final Logger logger = Logger.getLogger("GameBot");
    
    
    public static boolean isConnected() {
        return core.isConnected();
    }
    
    public static void sendRawMessage(String command) {
        core.sendRawMessage(command);
    }
    
    public static void sendPrivMsg(String channel, String message) {		
        if(!isConnected())
        throw new IllegalStateException("Not Connected");

	sendRawMessage("PRIVMSG " + channel + " :" + message + "\r\n");
    }
    
    
    public static void sendPrivMsg(Message who, String message) {
        if(who.getType() == Message.PRIVMSG)
            sendPrivMsg(who.getNick(), message);
	else if(who.getType() == Message.CHANMSG)
            sendPrivMsg(who.getChannel(), message);
	else
            logger.log(Level.WARNING, "Can''t handle message of type: {0}", who.getType());
    }
    
    
    public static void inviteToChannel(String nick, String channel) {		
        if(!isConnected())
        throw new IllegalStateException("Not Connected");

	sendRawMessage("INVITE " + nick + " " + channel + "\r\n");
    }
    
    public static void kickFromChannel(String nick, String channel, String msg) {		
        if(!isConnected())
        throw new IllegalStateException("Not Connected");

	sendRawMessage("KICK " + channel + " " + nick + " :" +msg+ "\r\n");
    }
    
    
    public static void setChannelMode(String channel, String mode) {		
        if(!isConnected())
        throw new IllegalStateException("Not Connected");

	sendRawMessage("MODE " + channel + " " + mode + "\r\n");
    }
}