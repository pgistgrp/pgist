package org.pgist.glossary;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.pgist.users.User;


/**
 * Glossary POJO
 * @author kenny
 *
 * @hibernate.class table="pgist_glossary_term"
 */
public class Term {

    
    private Long id;
    private String name;
    private String shortDefinition;
    private String extDefinition;
    private Set relatedTerms = new HashSet();
    private Set links = new HashSet();
    private Set sources = new HashSet();
    private boolean internal = false;
    private boolean deleted = false;
    private User owner;
    private Set categories = new HashSet();
    
    
    /**
     * @return
     * @hibernate.property type="text" not-null="true"
     */
    public String getExtDefinition() {
        return extDefinition;
    }
    
    
    public void setExtDefinition(String extDefinition) {
        this.extDefinition = extDefinition;
    }
    
    
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
     * @hibernate.property not-null="true"
     */
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public String getShortDefinition() {
        return shortDefinition;
    }
    
    
    public void setShortDefinition(String shortDefinition) {
        this.shortDefinition = shortDefinition;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_link_link" cascade="none" order-by="link_id"
     * @hibernate.collection-key  column="term_id"
     * @hibernate.collection-many-to-many column="link_id" class="org.pgist.glossary.TermLink"
     */
    public Set getLinks() {
        return links;
    }


    public void setLinks(Set links) {
        this.links = links;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_term_link" cascade="none" order-by="term_id"
     * @hibernate.collection-key  column="related_term_id"
     * @hibernate.collection-many-to-many column="term_id" class="org.pgist.glossary.Term"
     */
    public Set getRelatedTerms() {
        return relatedTerms;
    }


    public void setRelatedTerms(Set relatedTerms) {
        this.relatedTerms = relatedTerms;
    }


    /**
     * @return
     * @hibernate.set lazy="false" table="pgist_glossary_term_source_link" cascade="none" order-by="source_id"
     * @hibernate.collection-key  column="term_id"
     * @hibernate.collection-many-to-many column="source_id" class="org.pgist.glossary.TermSource"
     */
    public Set getSources() {
        return sources;
    }


    public void setSources(Set sources) {
        this.sources = sources;
    }


    /**
     * @return
     * @hibernate.property not-null="true"
     */
    public boolean isInternal() {
        return internal;
    }


    public void setInternal(boolean internal) {
        this.internal = internal;
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
     * @hibernate.set lazy="true" table="pgist_glossary_term_categ_link" cascade="none" order-by="category_id"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-many-to-many column="category_id" class="org.pgist.glossary.TermCategory"
     */
    public Set getCategories() {
        return categories;
    }


    public void setCategories(Set categories) {
        this.categories = categories;
    }
    
    
    public String getCategoryList() {
        StringBuffer sb = new StringBuffer();
        
        if (categories!=null) {
            for (Iterator iter=categories.iterator(); iter.hasNext(); ) {
                TermCategory category = (TermCategory) iter.next();
                sb.append(category.getName());
                if (iter.hasNext()) sb.append(", ");
            }//for iter
        }
        
        return sb.toString();
    }
    
    
}//class Term
