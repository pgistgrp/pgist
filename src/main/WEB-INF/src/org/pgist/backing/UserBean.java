package org.pgist.backing;

import java.util.List;

import javax.faces.component.UIData;
import javax.faces.component.UISelectBoolean;
import javax.faces.event.ActionEvent;

import org.pgist.dao.UserDAO;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.Role;
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
    private UIData roleData = null;
    protected UISelectBoolean roleChecked = null;


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


    public UIData getRoleData() {
        return roleData;
    }
    
    
    public void setRoleData(UIData roleData) {
        this.roleData = roleData;
    }
    
    
    public UISelectBoolean getRoleChecked() {
        return roleChecked;
    }
    
    
    public void setRoleChecked(UISelectBoolean roleChecked) {
        this.roleChecked = roleChecked;
    }
    
    
    /**
     * List All Users.
     * @return
     */
    public void listUser(ActionEvent event) {
        
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
            UserDAO.addUser(user, selectedIds(roleData, roleChecked, Role.class, "id"));
            return "success";
        } catch(UserExistException uee) {
        } catch(Exception e) {
        }
        
        return "failure";
    }//saveUser()
    
    
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
        List list = UserDAO.getRoleList();
        for (int i=0; i<list.size(); i++) {
            Role role = (Role) list.get(i);
        }
        return list;
    }//getRoles()
    
    
}//class UserBean
