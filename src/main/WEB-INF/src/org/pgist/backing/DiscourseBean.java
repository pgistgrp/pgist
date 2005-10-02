package org.pgist.backing;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.pgist.dao.DiscourseDAO;
import org.pgist.discourse.Discourse;
import org.pgist.discourse.Opinion;
import org.pgist.discourse.TextContent;
import org.pgist.model.Tree;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;


/**
 * Conversation Backing Bean
 * @author kenny
 *
 */
public class DiscourseBean extends ListTableBean {

    
    private List discourses;
    private Discourse discourse;
    private Opinion opinion;
    

    public List getDiscourses() {
        return discourses;
    }
    

    public void setDiscoursess(List discourses) {
        this.discourses = discourses;
    }
    
    
    public Discourse getDiscourse() {
        return discourse;
    }


    public void setDiscourse(Discourse discourse) {
        this.discourse = discourse;
    }


    public Opinion getOpinion() {
        return opinion;
    }


    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }


    /**
     * List All Discourses.
     * @return
     */
    public void listDiscourse(ActionEvent event, Map map) {
        
        try {
            discourses = DiscourseDAO.getDiscourseList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//listDiscourse()
    
    
    public String readDiscourse() throws Exception {
        discourse = (Discourse) DiscourseDAO.load(Discourse.class, selectedId());
        opinion = (Opinion) discourse.getRoot();
        return "success";
    }//readDiscourse()
    
    
    public String newDiscourse() throws Exception {
        discourse = new Discourse();
        discourse.setDeleted(false);
        discourse.setEnabled(true);
        discourse.setTitle("");
        
        Opinion opinion = new Opinion();
        opinion.setOwner(JSFUtil.getCurrentUser());
        opinion.setParent(null);
        opinion.setTime(new Date());
        opinion.setTitle("");
        opinion.setContent(null);
        opinion.setOwner(JSFUtil.getCurrentUser());
        opinion.setTone(1);
        
        discourse.setRoot(opinion);
        
        return "newConversation";
    }//newDiscourse()
    
    
    public String saveNewDiscourse() throws Exception {
        try {
            DiscourseDAO.insert(discourse.getRoot());
            DiscourseDAO.insert(discourse);
            objectId.setValue(discourse.getId());
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.setAttribute("thread", discourse);
            return "success";
        } catch(Exception e) {
            return "failure";
        }
    }//saveNewDiscourse()
    
    
    public static Tree getDiscourse(ActionEvent event, Long id) {
        Tree tree = null;
        try {
            tree = (Tree) DiscourseDAO.load(Discourse.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tree;
    }//getDiscourse()
    
    
    public static Opinion saveOpinion(ActionEvent event, Long id, Integer tone, String s) throws Exception {
        Opinion opinion = new Opinion();
        
        Opinion parent = (Opinion) DiscourseDAO.load(Opinion.class, id);
        opinion.setParent(parent);
        opinion.setOwner(JSFUtil.getCurrentUser());
        TextContent content = new TextContent();
        content.setContent(s);
        opinion.setContent(content);
        opinion.setTime(new Date());
        opinion.setTitle("");
        opinion.setTone(tone.intValue());
        DiscourseDAO.insert(opinion);
        
        return opinion;
    }//saveOpinion()
    

}//class DiscourseBean
