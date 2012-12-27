package com.game.mob.backbone;

import com.game.items.ItemInstance;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class ItemIterator implements Iterator<ItemInstance> {
	private int pos;
	private List<ItemInstance> items;
	
	
	public ItemIterator(Inventory inv) {
		pos = 0;
		initInventory(inv);
	}
	
        /*
	public ItemIterator(Equipment equipment) {
		pos = 0;
		initEquipment(equipment);
	}
	
	private void initEquipment(Equipment eq) {
		items = new ArrayList<Item>(eq.size());
		
		for (Item item : eq.equipment.values()) {
			items.add(item);
		}
	}
        * 
        */
	
	private void initInventory(Inventory inv) {
		items = new ArrayList<ItemInstance>(inv.size());
		
		for (ItemInstance item : inv.getItems()) {
			items.add(item);
		}
	}
	
	public boolean hasNext() {
		return (pos < items.size());
	}

	public ItemInstance next() {
		return items.get(pos++);
	}

	public void remove() {
		throw new UnsupportedOperationException("this iterator does not support removal");
	}

}