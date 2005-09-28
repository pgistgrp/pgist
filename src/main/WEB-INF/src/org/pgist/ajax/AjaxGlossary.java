package org.pgist.ajax;

import java.util.List;

import org.pgist.dao.GlossaryDAO;
import org.pgist.util.PageSetting;


/**
 * 
 * @author kenny
 *
 */
public class AjaxGlossary {

    
    /**
     * Get terms list in specific page. Parameter searchFilter is used to confine the search matching,
     * which will be the prefix of a term.
     * @param page
     * @param searchFilter
     * @return
     */
    public List getTermList(int page, String searchFilter) {
        List list = null;
        PageSetting setting = new PageSetting();
        setting.setPage(page);
        setting.set("categoryFilter", "all");
        if (searchFilter!=null) {
            searchFilter = searchFilter.toUpperCase().trim();
            if (!"".equals(searchFilter)) setting.set("nameFilter", searchFilter+"%");
        }
        try {
            list = GlossaryDAO.getTermList(setting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        list.add(""+setting.getPage());
        return list;
    }//getTermList()
    
    
}//class AjaxGlossary
