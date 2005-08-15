package org.pgist.glossary;


/**
 * Glossary Source POJO
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_source"
 */
public class Source {

    
    private Long id;
    private String content;
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getContent() {
        return content;
    }
    
    
    public void setContent(String content) {
        this.content = content;
    }
    
    
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
    
    
}//class Source
