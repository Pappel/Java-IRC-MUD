package com.game.items;

import com.game.mob.Player;
import java.io.Serializable;
import javax.persistence.*;


@Entity
public class ItemInstance implements Serializable {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITINID")
    private Integer itinid;
    
    @OneToOne
    private Item item;
    @OneToOne
    private Player owner;
    private int amount = 1;

    public ItemInstance() {
    }

    public ItemInstance(Player owner, Item item) {
        this.owner = owner;
        this.item = item;
    }

    
    
    public Integer getItinid() {
        return itinid;
    }

    public void setItinid(Integer itinid) {
        this.itinid = itinid;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }


    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
