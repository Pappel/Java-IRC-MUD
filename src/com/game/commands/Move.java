package com.game.commands;

import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Config;
import com.core.Lists;
import com.core.util.IslandSearch;
import com.game.mob.Player;
import com.game.world.Island;
import com.game.world.Room;
import java.util.ArrayList;
import java.util.List;



public class Move {
    
    Colors c;
    
    private String nick;
    private MessageSender msg;
    
    private String[] args;
    
    Player player = new Player();
        
    
    
    public Move(String nick, String[] args) {
        
        this.nick = nick;
        this.args = args;
        
        this.player = Lists.onlinePlayers.get(nick);
        
    }
    
    public void move(){
        
        if (args.length < 2){
            msg.sendPrivMsg(nick, c.UNDERLINE+"Move");
            msg.sendPrivMsg(nick, "Command: "+c.BOLD+"/msg "+Config.getNick()
                                +" move [destination type (island/location)] "
                                + "[destination name]");
            msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" islands "
                                +c.NORMAL+"for available islands.");
            msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" locations "
                                +c.NORMAL+"for available locations.");
        }
        else if (args.length >= 2){
            if(args[1].equals("island")){
                if (!player.getDynamicModel().getCurrentRoom().getRoomType().equals(Room.RoomType.DOCKS)){
                    msg.sendPrivMsg(nick, c.RED+"You need to be at the docks to move to another island.");  
                }
                else if(args.length == 2){
                    msg.sendPrivMsg(nick, c.RED+"Missing island name, try again.");
                    msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" islands "
                                        +c.NORMAL+"for available islands.");
                }
                else {
                    if(IslandSearch.findIslandByName(args[2])==null){
                        
                        msg.sendPrivMsg(nick, c.RED+"There is no island with that name, try again.");
                        msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" islands "
                                        +c.NORMAL+"for available islands.");
                    }
                    else {
                        
                        msg.sendPrivMsg(nick, "Join channel "+IslandSearch
                                    .findIslandByName(args[2]).getChannel()
                                    + " to travel there." );
                        
                        msg.inviteToChannel(nick, IslandSearch
                                        .findIslandByName(args[2]).getChannel());
                        
                    }
                }
            }
            else if(args[1].equals("location")){
                if(args.length == 2){
                    msg.sendPrivMsg(nick, c.RED+"Missing location name, try again.");
                    msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" locations "
                                        +c.NORMAL+"for available locations.");
                }
                else {
                    
                    String destLoc="";
                    
                    if (args.length == 3){                        
                        destLoc = args[2];                    
                    }
                    else if (args.length == 4){                     
                        destLoc = args[2] +" "+ args[3];
                    }
                        
                    
                        if(IslandSearch.findRoomByName(player.getDynamicModel()
                                           .getCurrentIsland(), destLoc)==null){
                            
                            msg.sendPrivMsg(nick, c.RED+"Either this location doesn't "
                                            + "exist or there is no such location "
                                            + "on your current island, try again");
                            
                            msg.sendPrivMsg(nick, "Type "+c.BOLD+"/msg "+Config.getNick()+" locations "
                                        +c.NORMAL+"for available locations.");
                        }
                        else if(player.getDynamicModel().getCurrentRoom().getName()
                                .toLowerCase().equals(destLoc.toLowerCase())){
                            
                            msg.sendPrivMsg(nick, c.RED+"You already are in that location.");
                        }
                        else {
                            msg.sendPrivMsg(nick, "Moving to "+c.YELLOW+IslandSearch
                                        .findRoomByName(player.getDynamicModel()
                                        .getCurrentIsland(), destLoc).getName()+c.NORMAL+ "...");
                            
                            player.getDynamicModel().setCurrentRoom(IslandSearch
                                        .findRoomByName(player.getDynamicModel()
                                        .getCurrentIsland(), destLoc));
                            
                            msg.sendPrivMsg(nick, "Done. You are now at "+c.YELLOW
                                    +player.getDynamicModel().getCurrentRoom().getName());
                        }
  
                }
            }
            else {
                msg.sendPrivMsg(nick, c.RED+"Invalid type, try again.");
                msg.sendPrivMsg(nick, "Available types: location, island");
            }
        }
        
    }
    
    
    public void availableIslands(){
        
        Room room = player.getDynamicModel().getCurrentRoom();
        
        if(!room.getRoomType().equals(Room.RoomType.DOCKS)){
            msg.sendPrivMsg(nick, c.RED+"No islands available because you're not at the docks.");   
        }
        else{
            
            msg.sendPrivMsg(nick, "Your current island: "
                            +player.getDynamicModel().getCurrentIsland().getName());
            msg.sendPrivMsg(nick, "Following islands are currently available to you: ");
            
            //List<Island> islands = Lists.islands;
            //Island playerIsland = player.getDynamicModel().getCurrentIsland();
            //islands.remove(playerIsland
                    
            List<Island> availableIslands = new ArrayList<Island>(Lists.islands);
            availableIslands.remove(player.getDynamicModel().getCurrentIsland());
            
            
            String listString0 = "";
            String listString1 = "";
            
            int br = 5;
            int j = 1;
            for (int i = 0; i < availableIslands.size(); i++) {                
                
                if (i < br) {
                    listString0 += c.BOLD+c.BROWN+" ["+c.YELLOW+j+++c.NORMAL
                            +" - " +availableIslands.get(i).getName()+c.BOLD+c.BROWN+"]"+c.NORMAL+" ";                 
                }
                else {
                    listString1 += c.BOLD+c.BROWN+" ["+c.YELLOW+j+++c.NORMAL
                            +" - " +availableIslands.get(i).getName()+c.BOLD+c.BROWN+"]"+c.NORMAL+" ";
                }

            }
            
            if(!listString0.isEmpty()){
                msg.sendPrivMsg(nick, listString0);
            }
            if(!listString1.isEmpty()){
                msg.sendPrivMsg(nick, listString1);
            }
            
            
        }
        
    }
    
    public void availableLocations(){
        
        //Island island = player.getDynamicModel().getCurrentIsland();
        
        
        msg.sendPrivMsg(nick, "Your current location: "
                            +player.getDynamicModel().getCurrentRoom().getName());
        msg.sendPrivMsg(nick, "Following locations are currently available to you: ");
            
            List<Room> islandRooms = new ArrayList<Room>(player.getDynamicModel()
                                                .getCurrentIsland().getRooms());
        
            islandRooms.remove(player.getDynamicModel().getCurrentRoom());
            
            String listString0 = "";
            String listString1 = "";
            
            int br = 5;
            int j = 1;
            for (int i = 0; i < islandRooms.size(); i++) {                
                
                if (i < br) {
                    listString0 += c.BOLD+c.BROWN+" ["+c.YELLOW+j+++c.NORMAL
                            +" - " +islandRooms.get(i).getName()+c.BOLD+c.BROWN+"]"+c.NORMAL+" ";                 
                }
                else {
                    listString1 += c.BOLD+c.BROWN+" ["+c.YELLOW+j+++c.NORMAL
                            +" - " +islandRooms.get(i).getName()+c.BOLD+c.BROWN+"]"+c.NORMAL+" ";
                }

            }
            
            if(!listString0.isEmpty()){
                msg.sendPrivMsg(nick, listString0);
            }
            if(!listString1.isEmpty()){
                msg.sendPrivMsg(nick, listString1);
            }
        
        
    }
    

    
}
