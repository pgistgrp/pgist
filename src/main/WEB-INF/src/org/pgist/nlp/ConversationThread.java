package org.pgist.nlp;


/**
 * PGIST User class.
 * @author kenny
 * @hibernate.class table="nlp_conversation_thread"
 */
public class ConversationThread {
    
    
    private Long id;
    private Post root;
    private boolean enabled;
    private boolean deleted;
    
    
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
     * @hibernate.many-to-one column="root_id" class="org.pgist.nlp.Post" casecad="all"
     */
    public Post getRoot() {
        return root;
    }


    public void setRoot(Post root) {
        this.root = root;
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


}//class ConversationThread
