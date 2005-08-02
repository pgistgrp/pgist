package org.pgist.util;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.pgist.dao.UserDAO;
import org.pgist.users.Role;
import org.pgist.users.User;

public class JSFUtil {

    
    public static HttpSession getSession(boolean create) {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(create);
    }
    
    
    public static boolean checkAdmin() {
        HttpSession session = getSession(false);
        
        boolean hasAdminRole = false;
        if (session!=null) {
            User user = (User) session.getAttribute("user");
            if (user!=null) {
                UserDAO.refresh(user);
                for (Iterator iter=user.getRoles().iterator(); iter.hasNext(); ) {
                    Role role = (Role) iter.next();
                    if ("admin".equals(role.getName())) {
                        hasAdminRole = true;
                        break;
                    }
                }//for iter
            }
        }
        
        return hasAdminRole;
    }
    
    
}
