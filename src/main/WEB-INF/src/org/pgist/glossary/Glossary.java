package org.pgist.glossary;

import java.util.List;


/**
 * Glossary POJO
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary"
 */
public class Glossary {

    
    private Long id;
    private String name;
    private String shortDefinition;
    private String extDefinition;
    private List sources;
    private List relatedTerms;
    private List links;
    
    
    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getExtDefinition() {
        return extDefinition;
    }
    
    
    public void setExtDefinition(String extDefinition) {
        this.extDefinition = extDefinition;
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
    
    
    public List getLinks() {
        return links;
    }
    
    
    public void setLinks(List links) {
        this.links = links;
    }
    
    
    /**
     * @return
     * @hibernate.property unique="true" not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public List getRelatedTerms() {
        return relatedTerms;
    }
    
    
    public void setRelatedTerms(List relatedTerms) {
        this.relatedTerms = relatedTerms;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getShortDefinition() {
        return shortDefinition;
    }
    
    
    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }
    
    
    public List getSources() {
        return sources;
    }
    
    
    public void setSources(List sources) {
        this.sources = sources;
    }
    
    
}//class Glossary
