package org.pgist.users;


/**
 * PGIST User Registry class.
 * @author kenny
 * @hibernate.class table="pgist_registry"
 */
public class Registry extends User {

    
    private int status;

    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
