package com.game.mob;

import com.core.DB.dao.PlayerDAO;
import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "MOB")
@Inheritance(strategy = InheritanceType.JOINED)
public class Mob implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MID")
    private Integer mid;
    
    @OneToOne(cascade= CascadeType.ALL)
    private BaseModel baseModel = new BaseModel();
    @OneToOne(cascade= CascadeType.ALL)
    private DynamicModel dynamicModel = new DynamicModel();
    //private MobileCombatModel combatModel = new MobileCombatModel();
    
    
    public Mob() {
        baseModel = new BaseModel();
	dynamicModel = new DynamicModel();
	//combatModel = new MobileCombatModel();
    }
    
    public Mob(BaseModel base, DynamicModel dynamic) {
	this.baseModel = base;
	this.dynamicModel = dynamic;
	//combatModel = combat;
    }
    
    public BaseModel getBaseModel() {
	return baseModel;
    }	
    public void setBaseModel(BaseModel model) {
        baseModel = model;
    }
	
    public DynamicModel getDynamicModel() {
        return dynamicModel;
    }	
    public void setDynamicModel(DynamicModel model) {
	dynamicModel = model;
    }
    
    
    public Integer getMID(){
        return mid;
    }
    public void setMID(int id){
        this.mid = id;
    }
    
    /**
     * Checks to see if this Mobile is a player.
     * @return true or false
     */
    public boolean isPlayer() {
        //TODO implement isPlayer
	//return this instanceof PlayerCharacter;
	return false;
    }

    /**
     * Checks to see if this Mobile is an NPC.
     * @return true or false
     */
    public boolean isNPC() {
	//TODO implement isNPC
	//return this instanceof NPC;
	return false;
    }
    
   
    
    
    
    
/*    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.mid != null ? this.mid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mob)) {
            return false;
        }
        Mob other = (Mob) object;
        if (this.mid != other.mid || 
                (this.mid == null || !this.mid.equals(other.mid))) {
        
            return false;
        }
        return true;
    }  
    
    @Override
    public String toString() {
        return "com.sample.entity.UserDetail[userId=" + mid + "]";
    }
    * 
    */
}
