package com.core.DB.dao;

import com.core.DB.HibernateUtil;
import com.game.items.Item;
import com.game.items.ItemInstance;
import com.game.mob.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public abstract class ItemInstanceDAO {
    
    static final Logger logger = Logger.getLogger("ItemInstanceDAO");
    
    
    
    /*
    public static ItemInstance getByInvID(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            ItemInstance itemInstance = (ItemInstance) session.get(ItemInstance.class, id); 
            transaction.commit();
            return itemInstance;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Getting itemInstance failed: {0}", e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
       
    }
    * 
    */
    /*
    
    public static ItemInstance getByInvID(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(ItemInstance.class)
                            .add(Restrictions.eq("INVID", id));
        
        try {
            Item item = (Item) criteria.uniqueResult();
            transaction.commit();
            return item;
        } catch (Exception e) {
            logger.log(Level.SEVERE, name+" doesn't exist: "+e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
           
    }
    
    
    */
    public static void create(ItemInstance itemInstance){       
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.save(itemInstance);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Creating itemInstance failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    
    public static void update(ItemInstance itemInstance){
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.update(itemInstance);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Updating itemInstance failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    
    public static void delete(Item item){
       
    }

}