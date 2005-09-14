package org.pgist.backing;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

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
    private String categoryFilter;
    private String category;
    private List categories = new ArrayList();
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


    public String getCategoryFilter() {
        return categoryFilter;
    }


    public void setCategoryFilter(String filter) {
        this.categoryFilter = filter;
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
    public void listTerm(ActionEvent event) {
        try {
            if (categoryFilter!=null) {
                categoryFilter = categoryFilter.trim();
                if (!"".equals(categoryFilter)) {
                    pageSetting.set("categoryFilter", categoryFilter);
                } else {
                    pageSetting.set("categoryFilter", null);
                }
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


    public void setCategoryList(String _categories) {
        SelectItem item;
        if (_categories!=null) {
            String[] pairs = _categories.split(",");
            for (int i=0; i<pairs.length; i++) {
                String pair = pairs[i];
                if (pair!=null) pair = pair.trim();
                if (!"".equals(pair)) {
                    int index = pair.indexOf(':');
                    if (index>1 && index<pair.length()-1) {
                        item = new SelectItem(pair.substring(0, index), pair.substring(index+1));
                        filterCategories.add(item);
                    }
                }
            }//for i
        }

        item = new SelectItem("", "All");
        categories.add(item);
        categories.addAll(filterCategories);
    }


}//class GlossaryBean
