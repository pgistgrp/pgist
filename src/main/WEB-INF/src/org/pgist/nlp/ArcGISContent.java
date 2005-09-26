package org.pgist.nlp;


/**
 * Context: text
 * @author kenny
 * @hibernate.joined-subclass name="ArcGISContent" table="nlp_content_arcgis"
 * @hibernate.joined-subclass-key column="id"
 *
 */
public class ArcGISContent extends Content {


    private String content;


    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public Object getContentAsObject() {
        return content;
    }


    public int getType() {
        return ARCGIS;
    }
    
    
}//class ArcGISContent
