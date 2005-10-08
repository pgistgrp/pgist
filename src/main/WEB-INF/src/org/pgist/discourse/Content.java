package org.pgist.discourse;


/**
 * 
 * @author kenny
 * @hibernate.class table="pgist_discourse_content"
 *
 */
public abstract class Content {

    
    public static final int TEXT   = 0;
    public static final int IAMGE  = 1;
    public static final int LINK   = 2;
    public static final int PDF    = 3;
    public static final int ARCGIS = 4;
    
    
    private Long id;
    
    
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


    public abstract int getType();
    public abstract Object getContentAsObject();


}//abstract class Content
