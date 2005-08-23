package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.exceptions.RoleExistException;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


/**
 * DAO for query related to Users
 * @author kenny
 *
 */
public class UserDAO extends BaseDAO {

    
    public static Role getRoleByName(String roleName) throws Exception {
        Role role = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Query query = session.createQuery("from Role where name=:name and deleted=:deleted");
            query.setString("name", roleName);
            query.setBoolean("deleted", false);
            List list = query.list();
            if (list.size()>0) {
                role = (Role) list.get(0);
            }
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
        
        return role;
    }//getRoleByName()
    
    
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
            if (list.size()>0) {
                user = (User) list.get(0);
            }
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
     * Get all users, enabled or disabled
     * @param setting
     * @return
     * @throws Exception
     */
    public static List getUserList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from User as user where user.deleted=:deleted");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and loginname like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                hql.append(" order by user.id");
                query = session.createQuery(hql.toString());
                query.setBoolean("deleted", false);
                if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
                query.setFirstResult(setting.getFirstRow());
                query.setMaxResults(setting.getRowOfPage());
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
    
    
    /**
     * Get all users, enabled or disabled
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
                query.setMaxResults(setting.getRowOfPage());
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
    
    
    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public static void addUser(User user) throws UserExistException, Exception {
        addUser(user, null);
    }
    
    
    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public static void addUser(User user, String[] idList) throws UserExistException, Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted");
            
            Query query = session.createQuery(hql.toString());
            query.setString("loginname", user.getLoginname());
            query.setBoolean("enabled", true);
            query.setBoolean("deleted", false);
            
            List list = query.list();
            if (list.size()>0) {
                throw new UserExistException("User already exists!");
            }
            
            user.getRoles().clear();
            
            if (idList!=null) {
                for (int i=0; i<idList.length; i++) {
                    Role role = (Role) session.load(Role.class, new Long(idList[i]));
                    user.addRole(role);
                }//for i
            }
            
            if (user.getPassword().length()<=31) user.encodePassword();
            
            session.save(user);

            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//addUser()
    

    /**
     * Add a new user to system
     * @param user
     * @throws Exception
     */
    public static void updateUser(User user, String[] idList) throws UserExistException, Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            UserDAO.refresh(user);

            StringBuffer hql = new StringBuffer("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted and id!=:id");
            
            Query query = session.createQuery(hql.toString());
            query.setString("loginname", user.getLoginname());
            query.setBoolean("enabled", true);
            query.setBoolean("deleted", false);
            query.setLong("id", user.getId().longValue());
            
            List list = query.list();
            if (list.size()>0) {
                throw new UserExistException("User already exists!");
            }
            
            user.getRoles().clear();
            
            if (idList!=null) {
                for (int i=0; i<idList.length; i++) {
                    Role role = (Role) session.load(Role.class, new Long(idList[i]));
                    user.addRole(role);
                }//for i
            }
            
            if (user.getPassword().length()<=31) user.encodePassword();
            
            session.save(user);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//updateUser()
    

    /**
     * Delete users according to the idList. The deletion is a transaction
     * so that either all users are deleted or none of them is deleted.
     * @param idList
     * @return
     */
    public static boolean delUsers(List idList) {
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            for (int i=0, size=idList.size(); i<size; i++) {
                User user = (User) session.load(User.class, (Long)idList.get(i));
                user.setDeleted(true);
                user.setEnabled(false);
                session.update(user);
            }//for i
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        return false;
    }
    
    
    /**
     * Edit the infomation of a user
     * @param user
     * @throws Exception
     */
    public static void editUser(User user) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            if (user.getPassword().length()<=31) user.encodePassword();
            session.update(user);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }
    
    
    /**
     * Enable or disable selected users
     * @param idList
     * @param enable
     * @throws Exception
     */
    public static void enableUsers(List idList, boolean enable) throws Exception {
        if (idList==null || idList.size()==0) return;
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            for (int i=0, size=idList.size(); i<size; i++) {
                User user = (User) session.load(User.class, (Long)idList.get(i));
                user.setEnabled(enable);
                session.update(user);
            }//for i
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//enableUsers()
    
    
    /**
     * Get all roles
     * @param setting
     * @return
     * @throws Exception
     */
    public static List getRoleList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from Role where deleted=:deleted");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and name like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                query = session.createQuery(hql.toString());
                query.setBoolean("deleted", false);
                if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
                query.setFirstResult(setting.getFirstRow());
                query.setMaxResults(setting.getRowOfPage());
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
    
    
    /**
     * Get all roles
     * @param setting
     * @return
     * @throws Exception
     */
    public static List getRoleList() {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            String hql = "from Role where deleted=:deleted order by id";
            Query query = session.createQuery(hql);
            query.setBoolean("deleted", false);
            list = query.list();
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }

        return list;
    }
    
    
    /**
     * Add a new role to system
     * @param role
     * @throws Exception
     */
    public static void addRole(Role role) throws RoleExistException, Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Query query = session.createQuery("from Role where name=:name and deleted=:deleted");
            query.setString("name", role.getName());
            query.setBoolean("deleted", false);
            List list = query.list();
            if (list.size()>0) {
                throw new RoleExistException("Role already exists!");
            }
            
            session.save(role);

            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }
    
    
    /**
     * Delete roles according to the idList. The deletion is a transaction
     * so that either all roles are deleted or none of them is deleted.
     * @param idList
     * @return
     */
    public static boolean delRoles(List idList) {
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            for (int i=0, size=idList.size(); i<size; i++) {
                Role role = (Role) session.load(Role.class, (Long)idList.get(i));
                role.setDeleted(true);
                session.update(role);
            }//for i
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            e.printStackTrace();
        }
        return false;
    }
    
    
    /**
     * Delete role according to the id.
     * @param id
     * @return
     */
    public static boolean delRole(Long id) {
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Role role = (Role) session.load(Role.class, id);
            role.setDeleted(true);
            session.update(role);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
        return false;
    }
    
    
    /**
     * Edit the infomation of a role
     * @param user
     * @throws Exception
     */
    public static void editRole(Role role) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            session.update(role);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }
    
    
}//class UserDAO
