package org.pgist.discourse;

import com.sun.image.codec.jpeg.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

import org.pgist.model.IFile;
import org.pgist.model.IImage;
import org.pgist.util.PgistFile;


/**
 * Context: link
 * @author kenny
 * @hibernate.joined-subclass name="ImageContent" table="pgist_do_content_image"
 * @hibernate.joined-subclass-key column="id"
 *
 */
public class ImageContent extends Content implements IImage {


    private PgistFile file;
    private PgistFile thumbnail1;


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


    /**
     * @return
     * @hibernate.many-to-one name="thumb1" column="thumb1_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getThumbnail1() {
        return thumbnail1;
    }


    public void setThumbnail1(PgistFile thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }


    public Object getContentAsObject() {
        return file;
    }


    public int getType() {
        return IAMGE;
    }
    
    
    public void generateThumbnails() throws Exception {
        if (file==null) return;
        if (thumbnail1==null) return;
        
        Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        mediaTracker.waitForID(0);
        
        int thumbWidth = 64;
        int thumbHeight = 64;
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double)imageWidth / (double)imageHeight;
        if (thumbRatio < imageRatio) {
          thumbHeight = (int)(thumbWidth / imageRatio);
        } else {
          thumbWidth = (int)(thumbHeight * imageRatio);
        }
        
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
        
        // save thumbnail image to OUTFILE
        BufferedOutputStream out = new BufferedOutputStream(thumbnail1.getOutputStream());
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.
          getDefaultJPEGEncodeParam(thumbImage);
        int quality = 85;
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float)quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close(); 
    }//generateThumbnails()
    
    
    public IFile getThumbnail() {
        return thumbnail1;
    }//getThumbnail()
    
    
}//class ImageContent
