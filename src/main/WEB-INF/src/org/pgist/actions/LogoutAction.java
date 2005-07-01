package org.pgist.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.users.User;


/**
 * PGIST LogoutAction class.
 * @author kenny
 */
public class LogoutAction extends Action {

    
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
        
        User user = (User) request.getSession().getAttribute("user");
        if (user==null) {
            return mapping.findForward("login");
        } else {
            request.getSession().setAttribute("user", null);
            return mapping.findForward("login");
        }
        
    }
    
    
}
