package com.core.util;

import com.core.Lists;
import com.game.world.Island;
import com.game.world.Room;
import java.util.List;


public abstract class IslandSearch {
    
    
    public static Island findByChannel(String channel){
        
        for (Island island : Lists.islands) {
            if (island.getChannel().equals(channel)) {
                return island;
            }
        }        
        return null;
    }
    
    
    public static Room findRoomByName(Island island, String roomname){
        
        for (Room room : island.getRooms()) {
            
            if (room.getName().toLowerCase().equals(roomname.toLowerCase())) {
                return room;
            }            
        }
        return null;
    }
    
    
    public static Room findRoomByContain(Island island, String roomname){
        
        for (Room room : island.getRooms()) {
            
            if (room.getName().toLowerCase().contains(roomname.toLowerCase())) {
                return room;
            }            
        }
        return null;
    }
    
    
    public static Island findIslandByName(String islandname){
        
        for (Island island : Lists.islands) {
            
            if (island.getName().toLowerCase().equals(islandname.toLowerCase())) {
                return island;
            }            
        }
        return null;
    }
    
    
}
