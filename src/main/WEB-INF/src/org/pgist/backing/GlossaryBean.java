package org.pgist.backing;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.GlossaryDAO;
import org.pgist.util.ListTableBean;


/**
 * Backing bean for processing glossary management
 * 
 * @author kenny
 *
 */
public class GlossaryBean extends ListTableBean {

    
    private List glossaries = null;


    public List getGlossaries() {
        return glossaries;
    }


    public void setGlossaries(List glossaries) {
        this.glossaries = glossaries;
    }
    
    
    /**
     * List All Glossary.
     * @return
     */
    public void listGlossary(ActionEvent event) {
        
        try {
            glossaries = GlossaryDAO.getGlossaryList(pageSetting);
        } catch(Exception e) {
        }
        
    }//listGlossary()


}//class GlossaryBean

