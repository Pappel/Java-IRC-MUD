package com.game.world;

import com.game.mob.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
public class Island implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="IID")
    private int IID;
    
    private String name;
    private String description;
    private String welcomeMsg;
    
    private int minLevel;
    private int maxLevel;
    
    private String channel;    

    @ElementCollection(fetch= FetchType.EAGER)
    @CollectionTable(name="ISLAND_ROOM", joinColumns=@JoinColumn(name="Island_IID"))
    private List<Room> rooms = new ArrayList<Room>();
    
    @Transient
    private List<Player> players = new ArrayList<Player>();
    
    
   
    
    
    public String getName() {
	return name;
    }
    public void setName(String name) {
	this.name = name;
    }
    
    
    public String getDescription() {
	return description;
    }	
    public void setDescription(String description) {
	this.description = description;
    }
    
    
    public String getWelcomeMsg() {
	return welcomeMsg;
    }	
    public void setWelcomeMsg(String welcomeMsg) {
	this.welcomeMsg = welcomeMsg;
    }
    
    
    public int getMinLevel() {
	return minLevel;
    }
    public void setMinLevel(int level) {
	this.minLevel = level;
    }
    
    
    public int getMaxLevel() {
	return maxLevel;
    }	
    public void setMaxLevel(int level) {
	this.maxLevel = level;
    }
    
    
    public String getChannel() {
	return channel;
    }	
    public void setChannel(String chan) {
	this.channel = chan;
    }
    
    
    
    public void addRoom(Room room){
        rooms.add(room);
    }
    
    public void addPlayer(Player player){
        players.add(player);
    }
    
    
    public void removePlayer(Player player){
        players.remove(player);
    }
    
    
        public List<Room> getRooms(){
        return rooms;
    }
    
    public List getPlayers(){
        return players;
    }
}
