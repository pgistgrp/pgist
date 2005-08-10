package org.pgist.nlp;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.pgist.model.Tree;
import org.pgist.model.Node;


/**
 * PGIST User class.
 * @author kenny
 * @hibernate.class table="nlp_conversation_thread"
 */
public class ConversationThread implements Tree {
    
    
    private Long id;
    private Post root;
    private boolean enabled;
    private boolean deleted;
    private String title;
    
    
    /**
     * @return
     * @hibernate.id generator-class="native"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }


    /**
     * @return
     * @hibernate.many-to-one column="root_id" class="org.pgist.nlp.Post" casecad="all"
     */
    public Node getRoot() {
        return root;
    }


    public void setRoot(Node root) {
        this.root = (Post) root;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isEnabled() {
        return enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isDeleted() {
        return deleted;
    }


    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public int getNodesCount() {
        int count = 0;
        
        if (root==null) return count;
        
        Stack stack = new Stack();
        stack.push(root);
        
        while (!stack.empty()) {
            Node node = (Node) stack.pop();
            count++;
            
            Set kids = node.getChildren();
            if (kids!=null) {
                for (Iterator iter=kids.iterator(); iter.hasNext(); ) {
                    stack.push(iter.next());
                }//for iter
            }
        }//while stack
        
        return count;
    }//getNodesCount()
    
    
    public Node findNode(Long nodeId) {
        if (root.getId().longValue()==nodeId.longValue()) return root;
        
        Stack stack = new Stack();
        stack.push(root);
        
        while (!stack.empty()) {
            Node one = (Node) stack.pop();
            
            Set kids = one.getChildren();
            if (kids!=null) {
                for (Iterator iter=kids.iterator(); iter.hasNext(); ) {
                    Node kid = (Node) iter.next();
                    if (kid.getId().longValue()==nodeId.longValue()) return kid;
                    stack.push(kid);
                }//for iter
            }
        }//while stack
        
        return null;
    }//findNode()
    
    
}//class ConversationThread
