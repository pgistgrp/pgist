package org.pgist.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Query;
import org.hibernate.Session;
import org.pgist.users.User;
import org.pgist.util.HibernateUtil;


/**
 * PGIST LoginAction class.
 * @author kenny
 */
public class LoginAction extends Action {

    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward execute(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        LoginActionForm aForm = (LoginActionForm) form;
        String loginname = aForm.getLoginname();
        
        Session session = HibernateUtil.getSession();
        try {
            HibernateUtil.begin();
            
            Query query = session.createQuery("from User where loginname=:loginname and enabled=:enabled and deleted=:deleted");
            query.setString("loginname", loginname);
            query.setBoolean("enabled", true);
            query.setBoolean("deleted", false);
            List list = query.list();
            if (list.size()==0) return mapping.findForward("failure");
            
            User user = (User) list.get(0);
            if ( user.checkPassword(aForm.getPassword()) ) {
                request.getSession().setAttribute("user", user);
                return mapping.findForward("success");
            }
            
            HibernateUtil.commit();
        } catch (Exception e) {
            try {
                HibernateUtil.rollback();
            } catch(Exception ex) {
            }
        }

        return mapping.findForward("failure");
        
    }
    

}
