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
    private PgistFile thumbnail2;
    private PgistFile thumbnail3;
    private PgistFile thumbnail4;
    private PgistFile thumbnail5;


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


    /**
     * @return
     * @hibernate.many-to-one name="thumb2" column="thumb2_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getThumbnail2() {
        return thumbnail2;
    }


    public void setThumbnail2(PgistFile thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }


    /**
     * @return
     * @hibernate.many-to-one name="thumb3" column="thumb3_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getThumbnail3() {
        return thumbnail3;
    }


    public void setThumbnail3(PgistFile thumbnail3) {
        this.thumbnail3 = thumbnail3;
    }


    /**
     * @return
     * @hibernate.many-to-one name="thumb4" column="thumb4_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getThumbnail4() {
        return thumbnail4;
    }


    public void setThumbnail4(PgistFile thumbnail4) {
        this.thumbnail4 = thumbnail4;
    }


    /**
     * @return
     * @hibernate.many-to-one name="thumb5" column="thumb5_id"  class="org.pgist.util.PgistFile"
     */
    public PgistFile getThumbnail5() {
        return thumbnail5;
    }


    public void setThumbnail5(PgistFile thumbnail5) {
        this.thumbnail5 = thumbnail5;
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
        if (thumbnail2==null) return;
        if (thumbnail3==null) return;
        if (thumbnail4==null) return;
        if (thumbnail5==null) return;
        generateThumbnail(thumbnail1, 128);
        generateThumbnail(thumbnail2, 64);
        generateThumbnail(thumbnail3, 32);
        generateThumbnail(thumbnail4, 16);
        generateThumbnail(thumbnail5, 8);
    }//generateThumbnails()
    
    
    private void generateThumbnail(PgistFile thumbnail, int size) throws Exception {
        
        Image image = Toolkit.getDefaultToolkit().getImage(file.getPath());
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        mediaTracker.waitForID(0);
        
        int thumbWidth = size;
        int thumbHeight = size;
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
        BufferedOutputStream out = new BufferedOutputStream(thumbnail.getOutputStream());
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.
          getDefaultJPEGEncodeParam(thumbImage);
        int quality = 85;
        quality = Math.max(0, Math.min(quality, 100));
        param.setQuality((float)quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        out.close(); 
    }//generateThumbnail()
    
    
    public IFile getThumbnail(int depth) {
        IFile thumbnail = null;
        switch (depth) {
            case 1:
                thumbnail = thumbnail1;
                break;
            case 2:
                thumbnail = thumbnail2;
                break;
            case 3:
                thumbnail = thumbnail3;
                break;
            case 4:
                thumbnail = thumbnail4;
                break;
            case 5:
                thumbnail = thumbnail5;
                break;
            default:
                ;
        }//swith
        return thumbnail;
    }//getThumbnail()
    
    
}//class ImageContent
