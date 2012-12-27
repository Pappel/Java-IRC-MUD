package com.game.items;

import com.game.mob.BodyPart;
import java.io.Serializable;
import javax.persistence.*;


@Entity
public class Item implements Serializable {
      
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITID")
    private Integer itid;
    
    private String name;
    private String description;
    private boolean stackable = false;
    
    @Enumerated(EnumType.STRING)
    private BodyPart.PartType partWornOn;
    
    
    
    
    public String getName() {
	return name;
    }
	
    public void setName(String name) {
	this.name = name;
    }
    
    
    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }
    
    
    public boolean isStackable() {
	return stackable;
    }


    public Integer getItid() {
        return itid;
    }

    public void setItid(Integer itid) {
        this.itid = itid;
    }

    public BodyPart.PartType getPartWornOn() {
        return partWornOn;
    }


    public void setPartWornOn(BodyPart.PartType partWornOn) {
        this.partWornOn = partWornOn;
    }

    
    public enum ItemClass {
        NORMAL, RARE, UNIQUE, ELITE
    }
    
    @Enumerated(EnumType.STRING)
    private ItemClass itemClass;
    public ItemClass getItemClass() { return itemClass; }
    public void setItemClass(ItemClass itemClass) { this.itemClass = itemClass; }
    
    
	
}
