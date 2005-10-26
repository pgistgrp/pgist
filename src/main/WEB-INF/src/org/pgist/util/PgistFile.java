package org.pgist.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.pgist.model.IFile;


/**
 * 
 * @author kenny
 * @hibernate.class table="pgist_files"
 *
 */
public class PgistFile implements IFile {


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
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] b = new byte[1024];
        int n=0;
        while ((n=inputStream.read(b))>0) {
            outputStream.write(b, 0, n);
        }//while
        outputStream.close();
    }


    public InputStream getInputStream() throws Exception {
        if (id==null) return null;
        
        File file = (File) DurableObjectManager.get(DurableObjectManager.MANAGED_FILE_PATH);
        file = new File(file, id.toString());
        return new FileInputStream(file);
    }//getInputStream()


    public OutputStream getOutputStream() throws Exception {
        if (id==null) return null;
        
        File file = (File) DurableObjectManager.get(DurableObjectManager.MANAGED_FILE_PATH);
        file = new File(file, id.toString());
        return new FileOutputStream(file);
    }//getOutputStream()


    public String getPath() throws Exception {
        if (id==null) return null;
        
        File file = (File) DurableObjectManager.get(DurableObjectManager.MANAGED_FILE_PATH);
        file = new File(file, id.toString());
        
        return file.getAbsolutePath();
    }//getPath()


}//class PgistFile
