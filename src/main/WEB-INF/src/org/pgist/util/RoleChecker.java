package org.pgist.util;

import java.util.Iterator;

import org.pgist.dao.UserDAO;
import org.pgist.users.Role;
import org.pgist.users.User;

/**
 * 
 * @author kenny
 *
 */
public class RoleChecker implements RbacChecker {

    
    public boolean checkRole(String[] roles) {
        User user = JSFUtil.getCurrentUser();
        UserDAO.refresh(user);
        for (Iterator iter=user.getRoles().iterator(); iter.hasNext(); ) {
            Role role = (Role) iter.next();
            UserDAO.refresh(role);
            for (int i=0; i<roles.length; i++) {
                if (role.getName().equals(roles[i])) return true;
            }//for i
        }//for iter
        
        return false;
    }
    
    
}//class RoleChecker
