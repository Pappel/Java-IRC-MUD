package com.IRC;

import com.core.Config;
import com.core.Lists;
import com.game.world.Island;
import java.io.IOException;
import java.util.ArrayList;

/**
 * IRC Bots must extend this class
 * 
 * @author kwgivler
 *
 */
public abstract class IRCBot implements MessageReceiver
{
	private static BotCore core;
	private String nick;
	private String realName;
        public MessageSender msg = new MessageSender();
	private ArrayList<String> channels = new ArrayList<>(); // Channels the bot is currently in

	/**
	 * Construct IRCBot
	 * @param server Server to connect to
	 * @param port Port to connect on
	 * @param nick bot's nick
	 * @param realName bot's realname
	 */
	public IRCBot(String server, int port, String nick, String realName)
	{
		core = new BotCore(server, port, this);
		this.nick = nick;
		this.realName = realName;
	}

	// --------------------------------------------------------------------------------------------------

	public IRCBot(String server, int port, String nick)
	{
		this(server, port, nick, "GameBot");
	}
	// --------------------------------------------------------------------------------------------------

	public IRCBot(String server, int port)
	{
		this(server, port, "GameBot", "GameBot");
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Connect to server
	 */
	public void connect() throws IOException
	{
		core.connect(nick, realName);
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Disconnect from server
	 */
	public void disconnect()
	{
		core.disconnect();
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Get the nick of the bot
	 * @return The bot's nick
	 */
	public String getNick()
	{
		return nick;
	}

	// --------------------------------------------------------------------------------------------------

	/**
	 * Get the channels the bot is in
	 * @return An ArrayList of the channels the bot is in
	 */
	public ArrayList<String> getChannels()
	{
		if(!msg.isConnected())
			throw new IllegalStateException("Not Connected");

		ArrayList<String> myChannels = new ArrayList<>();
		for(int i = 0; i < channels.size(); i++)
		{
			myChannels.add(channels.get(i));
		}
		return myChannels;
	}
   
	public void join(String channel)
	{
		if(!msg.isConnected())
			throw new IllegalStateException("Not Connected");

		msg.sendRawMessage("JOIN " + channel + "\r\n");
		channels.add(channel);
	}
        
        public void joinIslands()
	{
		if(!msg.isConnected())
			throw new IllegalStateException("Not Connected");
                
            for (Island island : Lists.islands) {
                msg.sendRawMessage("JOIN " + island.getChannel() + "\r\n");
                channels.add(island.getChannel());
            }

	}

	public void part(String channel)
	{
		if(!msg.isConnected())
			throw new IllegalStateException("Not connected");

		for (int i = 0; i < channels.size(); i++)
		{
			if(channels.get(i).equals(channel))
			{
				channels.remove(i);
				msg.sendRawMessage("PART " + channel + "\r\n");
			}
		}
	}
        
        public void identify()
	{
		if(!msg.isConnected())
			throw new IllegalStateException("Not Connected");

		msg.sendRawMessage("PRIVMSG nickserv :IDENTIFY "+Config.getPassword()+"\r\n");
	}

    @Override
	public abstract void receiveMessage(Message message);

}