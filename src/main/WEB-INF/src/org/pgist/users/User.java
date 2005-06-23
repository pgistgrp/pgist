package org.pgist.users;

import java.util.Set;

import org.pgist.util.MD5;


/**
 * PGIST User class.
 * @author kenny
 * @hibernate.class table="pgist_user"
 */
public class User {
    
    
    private Long id;
    private String loginname;
    private String password;
    private boolean enabled;
    private boolean deleted;
    private Set roles;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return
     * @hibernate.property unique="true" not-null="true"
     */
    public String getLoginname() {
        return loginname;
    }
    
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public void setOriginPassword(String password) {
        this.password = MD5.getDigest(password);
    }

    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.set lazy="true" table="pgist_user_role_link" cascade="none" order-by="id"
     * @hibernate.collection-key  column="user_id"
     * @hibernate.collection-many-to-many column="role_id" class="org.pgist.users.Role"
     */
    public Set getRoles() {
        return roles;
    }


    public void setRoles(Set roles) {
        this.roles = roles;
    }
    
    
    public boolean checkPassword(String providedPWD) {
        return this.password.equals(MD5.getDigest(providedPWD));
    }
    
    
}
