package org.pgist.backing;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.GlossaryDAO;
import org.pgist.exceptions.TermExistException;
import org.pgist.glossary.Term;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Backing bean for processing glossary management
 * 
 * @author kenny
 *
 */
public class GlossaryBean extends ListTableBean {

    
    private List terms = null;
    private Term term = null;


    public List getTerms() {
        return terms;
    }


    public void setTerms(List terms) {
        this.terms = terms;
    }
    
    
    public Term getTerm() {
        return term;
    }


    public void setTerm(Term term) {
        this.term = term;
    }


    /**
     * List All terms.
     * @return
     */
    public void listTerm(ActionEvent event) {
        
        try {
            terms = GlossaryDAO.getTermList(pageSetting);
        } catch(Exception e) {
        }
        
    }//listGlossary()


    /**
     * add new Term to glossary
     * @return
     */
    public String addTerm() {
        term = new Term();
        return "addTerm";
    }
    
    
    /**
     * edit term information
     * @return
     */
    public String editTerm() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            term = (Term) GlossaryDAO.load(Term.class, selectedId());
        } catch(Exception e) {
        }
        
        return "editTerm";
        
    }//editTerm()

    
    /**
     * insert/update glossary term
     * @return
     */
    public String saveTerm() {
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            if (term.getId()==null) {//new term
                GlossaryDAO.addTerm(term);
            } else {//update term
                GlossaryDAO.updateTerm(term);
            }
            return "success";
        } catch(TermExistException tee) {
            tee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }
    
    
    public void delTerms(ActionEvent event) {
        GlossaryDAO.delTerms(selectedIds(Term.class, "id"));
    }//delTerms()


}//class GlossaryBean
