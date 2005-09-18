package org.pgist.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.exceptions.TermExistException;
import org.pgist.glossary.Term;
import org.pgist.glossary.TermCategory;
import org.pgist.glossary.TermSource;
import org.pgist.util.HibernateUtil;
import org.pgist.util.JSFUtil;
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

            String categoryFilter = (String) setting.get("categoryFilter");
            if (categoryFilter==null || "".equals(categoryFilter)) {
                hql.append(" and term.categories is empty");
            } else {
                hql.append(" and term.categories.id in (")
                   .append(categoryFilter)
                   .append(")");
            }
            
            Query query = session.createQuery("select distinct count(term.id) "+hql.toString());
            query.setBoolean("deleted", false);
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            //if (categoryFilter!=null && !"".equals(categoryFilter)) {
            //    query.setString("categoryFilter", categoryFilter);
            //}
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                hql.append(" order by term.id");
                query = session.createQuery("select distinct term "+hql.toString());
                query.setBoolean("deleted", false);
                if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
                //if (categoryFilter!=null && !"".equals(categoryFilter)) {
                //    query.setString("categoryFilter", categoryFilter);
                //}
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
     * @param selectedCategories 
     * @throws Exception
     */
    public static void addTerm(Term term, String[] selectedCategories, String[] sources) throws Exception {
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
            
            term.getCategories().clear();
            if (selectedCategories!=null) {
                for (int i=0; i<selectedCategories.length; i++) {
                    TermCategory category = (TermCategory)session.load(TermCategory.class, new Long(selectedCategories[i]));
                    term.getCategories().add(category);
                }//for i
            }
            
            for (int i=0; i<sources.length; i++) {
                if (sources[i]!=null) {
                    sources[i] = sources[i].trim();
                    if (!"".equals(sources[i])) {
                        TermSource one = new TermSource();
                        one.setSource(sources[i]);
                        session.save(one);
                        term.getSources().add(one);
                    }
                }
            }//for i

            term.setOwner(JSFUtil.getCurrentUser());
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
     * @param selectedCategories 
     * @throws Exception
     */
    public static void updateTerm(Term term, String[] selectedCategories, String[] sources) throws Exception {
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
            
            term.getCategories().clear();
            if (selectedCategories!=null) {
                for (int i=0; i<selectedCategories.length; i++) {
                    TermCategory category = (TermCategory)session.load(TermCategory.class, new Long(selectedCategories[i]));
                    term.getCategories().add(category);
                }//for i
            }
            
            for (Iterator iter=term.getSources().iterator(); iter.hasNext(); ) {
                session.delete(iter.next());
            }//for iter
            term.getSources().clear();
            for (int i=0; i<sources.length; i++) {
                if (sources[i]!=null) {
                    sources[i] = sources[i].trim();
                    if (!"".equals(sources[i])) {
                        TermSource one = new TermSource();
                        one.setSource(sources[i]);
                        session.save(one);
                        term.getSources().add(one);
                    }
                }
            }//for i

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


    public static List getAllCategories() throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Query query = session.createQuery("from TermCategory as category order by category.id");
            list = query.list();
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }

        return list;
    }//getAllCategories()


}//class GlossaryDAO
