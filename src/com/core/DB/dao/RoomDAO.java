package com.core.DB.dao;

import com.core.DB.HibernateUtil;
import com.game.world.Room;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;


public abstract class RoomDAO {
    
    static final Logger logger = Logger.getLogger("RoomDAO");
    
   
    public static Room get(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            Room room = (Room) session.get(Room.class, id); 
            transaction.commit();
            return room;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Getting room failed: {0}", e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
        
    }
    
    public static void create(Room room){       
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.save(room);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Creating room failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void update(Room room){
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.update(room);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Updating room failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void delete(Room room){
       
    }
   
}
