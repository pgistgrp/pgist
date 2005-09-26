package org.pgist.util;


/**
 * 
 * @author kenny
 * @hibernate.class table="pgist_files"
 *
 */
public class PgistFile {


    private Long id;
    private String name;
    
    
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
     * @hibernate.property type="text" not-null="true"
     */
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


}//class PgistFile
