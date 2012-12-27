package com.core.DB.dao;

import com.core.DB.HibernateUtil;
import com.game.mob.Player;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public abstract class PlayerDAO {
    
    static final Logger logger = Logger.getLogger("PlayerDAO");
    
   
    public static Player getByID(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            Player player = (Player) session.get(Player.class, id); 
            transaction.commit();
            return player;
        } catch (Exception e) {
            logger.log(Level.WARNING, "Getting player failed: {0}", e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
       
    }
    
    public static Player getByUsername(String username){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Player.class)
                            .add(Restrictions.eq("username", username));
        
        try {
            Player player = (Player) criteria.uniqueResult();
            transaction.commit();
            return player;
        } catch (Exception e) {
            logger.log(Level.SEVERE, username+" doesn't exist: "+e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
           
    }
    
    public static void create(Player player){       
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.save(player);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Creating player failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void update(Player player){
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.update(player);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Updating player failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void delete(Player player){
       
    }
   
}
