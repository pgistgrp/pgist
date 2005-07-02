package org.pgist.actions;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm {

    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String loginname;
    private String password;
    private String password1;
    
    
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getLoginname() {
        return loginname;
    }
    
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    
    public void setPassword(String password) {
        this.password = password;
    }
    

    public String getPassword1() {
        return password1;
    }
    

    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    
}
