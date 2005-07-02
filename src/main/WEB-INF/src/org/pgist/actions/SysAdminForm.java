package org.pgist.actions;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class SysAdminForm extends ActionForm {


    private static final long serialVersionUID = 1L;
    private List users = null;
    private int page = 0;
    private String nameFilter = "";
    
    
    public List getUsers() {
        return users;
    }
    
    
    public void setUsers(List users) {
        this.users = users;
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

}
