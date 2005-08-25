package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.exceptions.TermExistException;
import org.pgist.glossary.Term;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


/**
 * DAO for query related to Glossary
 * 
 * @author kenny
 *
 */
public class GlossaryDAO extends BaseDAO {

    
    /**
     * Get all glossaries
     * @return
     * @throws Exception
     */
    public static List getTermList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from Term as term where term.deleted=:deleted");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and name like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                hql.append(" order by term.id");
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
    }//getTermList()
    
    
    /**
     * Add a new term to glossary
     * @param term
     * @throws Exception
     */
    public static void addTerm(Term term) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from Term where name=:name and deleted=:deleted");
            
            Query query = session.createQuery(hql.toString());
            query.setString("name", term.getName());
            query.setBoolean("deleted", false);
            
            List list = query.list();
            if (list.size()>0) {
                throw new TermExistException("Term already exists!");
            }
            
            session.save(term);

            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//addTerm()


    /**
     * Update the specified term
     * @param term
     * @throws Exception
     */
    public static void updateTerm(Term term) throws Exception {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            //UserDAO.refresh(user);

            StringBuffer hql = new StringBuffer("from Term where name=:name and deleted=:deleted and id!=:id");
            
            Query query = session.createQuery(hql.toString());
            query.setString("name", term.getName());
            query.setBoolean("deleted", false);
            query.setLong("id", term.getId().longValue());
            
            List list = query.list();
            if (list.size()>0) {
                throw new TermExistException("Term already exists!");
            }
            
            session.update(term);
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
    }//updateTerm()


    /**
     * delete all specified terms
     * @param idList
     */
    public static void delTerms(List idList) {
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            for (int i=0, size=idList.size(); i<size; i++) {
                Term term = (Term) session.load(Term.class, (Long)idList.get(i));
                term.setDeleted(true);
                session.update(term);
            }//for i
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }
    }//delTerms()


}//class GlossaryDAO
