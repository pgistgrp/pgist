package org.pgist.backing;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.EmailTemplateDAO;
import org.pgist.emails.EmailTemplate;
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
    public void listTemplate(ActionEvent event) {
        
        try {
            templates = EmailTemplateDAO.getTemplateList(pageSetting);
        } catch(Exception e) {
        }
        
    }//listTemplate()
    
    
}//class EmailTemplateBean
