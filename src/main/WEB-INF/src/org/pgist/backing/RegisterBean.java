package org.pgist.backing;


/**
 * Backing bean for register new user
 * @author kenny
 *
 */
public class RegisterBean {

    
    public String register() {
        /*
        Registry registry = new Registry();
        registry.setLoginname(loginname);
        registry.setOriginPassword(password);
        */
        
        /*
        User user = new User();
        user.setLoginname(loginname);
        user.setEmail(email);
        user.setOriginPassword(password);
        user.setDeleted(false);
        user.setEnabled(true);
        
        Role role = UserDAO.getRoleByName("member");
        user.getRoles().add(role);
        
        try {
            UserDAO.addUser(user);
            return mapping.findForward("success");
        } catch(UserExistException e) {
            request.setAttribute("PGISTMessage", e.getMessage());
        }
        
        */
        return null;
    }//register()
    
    
}//class RegisterBean
