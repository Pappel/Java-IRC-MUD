package com.core.DB;

//import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;


/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public abstract class HibernateUtil {
    //private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().configure("/config/hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        // Alternatively, we could look up in JNDI here
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

    public static List fetch(DetachedCriteria detachedCriteria) {
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Criteria criteria = detachedCriteria.getExecutableCriteria(session);

        try {
            return criteria.list();
        } catch (Exception e) {
            //LOGGER.error("Error retrieving data for criteria", e);
            return null;
        } finally {
            transaction.commit();
            session.close();
        }
    }

    public static void save(Object obj) {
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        try {
            session.save(obj);
            transaction.commit();
        } catch (Exception e) {
            //LOGGER.error("Error saving object", e);
            System.out.println(e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    public static void delete(Object obj) {
        Session session;
        Transaction transaction;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        try {
            session.delete(obj);
            transaction.commit();
        } catch (Exception e) {
            //LOGGER.error("Error deleting object", e);
            transaction.rollback();
        } finally {
            session.close();
        }
    }
}