package org.pgist.backing;

import java.util.List;
import java.util.Map;

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
    
    
    private List users = null;
    private User user = new User();
    private String[] selectedRoles;


    public List getUsers() {
        return users;
    }
    
    
    public void setUsers(List users) {
        this.users = users;
    }
    
    
    public User getUser() {
        return user;
    }
    
    
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * List All Users.
     * @return
     */
    public void listUser(ActionEvent event, Map map) {
        
        try {
            users = UserDAO.getUserList(pageSetting);
        } catch(Exception e) {
        }
        
    }//listUser()
    
    
    /**
     * GoTo the "Add User" page
     * @return
     */
    public String addUser() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        user = new User();
        user.setEnabled(true);
        user.setInternal(false);
        user.setDeleted(false);
        
        return "addUser";
    }//addUser()
    
    
    /**
     * GoTo the "Edit User" page
     * @return
     */
    public String editUser() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        try {
            user = (User) UserDAO.load(User.class, selectedId());
        } catch(Exception e) {
        }
        
        return "editUser";
        
    }//editUser()
    
    
    /**
     * Save a new/modified User
     * @return
     */
    public String saveUser() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            if (user.getId()==null) {//new user
                UserDAO.addUser(user, selectedRoles);
            } else {//update user
                UserDAO.updateUser(user, selectedRoles);
            }
            return "success";
        } catch(UserExistException uee) {
            uee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }//saveUser()
    

    /**
     * GoTo the "Edit Profile" page
     * @return
     */
    public String editProfile() {
        user = JSFUtil.getCurrentUser();
        System.out.println("+++> "+user.getLastname()+"   --  "+user.getFirstname());
        return "editProfile";
    }//editProfile()
    
    
    /**
     * Update the current user's profile
     * @return
     */
    public String updateProfile() {
        
        //check user
        //TODO
        
        try {
            UserDAO.updateProfile(user);
            return "success";
        } catch(UserExistException uee) {
            uee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }//updateProfile()
    

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
    
    
    public List getRoles() {
        return UserDAO.getRoleList();
    }//getRoles()


    public String[] getSelectedRoles() {
        return selectedRoles;
    }


    public void setSelectedRoles(String[] selectedRoles) {
        this.selectedRoles = selectedRoles;
    }
    
    
}//class UserBean
