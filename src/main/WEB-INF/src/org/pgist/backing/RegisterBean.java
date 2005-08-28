package org.pgist.backing;

import org.pgist.dao.UserDAO;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * Backing bean for register new user
 * @author kenny
 *
 */
public class RegisterBean {

    
    private User user;

    
    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
    
    
    /**
     * GoTo the "Register User" page
     * @return
     */
    public String registerUser() {
        
        user = new User();
        user.setEnabled(true);
        user.setInternal(false);
        user.setDeleted(false);
        
        return "register";
    }//addUser()
    
    
    /**
     * execute "Register User"
     * @return
     */
    public String doRegisterUser() {
        try {
            Role role = UserDAO.getRoleByName("member");
            user.getRoles().add(role);
            if (user.getPassword().length()<32) user.encodePassword();
            UserDAO.insert(user);
            return "success";
        } catch(UserExistException uee) {
            uee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }//doRegisterUser()


}//class RegisterBean
