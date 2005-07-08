package org.pgist.actions;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class SysAdminForm extends ActionForm {


    private static final long serialVersionUID = 1L;
    private List users = null;
    private List roles = null;
    private Long[] userId = null;
    private Long[] roleId = null;
    private int page = 0;
    private String nameFilter = "";
    private String roleName = "";
    
    
    public List getUsers() {
        return users;
    }
    
    
    public void setUsers(List users) {
        this.users = users;
    }


    public List getRoles() {
        return roles;
    }


    public void setRoles(List roles) {
        this.roles = roles;
    }


    public int getPage() {
        return page;
    }


    public void setPage(int page) {
        this.page = page;
    }


    public String getNameFilter() {
        return nameFilter;
    }


    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }


    public Long[] getUserId() {
        return userId;
    }


    public void setUserId(Long[] userId) {
        this.userId = userId;
    }


    public Long[] getRoleId() {
        return roleId;
    }


    public void setRoleId(Long[] roleId) {
        this.roleId = roleId;
    }


    public String getRoleName() {
        return roleName;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    
}
