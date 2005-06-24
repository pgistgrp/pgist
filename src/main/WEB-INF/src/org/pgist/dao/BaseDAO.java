package org.pgist.dao;

import org.hibernate.Session;
import org.pgist.util.HibernateUtil;

public class BaseDAO {

    
    /**
     * a generic method to insert new POJO into hibernate
     * @param object
     * @return
     */
    public static boolean insert(Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.save(object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }
    
    
    /**
     * a generic method to insert new POJO into hibernate
     * @param className
     * @param object
     * @return
     */
    public static boolean insert(String className, Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.save(className, object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }
    
    
    /**
     * a generic method to update existed POJO in hibernate
     * @param object
     * @return
     */
    public static boolean update(Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.update(object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }
    
    
    /**
     * a generic method to update existed POJO in hibernate
     * @param object
     * @return
     */
    public static boolean update(String className, Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.update(className, object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }
    
    
    /**
     * a generic method to insert a new POJO to hibernate or update existed POJO if it's already exist.
     * @param object
     * @return
     */
    public static boolean insertOrUpdate(Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.saveOrUpdate(object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }


    /**
     * a generic method to insert a new POJO to hibernate or update existed POJO if it's already exist.
     * @param object
     * @return
     */
    public static boolean insertOrUpdate(String className, Object object) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.saveOrUpdate(className, object);
            
            HibernateUtil.commit();
            
            return true;
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return false;
    }


}
