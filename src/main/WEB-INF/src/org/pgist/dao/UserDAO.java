package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.users.User;
import org.pgist.util.HibernateUtil;


/**
 * DAO for query related to Users
 * @author kenny
 *
 */
public class UserDAO extends BaseDAO {

    
    /**
     * Get User object by the given name and query conditions
     * @param loginname
     * @param enabled
     * @param deleted
     * @return
     */
    public static User getUserByName(String loginname, boolean enabled, boolean deleted) {
        User user = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Query query = session.createQuery("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted");
            query.setString("loginname", loginname);
            query.setBoolean("enabled", true);
            query.setBoolean("deleted", false);
            List list = query.list();
            if (list.size()==0) return user;
            
            user = (User) list.get(0);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        
        return user;
    }
    
    
}
