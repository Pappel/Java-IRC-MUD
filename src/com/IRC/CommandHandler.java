package com.IRC;


import com.IRC.util.Colors;
import com.core.DB.dao.ItemDAO;
import com.core.DB.dao.ItemInstanceDAO;
import com.core.DB.dao.PlayerDAO;
import com.core.Lists;
import com.game.commands.*;
import com.game.items.Item;
import com.game.items.ItemInstance;
import com.game.mob.BodyPart;
import com.game.mob.Player;


// Commands are handled here

    
public class CommandHandler {
        
        Colors c;
    
        public MessageSender msg = new MessageSender();
        
        private String channel; // channel
        private String nick; // 
        
        
        
        
        
        // Incoming commands handled here
    	public void handleCommand(Message message) throws Exception {
            
		String[] messageParams = message.getTrailing().toLowerCase().split(" ");
                int messageType = message.getType();
                

                if(messageType == 1) {
                    
                    nick = message.getNick();
                    
                    // -- REGISTER --
                    if(messageParams[0].equals("register") ) {
                        Register register = new Register(nick, messageParams);
                    }
                    // -- LOGIN --
                    else if(messageParams[0].equals("login") ) {
                        Login login = new Login(nick, messageParams);
                    }
                    
                    
                    // -- COMMANDS THAT REQUIRE LOGIN --
                    else {

                        // -- LOGOUT --
                        if(messageParams[0].equals("logout") ) {
                            if(Lists.onlinePlayers.containsKey(nick)){
                                Logout logout = new Logout(nick, 1);
                            }
                            else{
                                msg.sendPrivMsg(nick, c.RED+"You are not logged in.");
                            }

                        }
                        
                        
                        // -- MOVEMENT --
                        else if (messageParams[0].equals("move")){
                            if(Lists.onlinePlayers.containsKey(nick)){
                                Move move = new Move(nick, messageParams);
                                move.move();
                            }
                            else{
                                msg.sendPrivMsg(nick, c.RED+"You need to be logged in to use this command.");
                            }
                        }
                        else if (messageParams[0].equals("islands")){
                            if(Lists.onlinePlayers.containsKey(nick)){
                                Move move = new Move(nick, messageParams);
                                move.availableIslands();
                            }
                            else{
                                msg.sendPrivMsg(nick, c.RED+"You need to be logged in to use this command.");
                            }
                        }
                        else if (messageParams[0].equals("locations")){
                            if(Lists.onlinePlayers.containsKey(nick)){
                                Move move = new Move(nick, messageParams);
                                move.availableLocations();
                            }
                            else{
                                msg.sendPrivMsg(nick, c.RED+"You need to be logged in to use this command.");
                            }
                        }
                        
                        
                        
                        else {
                            
                            msg.sendPrivMsg(nick, c.RED+"Invalid command.");
                            
                        }
                        
                        
                    }
                    
             
                } 
                
                
                
                
                
                
                else if (messageType == 2) {
                    
                    nick = message.getNick();
                    
                    if(messageParams[0].equals("somecommand1") ) {

                        msg.sendPrivMsg("#testbot", "Item/Equipment test"); 
                        Item item = new Item();
                        item.setName("testItem1");
                        item.setItid(1);
                        item.setDescription("testDescription");
                        item.setPartWornOn(BodyPart.PartType.HEAD);
                        
                        

                        if(messageParams[2].equals("rare")){
                            item.setItemClass(Item.ItemClass.RARE);
                        }
                        else if(messageParams[2].equals("unique")){
                            item.setItemClass(Item.ItemClass.UNIQUE);
                        }
                        else if(messageParams[2].equals("elite")){
                            item.setItemClass(Item.ItemClass.ELITE);
                        }
                        else{
                            item.setItemClass(Item.ItemClass.NORMAL);
                        }
                        
                        
                        msg.sendPrivMsg("#testbot", "Created test item:");
                        msg.sendPrivMsg("#testbot", "id: "+item.getItid()+
                                        ", name: "+item.getName()+
                                        ", description: "+item.getDescription()+
                                        ", partWornOn: "+item.getPartWornOn().toString()+
                                        ", itemClass: "+item.getItemClass().toString());
                        
                        
                        msg.sendPrivMsg("#testbot", "fetching player data");
                        Player player = new Player();
                        player = PlayerDAO.getByUsername(messageParams[1]);
                        
                        if (player != null){
                            
                            try {
                            msg.sendPrivMsg("#testbot", "current item on player's HEAD slot: "
                            +player.getBaseModel().getBody().getBodyParts().get(1).getEquippedItem().getItem().getName());
                            } catch (NullPointerException e){
                                msg.sendPrivMsg("#testbot", "exception: "+e);
                            }
                            
                            msg.sendPrivMsg("#testbot", "equipping new item");
                            
                            ItemInstance ii = new ItemInstance(player, item);
                            player.getBaseModel().getBody().getBodyParts().get(1).setEquippedItem(ii);
                            PlayerDAO.update(player);

                            msg.sendPrivMsg("#testbot", "new item on player's HEAD slot: "
                            +player.getBaseModel().getBody().getBodyParts().get(1).getEquippedItem().getItem().getName());
                        }
                        else{
                            msg.sendPrivMsg("#testbot", "moo, now enter a real name");
                        }
                        
                        
                    }
                    if(messageParams[0].equals("somecommand2") ) {

                        msg.sendPrivMsg("#testbot", "Item/Equipment test"); 
                        Item item = new Item();
                        item.setName(messageParams[1]);
                        item.setItid(1);
                        item.setDescription("testDescription");
                        item.setPartWornOn(BodyPart.PartType.HEAD);
                        
                        try{
                        if(messageParams[2].equals("rare")){
                            item.setItemClass(Item.ItemClass.RARE);
                        }
                        else if(messageParams[2].equals("unique")){
                            item.setItemClass(Item.ItemClass.UNIQUE);
                        }
                        else if(messageParams[2].equals("elite")){
                            item.setItemClass(Item.ItemClass.ELITE);
                        }
                        else{
                            item.setItemClass(Item.ItemClass.NORMAL);
                        }
                        }catch(ArrayIndexOutOfBoundsException e){
                           item.setItemClass(Item.ItemClass.NORMAL);
                        }catch(NullPointerException e){
                            item.setItemClass(Item.ItemClass.NORMAL);
                        }
                        
                        ItemDAO.create(item);
                        msg.sendPrivMsg("#testbot", "Created test item:");
                        msg.sendPrivMsg("#testbot", "id: "+item.getItid()+
                                        ", name: "+item.getName()+
                                        ", description: "+item.getDescription()+
                                        ", itemClass: "+item.getItemClass().toString());
                        
                        
                        Player player = new Player();
                        player = Lists.onlinePlayers.get(nick);
                        msg.sendPrivMsg("#testbot", "found player: "+player.getUsername());
                        ItemInstance ii = new ItemInstance(player, item);
                        ItemInstanceDAO.create(ii);
                        player.getDynamicModel().getInventory().addItem(ii);
                        PlayerDAO.update(player);
                        
                    }
                    if(messageParams[0].equals("somecommand3") ) {
                        
                        CMDInventory CMDi = new CMDInventory(nick);
                        
                    }

                }
                
        }
}