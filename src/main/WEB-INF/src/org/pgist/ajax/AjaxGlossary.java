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

    
    public List getTermList(int page) {
        List list = null;
        PageSetting setting = new PageSetting();
        setting.setPage(page);
        setting.set("categoryFilter", "all");
        try {
            list = GlossaryDAO.getTermList(setting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }//getTermList()
    
    
}//class AjaxGlossary
