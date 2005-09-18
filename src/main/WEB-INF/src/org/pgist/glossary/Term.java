package org.pgist.glossary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
    private List relatedTerms = new ArrayList();
    private List links = new ArrayList();
    private List sources = new ArrayList();
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
     * @hibernate.list table="pgist_glossary_term_link_link" lazy="true" cascade="none" order-by="name"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-index column="indexes"
     * @hibernate.collection-many-to-many column="link_id" class="org.pgist.glossary.TermLink"
     */
    public List getLinks() {
        return links;
    }


    public void setLinks(List links) {
        this.links = links;
    }


    /**
     * @return
     * @hibernate.list table="pgist_glossary_term_term_link" lazy="true" cascade="none" order-by="name"
     * @hibernate.collection-key column="related_term_id"
     * @hibernate.collection-index column="indexes"
     * @hibernate.collection-many-to-many class="org.pgist.glossary.Term"
     */
    public List getRelatedTerms() {
        return relatedTerms;
    }


    public void setRelatedTerms(List relatedTerms) {
        this.relatedTerms = relatedTerms;
    }


    /**
     * @return
     * @hibernate.list table="pgist_glossary_term_source_link" lazy="false" cascade="none" order-by="name"
     * @hibernate.collection-key column="term_id"
     * @hibernate.collection-index column="indexes"
     * @hibernate.collection-many-to-many column="source_id" class="org.pgist.glossary.TermSource"
     */
    public List getSources() {
        return sources;
    }


    public void setSources(List sources) {
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
