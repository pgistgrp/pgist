package org.pgist.backing;

import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.pgist.dao.EmailTemplateDAO;
import org.pgist.emails.EmailTemplate;
import org.pgist.exceptions.TemplateExistException;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Backing bean for managing email templates
 * @author kenny
 *
 */
public class EmailTemplateBean extends ListTableBean {

    
    private List templates = null;
    private EmailTemplate template = new EmailTemplate();
    
    
    public EmailTemplate getTemplate() {
        return template;
    }
    
    
    public void setTemplate(EmailTemplate template) {
        this.template = template;
    }
    
    
    public List getTemplates() {
        return templates;
    }
    
    
    public void setTemplates(List templates) {
        this.templates = templates;
    }
    
    
    /**
     * List All Users.
     * @return
     */
    public void listTemplate(ActionEvent event, Map map) {
        
        try {
            templates = EmailTemplateDAO.getTemplateList(pageSetting);
        } catch(Exception e) {
        }
        
    }//listTemplate()
    
    
    /**
     * GoTo the "Edit Template" page
     * @return
     */
    public String editTemplate() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        try {
            template = (EmailTemplate) EmailTemplateDAO.load(EmailTemplate.class, selectedId());
        } catch(Exception e) {
        }
        
        return "editTemplate";
        
    }//editTemplate()
    
    
    /**
     * Save a modified Template
     * @return
     */
    public String saveTemplate() {
        
        if (!JSFUtil.checkAdmin()) return "notAdmin";
        
        try {
            EmailTemplateDAO.updateTemplate(template);
            return "success";
        } catch(TemplateExistException tee) {
            tee.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return "failure";
    }//saveTemplate()
    
    
}//class EmailTemplateBean
