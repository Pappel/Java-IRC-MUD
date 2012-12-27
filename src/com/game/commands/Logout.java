package com.game.commands;

import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Config;
import com.core.DB.dao.PlayerDAO;
import com.core.Lists;
import com.game.mob.Player;
import java.util.Date;



public class Logout {
    
    Colors c;
    
    private String nick;
    private MessageSender msg;
    
    
    
    public Logout(String pnick, int type){  
        
            this.nick = pnick;
            
            Player player = new Player();
        
            player = Lists.onlinePlayers.get(nick);
            Date date = new Date();
            player.setLastLogon(date);


        
        switch (type){
            case 0: msg.sendPrivMsg(Config.getChannel(), c.GREEN+player.getUsername()+c.NORMAL
                                    +" slowly fades away into the shadows...");
                    PlayerDAO.update(player);
                    Lists.onlinePlayers.remove(nick);

            break;
              
            case 1: 
                    msg.sendPrivMsg(nick, "You are now logged out.");
                    msg.sendPrivMsg(Config.getChannel(), c.GREEN+player.getUsername()+c.NORMAL
                                    +" slowly fades away into the shadows...");
                    
                    msg.kickFromChannel(nick, player.getDynamicModel().getCurrentIsland().getChannel(),
                            "Player logged out.");
                    PlayerDAO.update(player);
                    Lists.onlinePlayers.remove(nick);

            break;
        }
        
    }
    
}
