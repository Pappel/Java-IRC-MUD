package com.IRC;

/**
 * Message format for IRC bots
 * @author kwgivler
 *
 */
public class Message {
	private int type;
	private String[] parameters;
	private String message;
	private String nick;
	private String command;
        private String joinChannel;
        private String inviteChannel;
	
	public static final int UNKNOWN = -1; // Message we do not know how to parse 
	public static final int PRIVMSG = 1;  // Message is a PRIVMSG to the bot
	public static final int CHANMSG = 2;  // Message is PRIVMSG to channel
	public static final int NUMERIC = 3;  // Numeric command
	public static final int JOIN = 4;
        public static final int PART = 5; 
        public static final int QUIT = 6; 
        public static final int NICK = 7; 
        public static final int INVITE = 8; 
        public static final int NOTICE = 9; 
	
	/**
	 * Message Constructor
	 * @param type The Message type
	 * @param command The command portion of the message
	 * @param nick The nick associated with this message
	 * @param parameters The parameters to this message
	 * @param trailing The Message
	 */
	public Message(int type, String command, String nick, 
                String[] parameters, String trailing, String joinChannel, String inviteChannel)
	{
		this.type = type;
		this.parameters = parameters;
		this.message = trailing;
		this.nick = nick;
		this.command = command;
                this.joinChannel = joinChannel;
                this.joinChannel = joinChannel;
                this.inviteChannel = inviteChannel;
	}
	
	/**
	 * Get the type of this message
	 * @return The type of message
	 */
	public int getType()
	{
		return type;
	}
	
	/**
	 * Get the channel that is relevant to this message
	 * @return the channel relevant to this message
	 */
	public String[] getParameters()
	{
		return parameters;
	}
	
	/**
	 * Get the message
	 * @return the message
	 */
	public String getTrailing()
	{
		return message;
	}
	
	/**
	 * Get the nick that caused this message to be created
	 * @return the nick relevant to this message
	 */
	public String getNick()
	{
		return nick;
	}
	
	/**
	 * The command (Such as PRVIMSG)
	 * @return command
	 */
	public String getCommand()
	{
		return command;
	}
	
	/**
	 * The channel this message was sent on
	 * @return channel message was sent on
	 */
	public String getChannel()
	{
		if(type == Message.JOIN)
			return getTrailing();
		
		return parameters[0];
	}
        
        public String getJoinChannel()
	{
            return joinChannel;
	}
        
        public String getInviteChannel()
	{
            return inviteChannel;
	}
}