package org.pgist.discourse;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.model.Node;
import org.pgist.users.User;


/**
 * Post class
 * @author kenny
 * @hibernate.class table="pgist_disourse_opinion"
 *
 */
public class Opinion implements Node {
    
    
    private Long id;
    private Opinion parent;
    private String title;
    private Content content;
    private Set children = new HashSet();
    private User owner;
    private Date time;
    private int tone;
    
    
    /**
     * @return
     * Notes: the id comes from one-to-one mapped Content object.
     * @hibernate.id generator-class="foreign"
     * @hibernate.generator-param name="property" value="content"
     */
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="parent_id" class="org.pgist.discourse.Opinion" casecad="all"
     */
    public Node getParent() {
        return parent;
    }
    
    
    public void setParent(Node parent) {
        this.parent = (Opinion) parent;
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
    
    
    /**
     * @return
     * @hibernate.one-to-one name="content" cascade="all" class="org.pgist.discourse.Content"
     */
    public Content getContent() {
        return content;
    }
    
    
    public void setContent(Content content) {
        this.content = content;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_discourse_opop_link" cascade="all" order-by="id"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-one-to-many class="org.pgist.discourse.Opinion"
     */
    public Set getChildren() {
        return children;
    }
    
    
    public void setChildren(Set children) {
        this.children = children;
    }
    
    
    /**
     * @return
     * @hibernate.many-to-one column="owner_id" class="org.pgist.users.User" casecad="all"
     */
    public User getOwner() {
        return owner;
    }
    
    
    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public Date getTime() {
        return time;
    }
    
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public int getTone() {
        return tone;
    }
    
    
    public void setTone(int tone) {
        this.tone = tone;
    }
    
    
    public void addOpinion(Opinion opinion) {
        children.add(opinion);
    }
    
    
    public int getDepth() {
        int depth = 0;
        Node one = this;
        while (one.getParent()!=null) {
            depth++;
            one = one.getParent();
        }
        return depth;
    }


}//class Opinion
