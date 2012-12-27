package com.game.mob;

import com.game.mob.backbone.Bank;
import com.game.mob.backbone.Equipment;
import com.game.mob.backbone.Inventory;
import com.game.world.Island;
import com.game.world.Room;
import java.io.Serializable;
import javax.persistence.*;



@Entity
public class DynamicModel implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DMID")
    private Integer DMID;
 
    // MOB's location

    @ManyToOne
    private Island currentIsland;
    @ManyToOne
    private Room currentRoom;
    
    // MOB's money and bank

    @OneToOne(cascade= CascadeType.ALL)
    private Bank money = new Bank();
    @OneToOne(cascade= CascadeType.ALL)
    private Bank bankAccount = new Bank();
    
    @OneToOne(cascade= CascadeType.ALL)
    private Inventory inventory = new Inventory();
    /*
    @OneToOne(cascade= CascadeType.ALL)        
    private Equipment equipment = new Equipment();
    * 
    */
    
    
    
    
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
	
    public Room getCurrentRoom() {
	return currentRoom;
    }
    
    
    public void setCurrentIsland(Island island) {
        this.currentIsland = island;
    }
	
    public Island getCurrentIsland() {
	return currentIsland;
    }

    /**
     * @return the money
     */
    public Bank getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(Bank money) {
        this.money = money;
    }

    /**
     * @return the bankAccount
     */
    public Bank getBankAccount() {
        return bankAccount;
    }

    /**
     * @param bankAccount the bankAccount to set
     */
    public void setBankAccount(Bank bankAccount) {
        this.bankAccount = bankAccount;
    }

    
    public Integer getDMID() {
        return DMID;
    }

    public void setDMID(Integer DMID) {
        this.DMID = DMID;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

       
}
