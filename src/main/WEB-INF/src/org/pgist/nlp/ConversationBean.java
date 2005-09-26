package org.pgist.nlp;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public void listConversation(ActionEvent event, Map map) {
        
        try {
            conversations = ConversationDAO.getConversationList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//listConversation()
    
    
    public String readConversation() throws Exception {
        thread = (ConversationThread) ConversationDAO.load(ConversationThread.class, selectedId());
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        request.setAttribute("thread", thread);
        return "success";
    }
    
    
    public String newConversation() throws Exception {
        thread = new ConversationThread();
        thread.setDeleted(false);
        thread.setEnabled(true);
        thread.setTitle("");
        
        Post post = new Post();
        post.setOwner(JSFUtil.getCurrentUser());
        post.setParent(null);
        post.setTime(new Date());
        post.setTitle("");
        post.setContent(null);
        post.setOwner(JSFUtil.getCurrentUser());
        post.setTone(1);
        
        thread.setRoot(post);
        
        return "newConversation";
    }
    
    
    public String saveNewConversation() throws Exception {
        try {
            ConversationDAO.insert(thread.getRoot());
            ConversationDAO.insert(thread);
            objectId.setValue(thread.getId());
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.setAttribute("thread", thread);
            return "success";
        } catch(Exception e) {
            return "failure";
        }
    }//saveNewConversation()
    
    
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
        TextContent content = new TextContent();
        content.setContent(s);
        post.setContent(content);
        post.setTime(new Date());
        post.setTitle("");
        post.setTone(tone.intValue());
        ConversationDAO.insert(post);
        
        return post;
    }//savePost()
    

}//class ConversationBean
