package org.pgist.nlp;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.pgist.model.Tree;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Conversation Backing Bean
 * @author kenny
 *
 */
public class ConversationBean extends ListTableBean {

    
    private List conversations;
    private ConversationThread thread;
    

    public List getConversations() {
        return conversations;
    }
    

    public void setConversations(List conversations) {
        this.conversations = conversations;
    }
    
    
    public ConversationThread getThread() {
        return thread;
    }


    public void setThread(ConversationThread thread) {
        this.thread = thread;
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
    
    
    public String readConversation() throws Exception {
        ConversationThread thread = (ConversationThread) ConversationDAO.load(ConversationThread.class, selectedId());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute("thread", thread);
        return "success";
    }
    
    
    public static Tree getThread(ActionEvent event, Long id) {
        Tree tree = null;
        try {
            tree = (Tree) ConversationDAO.load(ConversationThread.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tree;
    }//getThread()
    
    
    public static Post savePost(ActionEvent event, Long id, Integer tone, String s) throws Exception {
        Post post = new Post();
        
        Post parent = (Post) ConversationDAO.load(Post.class, id);
        post.setParent(parent);
        post.setOwner(JSFUtil.getCurrentUser());
        post.setContent(s);
        post.setTime(new Date());
        post.setTitle("");
        post.setTone(tone.intValue());
        ConversationDAO.insert(post);
        
        return post;
    }//savePost()
    

}//class ConversationBean
