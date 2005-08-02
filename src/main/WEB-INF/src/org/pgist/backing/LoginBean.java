package org.pgist.backing;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.pgist.dao.UserDAO;
import org.pgist.users.User;
import org.pgist.util.JSFUtil;

import javax.servlet.http.HttpSession;


public class LoginBean {

    
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
    
    
    public void validate(FacesContext context,
            UIComponent component,
            Object value) throws ValidatorException {
    }
    
    
    public String login() {
        System.out.println("---> @ LoginBean.login");
        try {
            User user = UserDAO.getUserByName(loginname, true, false);
            if ( user.checkPassword(password) ) { //user is valid
                JSFUtil.getSession(true).setAttribute("user", user);
                return "success";
            } else { //user is invalid
                HttpSession session = JSFUtil.getSession(false);
                if (session!=null) {
                    session.invalidate();
                }
                return "failure";
            }
        } catch(Exception e) {
            return "failure";
        }
    }//login()
    
    
    public String logout() {
        System.out.println("---> @ LoginBean.logout");
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        User user = (User) session.getAttribute("user");
        if (user!=null) {
            session.setAttribute("user", null);
            session.invalidate();
        }
        return "login";
    }//logout()
    
    
}//class LoginBean

