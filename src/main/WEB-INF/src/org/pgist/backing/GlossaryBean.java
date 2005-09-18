package org.pgist.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.pgist.dao.GlossaryDAO;
import org.pgist.exceptions.TermExistException;
import org.pgist.glossary.Term;
import org.pgist.glossary.TermCategory;
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
    private String category;
    private String[] categoryFilter;
    private String[] selectedCategories;
    private String[] sources;
    private List categories = new ArrayList();
    private List allCategories = new ArrayList();
    private List filterCategories = new ArrayList();


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


    public String[] getSelectedCategories() {
        return selectedCategories;
    }


    public void setSelectedCategories(String[] selectedCategories) {
        this.selectedCategories = selectedCategories;
    }


    public String[] getSources() {
        return sources;
    }


    public void setSources(String[] sources) {
        this.sources = sources;
    }


    public String[] getCategoryFilter() {
        return categoryFilter;
    }


    public void setCategoryFilter(String[] filter) {
        this.categoryFilter = filter;
    }


    public List getAllCategories() throws Exception {
        return allCategories;
    }


    public List getCategories() {
        return categories;
    }


    public void setCategories(List categories) {
        this.categories = categories;
    }


    public String getCategory() {
        return category;
    }


    public void setCategory(String category) {
        this.category = category;
    }


    public List getFilterCategories() {
        return filterCategories;
    }


    public void setFilterCategories(List filterCategories) {
        this.filterCategories = filterCategories;
    }


    /**
     * List All terms.
     * @return
     */
    public void listTerm(ActionEvent event, Map map) {
        boolean postMode = "true".equals(map.get("postMode"));
        try {
            allCategories = GlossaryDAO.getAllCategories();
            categories.clear();
            if (postMode) {//post
                if (categoryFilter!=null) {
                    if (categoryFilter.length>0) {
                        StringBuffer buffer = new StringBuffer();
                        for (int i=0; i<categoryFilter.length; i++) {
                            if (i>0) buffer.append(',');
                            buffer.append(categoryFilter[i]);
                            for (int j=0, n=allCategories.size(); j<n; j++) {
                                TermCategory one = (TermCategory) allCategories.get(j);
                                if (categoryFilter[i].equals(one.getId())) {
                                    categories.add(one);
                                }
                            }//for j
                        }//for i
                        pageSetting.set("categoryFilter", buffer.toString());
                    } else {
                        pageSetting.set("categoryFilter", null);
                    }
                } else {
                    pageSetting.set("categoryFilter", null);
                }
            } else {//get
                StringBuffer buffer = new StringBuffer();
                for (int i=0, n=allCategories.size(); i<n; i++) {
                    TermCategory one = (TermCategory) allCategories.get(i);
                    if (i>0) buffer.append(',');
                    buffer.append(one.getId());
                }//for i
                pageSetting.set("categoryFilter", buffer.toString());
                categories.addAll(allCategories);
            }
            terms = GlossaryDAO.getTermList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }//listTerm()


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
            e.printStackTrace();
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
                GlossaryDAO.addTerm(term, selectedCategories, sources);
            } else {//update term
                GlossaryDAO.updateTerm(term, selectedCategories, sources);
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
