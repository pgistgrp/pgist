package org.pgist.backing;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectBoolean;
import javax.faces.event.ActionEvent;

import org.pgist.dao.UserDAO;
import org.pgist.exceptions.RoleExistException;
import org.pgist.exceptions.UserExistException;
import org.pgist.users.Role;
import org.pgist.users.User;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;
import org.pgist.util.PageSetting;


public class RoleBean extends ListTableBean {
    
    
    private static final long serialVersionUID = 1L;
    private List roles = null;


    public List getRoles() {
        return roles;
    }


    public void setRoles(List roles) {
        this.roles = roles;
    }


    /**
     * List All Roles.
     * @return
     */
    public void listRole(ActionEvent event) {
        
        //pageSetting.set("nameFilter", nameFilter);
        try {
            roles = UserDAO.getRoleList(pageSetting);
        } catch(Exception e) {
            //return "dberror";
        }
        
        //return "listUser";
    }//listRole()
    
    
    /**
     * add a new role
     * @return
     */
    public String addRole() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        Role role = new Role();
        //role.setName(roleName);
        role.setDeleted(false);
        
        try {
            UserDAO.addRole(role);
            return "success";
        } catch(RoleExistException ree) {
        } catch(Exception e) {
        }
        
        return "failure";
    }//addRole()
    
    
    /**
     * delete roles
     * @return
     */
    public void delRoles(ActionEvent event) {
        
        UserDAO.delRoles(selectedIds(Role.class, "id"));
        
    }//delRoles()
    
    
    /**
     * edit a role info
     * @return
     */
    public String editRole() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            //Role role = (Role) UserDAO.load(Role.class, roleId[0]);
            //UserDAO.editRole(role);
        } catch(Exception e) {
            return "listRole";
        }
        
        return null;
        
    }//editRole()


}//class RoleBean

