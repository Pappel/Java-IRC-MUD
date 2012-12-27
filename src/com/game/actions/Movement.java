package com.game.actions;

import com.IRC.MessageSender;
import com.core.DB.dao.PlayerDAO;
import com.core.util.IslandSearch;
import com.game.mob.Player;
import com.game.world.Island;


public class Movement {
    
    public MessageSender msg;
    
    public void moveToIsland(Player player, String leftChannel, String newChannel){
        
        
        Island leftIsland = player.getDynamicModel().getCurrentIsland();
        Island newIsland = IslandSearch.findByChannel(newChannel);
        
        
        if(!leftChannel.equals(newIsland.getChannel())){
            
            msg.kickFromChannel(player.getCurNick(), leftChannel
                                , "Player moved to "+newIsland.getName());
            
            leftIsland.getPlayers().remove(player);        
            player.getDynamicModel().setCurrentIsland(newIsland);
            player.getDynamicModel().setCurrentRoom(IslandSearch
                                    .findRoomByContain(newIsland, "port"));
            newIsland.addPlayer(player);
            
            PlayerDAO.update(player);
        }
        //remove player from current island players list
        //add player to new island players list
        
        //set players current island to new island
        
        //kick player from old islands channel
        
        
    }
}
