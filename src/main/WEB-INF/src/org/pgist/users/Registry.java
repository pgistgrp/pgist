package org.pgist.users;


/**
 * PGIST User Registry class.
 * @author kenny
 * @hibernate.class table="pgist_registry"
 */
public class Registry {

    
    private Long id;
    private String loginname;
    
    
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
     * @hibernate.property
     */
    public String getLoginname() {
        return loginname;
    }
    
    
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    
    
}
