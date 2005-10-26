package org.pgist.discourse;

import org.pgist.model.IText;


/**
 * Context: text
 * @author kenny
 * @hibernate.joined-subclass name="TextContent" table="pgist_do_content_text"
 * @hibernate.joined-subclass-key column="id"
 *
 */
public class TextContent extends Content implements IText {


    private String content = "";


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
        return TEXT;
    }


    public String getText() {
        return content;
    }//getText()
    
    
}//class TextContent
