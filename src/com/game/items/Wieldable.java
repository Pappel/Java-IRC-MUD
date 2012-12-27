package com.game.items;

import com.game.mob.BodyPart;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


@Entity
@Table(name="WIELDABLE")
public class Wieldable extends Item {

        
    
    /*
    public enum ItemClass {
        NORMAL, RARE, UNIQUE, ELITE
    }
    
    @Enumerated(EnumType.STRING)
    private ItemClass itemClass;
    public ItemClass getItemClass() { return itemClass; }
    public void setItemClass(ItemClass itemClass) { this.itemClass = itemClass; }
    
    
    @Enumerated(EnumType.STRING)
    private BodyPart.PartType bodyPartType;
    public BodyPart.PartType getBodyPartType() { return bodyPartType; }
    public void setBodyPartType(BodyPart.PartType bodyPartType) { this.bodyPartType = bodyPartType; }
 
 * 
 */
}
