package org.pgist.actions;

import org.apache.struts.action.ActionForm;


/**
 * Form for LoginAction
 * @author kenny
 *
 */
public class LoginActionForm extends ActionForm {

    
    private static final long serialVersionUID = 1L;
    private String loginname;
    private String password;
    
    
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
    
    
}
