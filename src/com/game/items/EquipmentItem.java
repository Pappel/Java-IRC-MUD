package com.game.items;

import com.game.mob.BodyPart;
import java.io.Serializable;
import javax.persistence.*;


@Entity
public class EquipmentItem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EQIID")
    private Integer EQIID;
    
    private BodyPart part;
    private Item item;
		
    public EquipmentItem() {}
    
    public EquipmentItem(BodyPart part, Item item) {
        this.part = part;
        this.item = item;
    }
		

    public BodyPart getBodyPart() { return part; }
    public void setBodyPart(BodyPart part) { this.part = part; }
		

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
}

