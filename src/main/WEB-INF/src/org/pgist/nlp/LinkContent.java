package org.pgist.nlp;


/**
 * Context: link
 * @author kenny
 * @hibernate.joined-subclass name="LinkContent" table="nlp_content_link"
 * @hibernate.joined-subclass-key column="id"
 *
 */
public class LinkContent extends Content {


    private String link;


    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getLink() {
        return link;
    }


    public void setLink(String link) {
        this.link = link;
    }


    public Object getContentAsObject() {
        return link;
    }


    public int getType() {
        return LINK;
    }
    
    
}//class LinkContent
