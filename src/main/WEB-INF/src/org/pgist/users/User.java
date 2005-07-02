package org.pgist.users;

import java.util.Set;

import org.pgist.util.MD5;


/**
 * PGIST User class.
 * @author kenny
 * @hibernate.class table="pgist_user"
 */
public class User extends BaseUser {
    
    
    private boolean internal;
    protected Set roles;
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isInternal() {
        return internal;
    }


    public void setInternal(boolean internal) {
        this.internal = internal;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_user_role_link" cascade="all" order-by="role_id"
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
