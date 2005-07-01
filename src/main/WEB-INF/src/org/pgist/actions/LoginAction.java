package org.pgist.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.dao.UserDAO;
import org.pgist.users.User;


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
        
        if ("GET".equals(request.getMethod())) {
            return mapping.findForward("login");
        }
        
        LoginActionForm aForm = (LoginActionForm) form;
        
        try {
            User user = UserDAO.getUserByName(aForm.getLoginname(), true, false);
            if ( user.checkPassword(aForm.getPassword()) ) { //user is valid
                request.getSession().setAttribute("user", user);
                return mapping.findForward("success");
            } else { //user is invalid
                return mapping.findForward("failure");
            }
        } catch(Exception e) {
            return mapping.findForward("failure");
        }
    }
    

}
