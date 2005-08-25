package org.pgist.glossary;


/**
 * Glossary POJO
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_term"
 */
public class Term {

    
    private Long id;
    private String name;
    private String shortDefinition;
    private String extDefinition;
    private String source1;
    private String source2;
    private String source3;
    private String relatedTerm1;
    private String relatedTerm2;
    private String relatedTerm3;
    private String relatedTerm4;
    private String relatedTerm5;
    private String link1;
    private String link2;
    private String link3;
    private boolean internal = false;
    private boolean deleted = false;
    
    
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
    public String getShortDefinition() {
        return shortDefinition;
    }
    
    
    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getLink1() {
        return link1;
    }


    public void setLink1(String link1) {
        this.link1 = link1;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getLink2() {
        return link2;
    }


    public void setLink2(String link2) {
        this.link2 = link2;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getLink3() {
        return link3;
    }


    public void setLink3(String link3) {
        this.link3 = link3;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getSource1() {
        return source1;
    }


    public void setSource1(String source1) {
        this.source1 = source1;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getSource2() {
        return source2;
    }


    public void setSource2(String source2) {
        this.source2 = source2;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getSource3() {
        return source3;
    }


    public void setSource3(String source3) {
        this.source3 = source3;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRelatedTerm1() {
        return relatedTerm1;
    }


    public void setRelatedTerm1(String term1) {
        this.relatedTerm1 = term1;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRelatedTerm2() {
        return relatedTerm2;
    }


    public void setRelatedTerm2(String term2) {
        this.relatedTerm2 = term2;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRelatedTerm3() {
        return relatedTerm3;
    }


    public void setRelatedTerm3(String term3) {
        this.relatedTerm3 = term3;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRelatedTerm4() {
        return relatedTerm4;
    }


    public void setRelatedTerm4(String term4) {
        this.relatedTerm4 = term4;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getRelatedTerm5() {
        return relatedTerm5;
    }


    public void setRelatedTerm5(String term5) {
        this.relatedTerm5 = term5;
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
    
    
}//class Term
