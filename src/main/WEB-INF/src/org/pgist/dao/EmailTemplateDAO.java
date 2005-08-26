package org.pgist.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.emails.EmailTemplate;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


/**
 * DAO for query related to email templates
 * @author kenny
 *
 */
public class EmailTemplateDAO extends BaseDAO {

    
    /**
     * get template list
     * @param pageSetting
     * @return
     */
    public static List getTemplateList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from EmailTemplate");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append("where name like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                query = session.createQuery(hql.toString());
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
    }//getTemplateList()
    

    public static void updateTemplate(EmailTemplate template) throws Exception {
        update(template);
    }//updateTemplate()


    public static EmailTemplate getTemplateByName(String name) throws Exception {
        EmailTemplate template = null;
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            Query query = session.createQuery("from EmailTemplate where name=:name");
            query.setString("name", name);
            List list = query.list();
            if (list.size()>0) {
                template = (EmailTemplate) list.get(0);
            }
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
            throw e;
        }
        
        return template;
    }//getTemplateByName()

    
}//class EmailTemplateDAO
