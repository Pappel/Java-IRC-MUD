package com;

import com.IRC.CommandHandler;
import com.IRC.IRCBot;
import com.IRC.Message;
import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Config;
import com.core.DB.dao.IslandDAO;
import com.core.DB.dao.PlayerDAO;
import com.core.Lists;
import com.core.util.IslandSearch;
import com.game.actions.Movement;
import com.game.commands.Logout;
import com.game.mob.Player;
import com.game.world.Island;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameBot extends IRCBot{

	private String owner; // nick of bot owner
        
        Colors c;
        
        public MessageSender msg;
        public CommandHandler cmd = new CommandHandler();
        private String nick; // nick
        private String channel; // nick

	private Logger logger = Logger.getLogger("GameBot");

	/**
	 * Construct GameBot
	 * @param server IRC to connect to
	 * @param port port to connect on
	 * @param nick Bot's nickname
	 * @param realName Bot's realname
	 */
	public GameBot(String server, int port, String nick, String realName) {		
		super(server, port, nick, realName);
	}

	/**
	 * Start the bot
	 * @param prefix Command prefix
	 * @param channel Initial channel to join
	 * @param owner nick of bot owner
	 */
	public void start(String prefix, String channel, String owner) throws IOException {
		this.owner = owner;

                // connect to IRC network and join main channel
		connect();
		join(channel);
                identify();
                // load island into list and join island channels
                
                IslandDAO.loadIslands();
                joinIslands();
                
                // identify with nickserv
                //identify();
                
                // logout all online players
                //GeneralQueries gq = new GeneralQueries();
                //int count = gq.logEveryoneOut();
                
                
                /*
                if (count == 1){
                    msg.sendPrivMsg(Config.getChannel(), "Restart complete, "
                            + "logged out "+c.RED+count+c.NORMAL+" player.");
                }
                else {
                    msg.sendPrivMsg(Config.getChannel(), "Restart complete, "
                            + "logged out "+c.RED+count+c.NORMAL+" players.");
                }
                * 
                */
	}
        
        
         /**
	 * Received Messages are sent here by the BotCore
	 * @param message the parsed message
	 */
    @Override
         public void receiveMessage(Message message) {
            int type = message.getType();
         
            if(type == Message.CHANMSG || type == Message.PRIVMSG) {  
                
                try {
                    cmd.handleCommand(message);
                } catch (Exception ex) {
                    Logger.getLogger(GameBot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            else if (type == Message.NOTICE){
                
                String[] msgParams = message.getTrailing().split(" ");
                
                String inviter = msgParams[0];
                nick = msgParams[2];
                channel = message.getInviteChannel();
                
                if(!inviter.equals(Config.getNick())){
                    Lists.inviteKicks.put(nick, channel);
                }
                
            }
            
            else if (type == Message.JOIN){
                
                nick = message.getNick();

                
                if (Lists.inviteKicks.containsKey(nick) && 
                        Lists.inviteKicks.get(nick).equals(message.getJoinChannel())){
                    
                    msg.kickFromChannel(nick, message.getJoinChannel(), "You're not suppose to be here");
                    
                }       
                    
                    Player player = Lists.onlinePlayers.get(nick);
                    
                    String leftChannel = player.getDynamicModel().getCurrentIsland().getChannel();
                    
                    String newChannel = message.getJoinChannel();
                    
                    Movement move = new Movement();
                    
                    move.moveToIsland(player, leftChannel, newChannel);
                
            }
            
            else if(type == Message.PART) {
                
                String[] params = message.getParameters();
                
                nick = message.getNick();
                
                if (Lists.onlinePlayers.containsKey(nick)){
                        if (params[0].equals(Config.getChannel())){
                            
                            String channel = Lists.onlinePlayers.get(nick)
                                            .getDynamicModel().getCurrentIsland().getChannel();
                            
                            msg.kickFromChannel(nick, channel, "Player left the game.");
                            Logout logout = new Logout(nick, 0);   
                        }
                        else if (params[0].equals(Lists.onlinePlayers.get(nick)
                                            .getDynamicModel().getCurrentIsland().getChannel())){
                            
                            msg.kickFromChannel(nick, Config.getChannel(), "Player left the game.");
                            Logout logout = new Logout(nick, 0);  
                        }

                        
                        //Logout logout = new Logout(nick, 0);
                    }
            }
            
            else if(type == Message.QUIT) {
                nick = message.getNick();
                
                if (Lists.onlinePlayers.containsKey(nick)){
                    Logout logout = new Logout(nick, 0);  
                }
                
            }
            
            else if(type == Message.NICK) {
                nick = message.getNick();
                String newNick = message.getTrailing();
                
                if (Lists.onlinePlayers.containsKey(nick)){
                
                    Player player = new Player();
                    player = Lists.onlinePlayers.get(nick);
                
                    Lists.onlinePlayers.remove(nick);
                
                    player.setCurNick(newNick);
                
                    Lists.onlinePlayers.put(newNick, player);
                
                }
                
            }

	}  
         
        
        

	/**
	 * Process quit command
	 * @param message message containing quit command
	*/ 
	private void quit(Message message) {
		if(message.getNick().equalsIgnoreCase(owner)) {
			disconnect();
			System.exit(0);
		}
                else {
			msg.sendPrivMsg(message, "You don't own me!");
		}
	}
        

	/**
	 * 
	 * @return The nick which owns the bot
	 */
	public String getOwner() {
		return owner.toLowerCase();
	}
}