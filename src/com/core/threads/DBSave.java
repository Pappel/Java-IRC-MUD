/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core.threads;

import com.core.DB.HibernateUtil;
import com.core.DB.dao.PlayerDAO;
import com.core.Lists;
import com.game.mob.Player;
import com.game.world.Island;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kasutaja
 */
public class DBSave implements Runnable{
    
    
    public DBSave(){
        
        Thread dbSave = new Thread(this);
        dbSave.setName("dbSave");
        dbSave.start();
        
    }
    

    @Override
    public void run() {
        while(true){
            
            try {
                Thread.currentThread().sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DBSave.class.getName()).log(Level.SEVERE, null, ex);
              
            }
            
            // Save players
            for (Player player : Lists.onlinePlayers.values()) {
                System.out.println("Saving: " + player.getUsername());
                PlayerDAO.update(player);
            }
            
            /*
            // Save islands
            for (Island island : Lists.islands) {
                System.out.println("Saving: " + island.getName());
                island.saveIsland();
            }
            * 
            */
            
        }
        
    }
    
}
