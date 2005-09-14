package org.pgist.glossary;


/**
 * Category for Glossary Term
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_category"
 */
public class TermCategory {

    
    private Long id;
    private String name;
    private String description;
    private boolean internal = false;
    
    
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
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


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
    
    
}//class TermCategory
