package com.core.DB.dao;

import com.core.DB.HibernateUtil;
import com.game.items.Item;
import com.game.mob.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public abstract class ItemDAO {
    
    static final Logger logger = Logger.getLogger("ItemDAO");
    
    
    
    
    public static Item getByID(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            Item item = (Item) session.get(Item.class, id); 
            transaction.commit();
            return item;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Getting item failed: {0}", e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
       
    }
    
    
    public static Item getByName(String name){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Item.class)
                            .add(Restrictions.eq("name", name));
        
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
    
    
    
    public static void create(Item item){       
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.save(item);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Creating item failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    
    public static void update(Item item){
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Updating item failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    
    public static void delete(Item item){
       
    }
    
}