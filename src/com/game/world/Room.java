package com.game.world;

import com.game.mob.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
public class Room implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RID")
    private int RID;
        
    private String name;
    private String description;

    @Transient
    private List<Player> players = new ArrayList<Player>();
    
    
    public enum RoomType {
        DOCKS, FOREST, VILLAGE, CITY, CAMP
    }
    
    
    @Enumerated(EnumType.STRING)
    private RoomType roomType;
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    
    
    
    
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
    
    
    public void addPlayer(Player player){
        players.add(player);
    }
    public void removePlayer(Player player){
        players.remove(player);
    }
    
}
