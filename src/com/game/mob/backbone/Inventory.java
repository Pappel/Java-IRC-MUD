package com.game.mob.backbone;

import com.game.items.ItemInstance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Inventory implements Iterable<ItemInstance>, Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="INVID")
    private Integer INVID;
    
    //@ElementCollection
    //@LazyCollection(LazyCollectionOption.FALSE)
    //@CollectionTable(name="INV_ITEM", joinColumns=@JoinColumn(name="INVID"))
    
    //@ElementCollection(fetch= FetchType.EAGER)
    //@CollectionTable(name="INV_ITEM", joinColumns=@JoinColumn(name="INVID"))
    @OneToMany(cascade= CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ItemInstance> inv;
    
    private int capacity;
    
    
    
    public Inventory() {
        inv = new ArrayList<ItemInstance>(25);
	capacity = 25;
    }
    

    public int getCapacity() {
        return capacity;
    }
	
    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }
    
    
    public void removeItem(ItemInstance itemInstance) {
        inv.remove(itemInstance);
    }
    
    public void addItem(ItemInstance itemInstance) {
	inv.add(itemInstance);
    }
    
    public ItemInstance getItemByName(String name) {

        if (name.length() < 2) {
            return null;
        }
		
        name = name.toLowerCase();
	
        for (ItemInstance itemInstance : inv) {
            if (itemInstance.getItem().getName().toLowerCase().indexOf(name) != -1) {
                return itemInstance;
            }
        }
        return null;
    }
    
    
    public List<ItemInstance> getItems() {
	return inv;
    }
    
    
    
    public int size() {
	return inv.size();
    }
    
    
    public Iterator<ItemInstance> iterator() {
        return new ItemIterator(this);
    }
    
}
