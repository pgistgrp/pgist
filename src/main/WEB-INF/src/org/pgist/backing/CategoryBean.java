package org.pgist.backing;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.CategoryDAO;
import org.pgist.exceptions.CategoryExistException;
import org.pgist.glossary.TermCategory;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Backing Bean for Category Management
 * @author kenny
 *
 */
public class CategoryBean extends ListTableBean {

    
    private List categories = null;
    private TermCategory category = null;
    
    
    public List getCategories() {
        return categories;
    }
    
    
    public void setCategories(List categories) {
        this.categories = categories;
    }
    
    
    public TermCategory getCategory() {
        return category;
    }
    
    
    public void setCategory(TermCategory category) {
        this.category = category;
    }
    
    
    /**
     * List All Categories.
     * @return
     */
    public void listCategory(ActionEvent event) {
        try {
            categories = CategoryDAO.getCategoryList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//listCategory()


    /**
     * add new category
     * @return
     */
    public String addCategory() {
        category = new TermCategory();
        return "addCategory";
    }
    
    
    /**
     * edit category information
     * @return
     */
    public String editCategory() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            category = (TermCategory) CategoryDAO.load(TermCategory.class, selectedId());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "editCategory";
        
    }//editcategory()

    
    /**
     * insert/update glossary category
     * @return
     */
    public String saveCategory() {
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            if (category.getId()==null) {//new category
                CategoryDAO.addCategory(category);
            } else {//update category
                CategoryDAO.updateCategory(category);
            }
            return "success";
        } catch(CategoryExistException cee) {
            cee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }
    
    
    public void delCategories(ActionEvent event) {
        CategoryDAO.delCategories(selectedIds(TermCategory.class, "id"));
    }//delcategorys()


}//class CategoryBean
