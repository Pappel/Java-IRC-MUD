package com.game.commands;

import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Lists;
import com.game.items.Item;
import com.game.items.ItemInstance;
import com.game.mob.BodyPart;
import com.game.mob.Player;
import com.game.mob.backbone.Inventory;
import org.apache.commons.lang.StringUtils;


public class CMDInventory {
  
    Colors c;
    
    String nick;
    Player player;
    Inventory inventory;
    MessageSender msg;
    
    
    public CMDInventory(String nick){
        this.nick = nick;
        this.player = Lists.onlinePlayers.get(nick);
        this.inventory = player.getDynamicModel().getInventory();
        
        
        
        String listString0 = "";
        String itemName = "";
        int itemAmount;
        Item.ItemClass itemClass;

                
        msg.sendPrivMsg("#testbot", c.BOLD+c.UNDERLINE+"Your inventory");
        msg.sendPrivMsg("#testbot", " ");
        
        for( int row = 0; row < inventory.getCapacity()/5; row++ ) {  
            listString0= "";
            for( int col = 0; col < 5; col++ ) {
                
                try{
                    itemName = inventory.getItems().get(col +( 5 * row)).getItem().getName();
                    itemAmount = inventory.getItems().get(col +( 5 * row)).getAmount();
                    itemClass = inventory.getItems().get(col +( 5 * row)).getItem().getItemClass();
                    
                    if(itemClass.equals(Item.ItemClass.ELITE)){
                        listString0 +=  c.BOLD+c.BROWN+"[" +c.PURPLE+c.BOLD
                                    +StringUtils.center(itemAmount+"x "+itemName, 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                        
                    }
                    else if(itemClass.equals(Item.ItemClass.RARE)){
                        listString0 +=  c.BOLD+c.BROWN+"[" +c.GREEN+c.BOLD
                                    +StringUtils.center(itemAmount+"x "+itemName, 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                        
                    }
                    else if(itemClass.equals(Item.ItemClass.UNIQUE)){
                        listString0 +=  c.BOLD+c.BROWN+"[" +c.YELLOW+c.BOLD
                                    +StringUtils.center(itemAmount+"x "+itemName, 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                        
                    }
                    else {
                        listString0 +=  c.BOLD+c.BROWN+"[" +c.NORMAL+c.BOLD
                                    +StringUtils.center(itemAmount+"x "+itemName, 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                    }
                    
                    
                }catch (NullPointerException e){
                    //listString0 += c.BOLD+c.BROWN+"["+c.NORMAL+"_____________"+c.BOLD+c.BROWN+"]";
                    listString0 +=  c.BOLD+c.BROWN+"[" +c.NORMAL+c.BOLD
                                    +StringUtils.center("", 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                }catch (IndexOutOfBoundsException f){
                    //listString0 += c.BOLD+c.BROWN+"["+c.NORMAL+"_____________"+c.BOLD+c.BROWN+"]";
                    listString0 +=  c.BOLD+c.BROWN+"[" +c.NORMAL+c.BOLD
                                    +StringUtils.center("", 17, '-')
                                    +c.NORMAL+c.BOLD+c.BROWN+"]";
                }

                
            }  
            msg.sendPrivMsg("#testbot", listString0);
        } 
        
    }
    
    
    
}
