package com.game.commands;

import com.IRC.MessageSender;
import com.IRC.util.Colors;
import com.core.Config;
import com.core.DB.dao.IslandDAO;
import com.core.DB.dao.PlayerDAO;
import com.core.DB.dao.RoomDAO;
import com.core.Lists;
import com.game.mob.BaseModel.Gender;
import com.game.mob.Body;
import com.game.mob.Player;
import com.game.world.Island;
import java.util.Date;




public class Register {
    
    Colors c;
    
    private String nick;
    private String username;
    private String password;
    
    private Gender gender;
    
    private MessageSender msg;
    
    
    public Register(String pnick, String[] args) {
            
        this.nick = pnick;
        
        if (args.length < 2){
            msg.sendPrivMsg(nick, c.UNDERLINE+"Registration");
            msg.sendPrivMsg(nick, "1. Command: "+c.BOLD+"/msg "+Config.getNick()+" register "
                                + "[username] [password] [gender (male/female)]");
            msg.sendPrivMsg(nick, "2. Please use only letters and/or numbers.");
            msg.sendPrivMsg(nick, "3. Username must be 3-15 characters long.");
            msg.sendPrivMsg(nick, "4. Password must be 5-20 characters long.");
        }
        
        else if (args.length < 3){
            msg.sendPrivMsg(nick, c.RED+"Missing password and gender, try again.");
            msg.sendPrivMsg(nick, "Command: "+c.BOLD+"/msg "+Config.getNick()+" register [username] "
                                +c.RED+"[password] [gender (male/female)]"); 
        }
        
        else if (args.length < 4){
            msg.sendPrivMsg(nick, c.RED+"Missing gender, try again");
            msg.sendPrivMsg(nick, "Command: "+c.BOLD+"/msg "+Config.getNick()+" register [username] "
                                +"[password] "+c.RED+"[gender (male/female)]"); 
        }
        
        else {
            
            if (args[1].length() < 3){
                msg.sendPrivMsg(nick, c.RED+"Username can't be less than 3 characters long, try again.");
            }
            else if (args[1].length() > 15){
                msg.sendPrivMsg(nick, c.RED+"Username can't be more than 15 characters long, try again.");
            }
            //else if (newPlayer.usernameExists(args[1])){
            //    msg.sendPrivMsg(nick, c.RED+"Username already taken, try again.");
            //}
            
            else if (args[2].length() < 5){
                msg.sendPrivMsg(nick, c.RED+"Password can't be less than 5 characters long, try again.");                
            }
            else if (args[2].length() > 20){
                msg.sendPrivMsg(nick, c.RED+"Password can't be more than 20 characters long, try again.");                
            }
            
            else if (!args[3].equals("male") && !args[3].equals("female")){
                msg.sendPrivMsg(nick, c.RED+"Invalid gender, must be either "
                                    + c.UNDERLINE+"male "+c.NORMAL+c.RED+"or "
                                    + c.UNDERLINE+"female"+c.NORMAL+c.RED+", try again");
            }
            else {
                if(args[3].equals("male")){
                    this.username = args[1];
                    this.password = args[2];
                    this.gender = Gender.MALE;
                    save();
                }
                if(args[3].equals("female")){
                    this.username = args[1];
                    this.password = args[2];
                    this.gender = Gender.FEMALE;
                    save();
                }
            }
            
        }
        
    }

    
    
    void save(){
           
        Player newPlayer = new Player();
        
        newPlayer.setUsername(username);
        newPlayer.setPassword(password);
        newPlayer.setRegNick(nick);
        newPlayer.setCurNick(nick);
        
        newPlayer.getBaseModel().setGender(gender);
        
        Date date = new Date();
        newPlayer.setRegDate(date);
        newPlayer.setLastLogon(date);
        
        newPlayer.getDynamicModel().getMoney().setAmount(5000);
        
        

        
        newPlayer.getDynamicModel().setCurrentIsland(IslandDAO.getByID(1));
        newPlayer.getDynamicModel().setCurrentRoom(RoomDAO.get(1));
        
        
        
              
        PlayerDAO.create(newPlayer);
        
        
        Lists.onlinePlayers.put(nick, newPlayer);
        
        welcomeMsg();
        
        
        msg.sendPrivMsg(nick, "Please join the channel "
                        +c.BROWN+newPlayer.getDynamicModel().getCurrentIsland().getChannel()
                        +c.NORMAL+" immediately or you will be logged out!");
        //msg.inviteToChannel(nick, i.getChannel());
    }
    
    void welcomeMsg(){
        
        msg.sendPrivMsg(nick, "Welcome to "+c.YELLOW+"The Forsaken Isles"+c.NORMAL+", "+c.GREEN+username);
        
        // some information here
        
        msg.sendPrivMsg(Config.getChannel(), c.GREEN+username+c.NORMAL+" has entered realm of "
                + c.YELLOW+"The Forsaken Isles"+c.NORMAL+". Welcome new adventurer!");
    }
    
}
