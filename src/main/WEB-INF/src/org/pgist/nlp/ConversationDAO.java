package org.pgist.nlp;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.util.HibernateUtil;
import org.pgist.util.PageSetting;


/**
 * DAO class for Conversation
 * @author kenny
 *
 */
public class ConversationDAO {

    
    /**
     * Get all users, enabled or disabled
     * @param enabled
     * @return
     * @throws Exception
     */
    public static List getConversationList(PageSetting setting) throws Exception {
        List list = null;
        
        try {
            Session session = HibernateUtil.getSession();
            HibernateUtil.begin();
            
            StringBuffer hql = new StringBuffer("from ConversationThread as conversation");
            String nameFilter = (String) setting.get("nameFilter");
            if (nameFilter!=null && !"".equals(nameFilter)) hql.append(" and loginname like :nameFilter");
            
            Query query = session.createQuery("select count(id) "+hql.toString());
            if (nameFilter!=null && !"".equals(nameFilter)) query.setString("nameFilter", "%"+nameFilter+"%");
            list = query.list();
            
            if (list.size()>0) {
                setting.setRowSize(((Integer)list.get(0)).intValue());
                
                hql.append(" order by conversation.id desc");
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
    }//getConversationList()

    
}//class ConversationDAO
