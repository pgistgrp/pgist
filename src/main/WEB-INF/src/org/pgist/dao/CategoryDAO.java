package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.exceptions.CategoryExistException;
import org.pgist.glossary.TermCategory;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


/**
 * DAO for Category Management
 * @author kenny
 *
 */
public class CategoryDAO extends BaseDAO {

    
    public static List getCategoryList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from TermCategory as category");

            Query query = session.createQuery("select count(id) "+hql.toString());
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                hql.append(" order by category.id");
                query = session.createQuery(hql.toString());
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
    }//getCategoryList()
    

    /**
     * Add a new category
     * @param term
     * @throws Exception
     */
    public static void addCategory(TermCategory category) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from TermCategory where name=:name");
            
            Query query = session.createQuery(hql.toString());
            query.setString("name", category.getName());
            
            List list = query.list();
            if (list.size()>0) {
                throw new CategoryExistException("Category already exists!");
            }
            
            session.save(category);

            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//addCategory()


    /**
     * Update the specified category
     * @param category
     * @throws Exception
     */
    public static void updateCategory(TermCategory category) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            //UserDAO.refresh(user);

            StringBuffer hql = new StringBuffer("from TermCategory where name=:name and id!=:id");
            
            Query query = session.createQuery(hql.toString());
            query.setString("name", category.getName());
            query.setLong("id", category.getId().longValue());
            
            List list = query.list();
            if (list.size()>0) {
                throw new CategoryExistException("Category already exists!");
            }
            
            session.update(category);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//updateCategory()


    /**
     * delete all specified terms
     * @param idList
     */
    public static void delCategories(List idList) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            for (int i=0, size=idList.size(); i<size; i++) {
                TermCategory category = (TermCategory) session.load(TermCategory.class, (Long)idList.get(i));
                session.update(category);
            }//for i
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
    }//delCategories()


}//class CategoryDAO
