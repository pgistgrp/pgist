package org.pgist.discourse;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import org.pgist.model.INode;
import org.pgist.model.ITree;


/**
 * 
 * @author kenny
 * @hibernate.class table="pgist_discourse"
 *
 */
public class Discourse implements ITree {
    
    
    private Long id;
    private Opinion root;
    private boolean enabled;
    private boolean deleted;
    private String title = "";
    
    
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
     * @hibernate.many-to-one column="root_id" class="org.pgist.discourse.Opinion" casecad="all"
     */
    public INode getRoot() {
        return root;
    }


    public void setRoot(INode root) {
        this.root = (Opinion) root;
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
            INode node = (INode) stack.pop();
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
    
    
    public INode findNode(Long nodeId) {
        if (root.getId().longValue()==nodeId.longValue()) return root;
        
        Stack stack = new Stack();
        stack.push(root);
        
        while (!stack.empty()) {
            INode one = (INode) stack.pop();
            
            Set kids = one.getChildren();
            if (kids!=null) {
                for (Iterator iter=kids.iterator(); iter.hasNext(); ) {
                    INode kid = (INode) iter.next();
                    if (kid.getId().longValue()==nodeId.longValue()) return kid;
                    stack.push(kid);
                }//for iter
            }
        }//while stack
        
        return null;
    }//findNode()
    
    
}//class Discourse
