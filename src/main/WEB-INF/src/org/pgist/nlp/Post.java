package org.pgist.nlp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.pgist.model.Node;
import org.pgist.users.User;


/**
 * Post class
 * @author kenny
 * @hibernate.class table="nlp_post"
 *
 */
public class Post implements Node {
    
    
    private Long id;
    private Post parent;
    private String title;
    private String content;
    private Set children = new HashSet();
    private User owner;
    private Date time;
    private int tone;
    
    
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
     * @hibernate.many-to-one column="parent_id" class="org.pgist.nlp.Post" casecad="all"
     */
    public Node getParent() {
        return parent;
    }
    
    
    public void setParent(Node parent) {
        this.parent = (Post) parent;
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
     * @hibernate.property not-null="true"
     */
    public String getContent() {
        return content;
    }
    
    
    public void setContent(String content) {
        this.content = content;
    }
    
    
    /**
     * @return
     * @hibernate.set lazy="false" table="nlp_thread_post_link" cascade="all" order-by="id"
     * @hibernate.collection-key column="parent_id"
     * @hibernate.collection-one-to-many class="org.pgist.nlp.Post"
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
    
    
    public void addPost(Post post) {
        children.add(post);
    }


}//class Post
