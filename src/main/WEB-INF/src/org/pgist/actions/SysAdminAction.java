package org.pgist.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.pgist.dao.UserDAO;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.PageSetting;


/**
 * User Administration Action Class.
 * @author kenny
 *
 */
public class SysAdminAction extends DispatchAction  {
    
    
    /**
     * Check if the current user has role of administrator
     * @param user
     * @return
     */
    private boolean checkRole(User user) {
        UserDAO.refresh(user);
        boolean hasAdminRole = false;
        for (Iterator iter=user.getRoles().iterator(); iter.hasNext(); ) {
            Role role = (Role) iter.next();
            if ("admin".equals(role.getName())) {
                hasAdminRole = true;
                break;
            }
        }//for iter
        return hasAdminRole;
    }
    
    
    /**
     * Process if no specific action is given.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    protected ActionForward unspecified(ActionMapping mapping,
            ActionForm form,
            javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response)
     throws java.lang.Exception {
        return mapping.findForward("main");
    }
    
    
    /**
     * List All Users.
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward listUser(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        
        if (!checkRole((User) request.getSession().getAttribute("user"))) {
            return mapping.findForward("notAdmin");
        }
        
        SysAdminForm aForm = (SysAdminForm) form;
        PageSetting setting = new PageSetting(aForm.getPage());
        setting.set("nameFilter", aForm.getNameFilter());
        List list = UserDAO.getUserList(true, setting);
        aForm.setUsers(list);
        
        return mapping.findForward("listUser");
    }//listUser()
    
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward addUser(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        if (!checkRole((User) request.getSession().getAttribute("user"))) {
            return mapping.findForward("notAdmin");
        }
        
        if ("GET".equals(request.getMethod())) {
            return mapping.findForward("userAdd");
        }
        
        UserForm aForm = (UserForm) form;
        
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

        if (UserDAO.insert(user)) {
            return mapping.findForward("success");
        } else {
            return mapping.findForward("failure");
        }
    }//addUser()
    
    
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward delUser(
            ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        if (!checkRole((User) request.getSession().getAttribute("user"))) {
            return mapping.findForward("notAdmin");
        }
        
        SysAdminForm aForm = (SysAdminForm) form;

        if (UserDAO.delUsers(aForm.getUserId())) {
            return mapping.findForward("listUser");
        }
        
        return null;
        
    }//delUser()
    
    
}
