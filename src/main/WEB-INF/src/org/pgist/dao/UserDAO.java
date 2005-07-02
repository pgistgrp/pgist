package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.users.User;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


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
    public static User getUserByName(String loginname, boolean enabled, boolean deleted) throws Exception {
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
            throw e;
        }
        
        return user;
    }
    
    
    /**
     * Get all user, enabled or disabled
     * @param enabled
     * @return
     * @throws Exception
     */
    public static List getUserList(boolean enabled, PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from User where enabled=:enabled and deleted=:deleted");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and loginname like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            query.setBoolean("enabled", enabled);
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                query = session.createQuery(hql.toString());
                query.setBoolean("enabled", enabled);
                query.setBoolean("deleted", false);
                if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
                query.setFirstResult(setting.getFirstRow());
                query.setFetchSize(setting.getRowOfPage());
                list = query.list();
            }

            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }

        return list;
    }
    
    
}
