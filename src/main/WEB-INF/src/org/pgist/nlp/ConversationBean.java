package org.pgist.nlp;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.pgist.dao.UserDAO;
import org.pgist.util.ListTableBean;


/**
 * Conversation Backing Bean
 * @author kenny
 *
 */
public class ConversationBean extends ListTableBean {

    
    private List conversations;
    

    public List getConversations() {
        return conversations;
    }
    

    public void setConversations(List conversations) {
        this.conversations = conversations;
    }
    
    
    /**
     * List All Users.
     * @return
     */
    public void listConversation(ActionEvent event) {
        
        try {
            conversations = ConversationDAO.getConversationList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//listConversation()
    
    
}//class ConversationBean

