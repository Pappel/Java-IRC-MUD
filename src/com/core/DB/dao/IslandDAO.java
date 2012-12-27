package com.core.DB.dao;

import com.core.DB.HibernateUtil;
import com.core.Lists;
import com.game.world.Island;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


public abstract class IslandDAO {
    
    static final Logger logger = Logger.getLogger("IslandDAO");
    
   
    public static Island getByID(int id){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            Island island = (Island) session.get(Island.class, id); 
            transaction.commit();
            return island;
        } catch (Exception e) {
            Island island = (Island) session.get(Island.class, id); 
            transaction.commit();
            logger.log(Level.WARNING, "Getting island failed: {0}", e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
        
    }
    
    public static Island getByChannel(String channel){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Criteria criteria = session.createCriteria(Island.class)
                            .add(Restrictions.eq("channel", channel));
        
        try {
            Island island = (Island) criteria.uniqueResult();
            transaction.commit();
            return island;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Island with channel: "+channel+" doesn't exist: "+e);
            transaction.rollback();
            return null;
        } finally {
            session.close();
        }
           
    }
    
    public static void create(Island island){       
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.save(island);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Creating island failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void update(Island island){
        Session session;
        Transaction transaction;
        
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        try {
            session.update(island);
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Updating island failed: "+e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
    
    public static void delete(Island island){
       
    }
    
    
    
    
    
    public static void loadIslands(){
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        
        try {
            Criteria crit = session.createCriteria(Island.class);
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            Lists.islands = crit.list();
            transaction.commit();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Could not load islands: {0}", e);
            transaction.rollback();
        } finally {
            session.close();
        }
        
    }
   
}
