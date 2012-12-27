package com.game.mob;

import com.game.items.ItemInstance;
import java.io.Serializable;
import javax.persistence.*;



@Entity
public class BodyPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BPID")
    private Integer BPID;
    
    
	// Description of the body part.
	//private String name;
        @OneToOne(cascade= CascadeType.ALL)
        private ItemInstance equippedItem = null;

	public BodyPart() {}
	
	public BodyPart(PartType type) {
		this.partType = type;
	}

    /**
     * @return the equippedItem
     */
    public ItemInstance getEquippedItem() {
        return equippedItem;
    }

    /**
     * @param equippedItem the equippedItem to set
     */
    public void setEquippedItem(ItemInstance equippedItem) {
        this.equippedItem = equippedItem;
    }
        
        
        public enum PartType {
            HEAD, SHOULDERS, TORSO,
            ARMS, HANDS, LEGS,
            FEET, LEFT_HAND, RIGHT_HAND;
        }
        
        @Enumerated(EnumType.STRING)
        private PartType partType;
        public PartType getPartType() { return partType; }
        public void setPartType(PartType partType) { this.partType = partType; }

	/*
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
        * 
        */

    public Integer getBPID() {
        return BPID;
    }

    public void setBPID(Integer BPID) {
        this.BPID = BPID;
    }

}