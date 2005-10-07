package org.pgist.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


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
    
    
    public void receive(InputStream inputStream) throws Exception {
        File file = (File) DurableObjectManager.get(DurableObjectManager.MANAGED_FILE_PATH);
        file = new File(file, id.toString());
        System.out.println("---> "+file.getAbsolutePath());
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] b = new byte[1024];
        int n=0;
        while ((n=inputStream.read(b))>0) {
            outputStream.write(b, 0, n);
        }//while
        outputStream.close();
    }


}//class PgistFile
