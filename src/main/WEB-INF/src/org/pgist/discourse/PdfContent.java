package org.pgist.discourse;

import org.pgist.model.IFile;
import org.pgist.model.IPdf;
import org.pgist.util.PgistFile;


/**
 * Context: link
 * @author kenny
 * @hibernate.joined-subclass name="PdfContent" table="pgist_do_content_pdf"
 * @hibernate.joined-subclass-key column="id"
 *
 */
public class PdfContent extends Content implements IPdf {


    private PgistFile file;


    /**
     * @return
     * @hibernate.many-to-one name="file" column="file_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getFile() {
        return file;
    }


    public void setFile(PgistFile file) {
        this.file = file;
    }


    public Object getContentAsObject() {
        return file;
    }


    public int getType() {
        return PDF;
    }


    public IFile getPDF() {
        return file;
    }
    
    
}//class PdfContent
