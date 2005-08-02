package org.pgist.backing;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.UserDAO;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.User;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Backing bean for managing the users
 * @author kenny
 *
 */
public class UserBean extends ListTableBean {
    
    
    private static final long serialVersionUID = 1L;
    private List users = null;


    public List getUsers() {
        return users;
    }
    
    
    public void setUsers(List users) {
        this.users = users;
    }


    /**
     * List All Users.
     * @return
     */
    public void listUser(ActionEvent event) {
        
        //if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        //pageSetting.set("nameFilter", nameFilter);
        try {
            users = UserDAO.getUserList(pageSetting);
        } catch(Exception e) {
            //return "dberror";
        }
        
        //return "listUser";
    }//listUser()
    
    
    /**
     * Add a new User
     * @return
     */
    public String addUser() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        /*
        Registry registry = new Registry();
        registry.setLoginname(loginname);
        registry.setOriginPassword(password);
        */
        
        User user = new User();
        user.setLoginname("");
        user.setEmail("");
        user.setOriginPassword("");
        user.setDeleted(false);
        user.setEnabled(true);

        try {
            UserDAO.addUser(user);
            return "success";
        } catch(UserExistException uee) {
        } catch(Exception e) {
        }
        
        return "failure";
    }//addUser()
    
    
    /**
     * delete users
     * @return
     */
    public void delUsers(ActionEvent event) {
        
        UserDAO.delUsers(selectedIds(User.class, "id"));
        
    }//delUsers()
    
    
    /**
     * enable roles
     * @return
     */
    public void enableUsers(ActionEvent event) throws Exception {
        
        UserDAO.enableUsers(selectedIds(User.class, "id"), true);
        
    }//enableUsers()
    
    
    /**
     * disable roles
     * @return
     */
    public void disableUsers(ActionEvent event) throws Exception {
        
        UserDAO.enableUsers(selectedIds(User.class, "id"), false);
        
    }//disableUsers()
    
    
    /**
     * edit a user info
     * @return
     */
    public String editUser() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            //User user = (User) UserDAO.load(User.class, userId);
            //UserDAO.editUser(user);
        } catch(Exception e) {
            return "listUser";
        }
        
        return null;
        
    }//editUser()
    
    
}//class UserBean
