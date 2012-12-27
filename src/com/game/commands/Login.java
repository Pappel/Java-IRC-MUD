package com.game.commands;

import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Config;
import com.core.DB.dao.PlayerDAO;
import com.core.Lists;
import com.game.mob.Player;
import java.util.Date;

/**
 *
 * @author kasutaja
 */
public class Login {
    
    Colors c;
    
    private String nick;
    
    private MessageSender msg;
    
    
    
    public Login(String pnick, String[] args) {
        this.nick = pnick;
        
        if (args.length < 2){
            msg.sendPrivMsg(nick, c.UNDERLINE+"Login");
            msg.sendPrivMsg(nick, "Command: "+c.BOLD+"/msg "+Config.getNick()+" login [username] [password]");
        }
        else if (args.length < 3){
            msg.sendPrivMsg(nick, c.RED+"Missing password, try again.");
            msg.sendPrivMsg(nick, "Command: "+c.BOLD+"/msg "+Config.getNick()+" login [username] "+c.RED+"[password]");
        }
        
        else {
            
            Player player = new Player();
            player = PlayerDAO.getByUsername(args[1]);
            
            if (player == null){
                msg.sendPrivMsg(nick, c.RED+"Username doesn't exist, try again.");
            }
            else if (Lists.onlinePlayers.containsKey(nick)){
                msg.sendPrivMsg(nick, c.RED+"Player already logged in.");
            }
            else if (!player.getPassword().equals(args[2])){
                msg.sendPrivMsg(nick, c.RED+"Invalid password, try again.");
            }
            else {
                
                msg.sendPrivMsg(nick, "You are now logged in.");
                msg.sendPrivMsg(nick, "Last login: "+player.getLastLogon()+" (UTC +2)");
                
                Date date = new Date();
                
                player.setLastLogon(date);
                player.setCurNick(nick);

                
                Lists.onlinePlayers.put(nick, player);
                
                msg.sendPrivMsg(nick, "Your current location: "
                        +c.DARK_GREEN+c.BOLD+player.getDynamicModel().getCurrentIsland().getName()
                        +" island"+c.NORMAL+", "
                        +c.YELLOW+player.getDynamicModel().getCurrentRoom().getName()
                        +c.NORMAL+".");
                msg.sendPrivMsg(nick, "Please join the channel "
                        +c.BROWN+player.getDynamicModel().getCurrentIsland().getChannel()
                        +c.NORMAL+" immediately or you will be logged out!");
                
                msg.inviteToChannel(nick, player.getDynamicModel().getCurrentIsland().getChannel());
                
                
                
                msg.sendPrivMsg(Config.getChannel(), "Welcome back to "
                        + c.YELLOW+"The Forsaken Isles "+c.NORMAL+"player "+c.GREEN+args[1]+c.NORMAL+".");
            }
        }

    }

    
    
}
