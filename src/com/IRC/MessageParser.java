package com.IRC;

import java.util.logging.Logger;

public class MessageParser {
	private static Logger logger = Logger.getLogger("GameBot");
	private String botNick;

	/**
	 * Constructor
	 * @param bot The bot we are parsing messages for
	 */
	public MessageParser(String botNick)
	{
		this.botNick = botNick;
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Attempt to parse raw message from server
	 * @param message Message to parse
	 */
	public Message parseMessage(String message)
	{
		/*
		 * Originally Based on code from:
		 * http://calebdelnay.com/blog/2010/11/parsing-the-irc-message-format-as-a-client
		 */
            
                 //String message = Colors.removeFormattingAndColors(rawmessage);
            
            
            
		String trailing = "";
		String prefix = "";
		String command = "";
		String nick = "";
                String joinChannel = "";
                String inviteChannel = "";
                String vhost = "";
		String[] parameters;
		int prefixEnd = -1;
		int trailingStart = message.indexOf(" :");

		//logger.log(Level.INFO, "Message: " + message);
                
                //:leashed!Your_Uncle@leashed.users PRIVMSG #testbot :test

		// Extract nick
                if (message.contains("JOIN :"))
		{
			int jcstart = message.indexOf("JOIN :");
                        //int vhostStart = message.indexOf("@");
			joinChannel = message.substring(jcstart+6);
		}
                
                // channel of invite
                if (message.contains("NOTICE @#"))
		{
			int icstart = message.indexOf(" @#");
                        int icend = message.indexOf(" :");
			inviteChannel = message.substring(icstart+2, icend);
		}
                
                // get joinChannel
                if (message.contains(".users "))
		{
			int vhostEnd = message.indexOf(".users ");
                        int vhostStart = message.indexOf("@");
			vhost = message.substring(vhostStart+1, vhostEnd);
		}
                
                
		if (message.indexOf("!") >= 0)
		{
			int nickEnd = message.indexOf("!");
			nick = message.substring(1, nickEnd);
		}

		// Extract prefix (contains :Nick!user@host)
		if (message.startsWith(":"))
		{
			prefixEnd = message.indexOf(" ");
			prefix = message.substring(1, prefixEnd);
		}

		// Extract trailing (Last part of message, example: The text sent to a channel)
		if (trailingStart >= 0)
			trailing = message.substring(trailingStart + 2).replaceAll("[^a-zA-Z 0-9]+","");
		else
			trailingStart = message.length();

		// extract command and command parameters (example: command = PRIVMSG parameters = #channel)
		String[] commandAndParameters = message.substring(prefixEnd + 1, trailingStart).split(" ");
		command = commandAndParameters[0];
		parameters = new String[commandAndParameters.length -1];

		if (commandAndParameters.length > 1){
			for(int i = 1; i < commandAndParameters.length; i++){
				parameters[i-1] = commandAndParameters[i];
			}
		}

		int type = getType(command, nick, parameters);

		// Debugging
		StringBuilder debug = new StringBuilder();
		debug.append("Type: ").append(type);
		debug.append(" Prefix: ").append(prefix);
		debug.append(" Command: ").append(command);
		debug.append(" Nick: ").append(nick);
                debug.append(" joinChannel: ").append(joinChannel);
                debug.append(" inviteChannel: ").append(joinChannel);
                debug.append(" Vhost: ").append(vhost);
		for(int i = 0; i < parameters.length; i++)
		{
			debug.append(" Param[").append(i).append("]: ").append(parameters[i]);
		}
		debug.append(" Trailing: ").append(trailing);
		logger.info(debug.toString());

		return new Message(type, command, nick, parameters, trailing, joinChannel, inviteChannel);
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Determine the type of the message we are parsing
	 * @return The message type
	 */
	private int getType(String command, String nick, String[] parameters)
	{
		// Determine message type:
		// Don't understand this message
		int type = Message.UNKNOWN;
		// Someone joined a channel we are in
                if(command.equals("NOTICE") && !nick.equals(botNick))
			type = Message.NOTICE;
                if(command.equals("INVITE") && !nick.equals(botNick))
			type = Message.INVITE;
		if(command.equals("JOIN") && !nick.equals(botNick))
			type = Message.JOIN;
                if(command.equals("PART") && !nick.equals(botNick))
			type = Message.PART;
                if(command.equals("QUIT") && !nick.equals(botNick))
			type = Message.QUIT;
                if(command.equals("NICK") && !nick.equals(botNick))
			type = Message.NICK;
		// PRIVMSG to us or a channel
		if(command.equals("PRIVMSG"))
		{
			if (parameters[0].equals(botNick))
				type = Message.PRIVMSG;
			else
				type = Message.CHANMSG;
		}

		try{
			Integer.parseInt(command);
			type = Message.NUMERIC;
		} catch (NumberFormatException e)
		{
			// do nothing, command is not numeric
		}
		return type;
	}

	// --------------------------------------------------------------------------------------------------
}