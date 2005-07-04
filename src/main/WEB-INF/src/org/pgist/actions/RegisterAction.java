package org.pgist.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.pgist.dao.UserDAO;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * PGIST RegisterAction class.
 * @author kenny
 *
 */
public class RegisterAction extends Action {

    
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
            return mapping.findForward("register");
        }
        
        UserForm aForm   = (UserForm) form;
        
        String loginname = aForm.getLoginname();
        String password  = aForm.getPassword();
        String password1 = aForm.getPassword1();
        String email = aForm.getEmail();
        
        //check if password is valid
        if (password==null || "".equals(password)) {
            System.out.println("password invlid");
            return mapping.findForward("failure");
        }
        
        //check if password is matched
        if (!password.equals(password1)) {
            System.out.println("password not match");
            return mapping.findForward("failure");
        }
        
        //check if password is valid
        if (email==null || "".equals(email)) {
            System.out.println("email invlid");
            return mapping.findForward("failure");
        }

        /*
        Registry registry = new Registry();
        registry.setLoginname(loginname);
        registry.setOriginPassword(password);
        */
        
        User user = new User();
        user.setLoginname(loginname);
        user.setEmail(email);
        user.setOriginPassword(password);
        user.setDeleted(false);
        user.setEnabled(true);
        
        Role role = UserDAO.getRoleByName("member");
        user.getRoles().add(role);
        
        if (UserDAO.insert(user)) {
            return mapping.findForward("success");
        } else {
            return mapping.findForward("failure");
        }
    }
    
    
}
