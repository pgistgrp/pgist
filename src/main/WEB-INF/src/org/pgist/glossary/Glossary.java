package org.pgist.glossary;


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
    private String source1;
    private String source2;
    private String source3;
    private String term1;
    private String term2;
    private String term3;
    private String term4;
    private String term5;
    private String link1;
    private String link2;
    private String link3;
    
    
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
     * @hibernate.property unique="true" not-null="true"
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
    public String getTerm1() {
        return term1;
    }


    public void setTerm1(String term1) {
        this.term1 = term1;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTerm2() {
        return term2;
    }


    public void setTerm2(String term2) {
        this.term2 = term2;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTerm3() {
        return term3;
    }


    public void setTerm3(String term3) {
        this.term3 = term3;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTerm4() {
        return term4;
    }


    public void setTerm4(String term4) {
        this.term4 = term4;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTerm5() {
        return term5;
    }


    public void setTerm5(String term5) {
        this.term5 = term5;
    }
    
    
}//class Glossary
