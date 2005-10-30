package org.pgist.backing;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.pgist.component.UIAction;
import org.pgist.dao.DiscourseDAO;
import org.pgist.discourse.Discourse;
import org.pgist.discourse.ImageContent;
import org.pgist.discourse.LinkContent;
import org.pgist.discourse.Opinion;
import org.pgist.discourse.PdfContent;
import org.pgist.discourse.TextContent;
import org.pgist.emails.EmailSender;
import org.pgist.model.ITree;
import org.pgist.util.JSFUtil;
import org.pgist.util.ListTableBean;
import org.pgist.util.PgistFile;


/**
 * Conversation Backing Bean
 * @author kenny
 *
 */
public class DiscourseBean extends ListTableBean {

    
    private List discourses;
    private Discourse discourse;
    private Opinion opinion;
    private String content;
    private boolean emailRemind = false;
    

    public List getDiscourses() {
        return discourses;
    }
    

    public void setDiscoursess(List discourses) {
        this.discourses = discourses;
    }
    
    
    public Discourse getDiscourse() {
        return discourse;
    }


    public void setDiscourse(Discourse discourse) {
        this.discourse = discourse;
    }


    public Opinion getOpinion() {
        return opinion;
    }


    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }


    public boolean isEmailRemind() {
        return emailRemind;
    }


    public void setEmailRemind(boolean emailRemind) {
        this.emailRemind = emailRemind;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }
    

    /**
     * List All Discourses.
     * @return
     */
    public void listDiscourse(ActionEvent event, Map map) {
        
        try {
            discourses = DiscourseDAO.getDiscourseList(pageSetting);
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }//listDiscourse()
    
    
    public String readDiscourse() throws Exception {
        discourse = (Discourse) DiscourseDAO.load(Discourse.class, selectedId());
        opinion = (Opinion) discourse.getRoot();
        return "success";
    }//readDiscourse()
    
    
    public void readDiscourse(ActionEvent event) throws Exception {
        UIAction component = (UIAction) event.getComponent();
        Map params = component.getParams();
        discourse = (Discourse) DiscourseDAO.load(Discourse.class, new Long((String) params.get("treeId")));
        opinion = (Opinion) DiscourseDAO.load(Opinion.class, new Long((String) params.get("nodeId")));
    }//readDiscourse()
    
    
    public void newOpinion(ActionEvent event) throws Exception {
        UIAction component = (UIAction) event.getComponent();
        Map params = component.getParams();
        discourse = (Discourse) DiscourseDAO.load(Discourse.class, new Long((String) params.get("treeId")));
        opinion = (Opinion) DiscourseDAO.load(Opinion.class, new Long((String) params.get("nodeId")));
        
        Opinion opin = new Opinion();
        opin.setEmailRemind("true".equals(params.get("emailReminder")));
        
        String punctuate = (String) params.get("punctuate");
        opin.setTone(Integer.parseInt(punctuate));
        String cttType = (String) params.get("cttType");
        if ("0".equals(cttType)) {//text
            String cttText = (String) params.get("cttText");
            if (cttText!=null) {
                cttText = cttText.trim();
                TextContent content = new TextContent();
                content.setContent(cttText);
                DiscourseDAO.insert(content);
                opin.setContent(content);
            }
        } else if ("1".equals(cttType)) {
            UploadedFile image = (UploadedFile) params.get("cttImage");
            PgistFile file = new PgistFile();
            file.setName(image.getName());
            DiscourseDAO.insert(file);
            try {
                file.receive(image.getInputStream());
                ImageContent cttImage = new ImageContent();
                cttImage.setFile(file);
                DiscourseDAO.insert(cttImage);
                file = new PgistFile();
                file.setName("thumbnail1");
                DiscourseDAO.insert(file);
                cttImage.setThumbnail1(file);
                file = new PgistFile();
                file.setName("thumbnail2");
                DiscourseDAO.insert(file);
                cttImage.setThumbnail2(file);
                file = new PgistFile();
                file.setName("thumbnail3");
                DiscourseDAO.insert(file);
                cttImage.setThumbnail3(file);
                file = new PgistFile();
                file.setName("thumbnail4");
                DiscourseDAO.insert(file);
                cttImage.setThumbnail4(file);
                file = new PgistFile();
                file.setName("thumbnail5");
                DiscourseDAO.insert(file);
                cttImage.setThumbnail5(file);
                cttImage.generateThumbnails();
                opin.setContent(cttImage);
            } catch(Exception e) {
                e.printStackTrace();
                DiscourseDAO.delete(file);
            }
        } else if ("2".equals(cttType)) {
            String cttLink = (String) params.get("cttLink");
            if (cttLink!=null) {
                cttLink = cttLink.trim();
                LinkContent content = new LinkContent();
                content.setLink(cttLink);
                DiscourseDAO.insert(content);
                opin.setContent(content);
            }
        } else if ("3".equals(cttType)) {
            UploadedFile pdf = (UploadedFile) params.get("cttPDF");
            PgistFile file = new PgistFile();
            file.setName(pdf.getName());
            DiscourseDAO.insert(file);
            try {
                file.receive(pdf.getInputStream());
                PdfContent cttPDF = new PdfContent();
                cttPDF.setFile(file);
                DiscourseDAO.insert(cttPDF);
                opin.setContent(cttPDF);
            } catch(Exception e) {
                e.printStackTrace();
                DiscourseDAO.delete(file);
            }
        }
        opin.setOwner(JSFUtil.getCurrentUser());
        opin.setParent(opinion);
        opin.setTime(new Date());
        DiscourseDAO.insert(opin);
        
        opinion.addOpinion(opin);
        DiscourseDAO.update(opinion);
        
        if (opinion.isEmailRemind()) {
            Hashtable values = new Hashtable();
            values.put("discourse", discourse);
            values.put("origin", opinion);
            values.put("reply", opin);
            EmailSender.send(opinion.getOwner().getEmail(), "Reply to your opinion at PGIST.",
                    "do_reply_reminder", values);
        }
        
        opinion = opin;
    }//newOpinion()
    
    
    public String newDiscourse() throws Exception {
        discourse = new Discourse();
        discourse.setDeleted(false);
        discourse.setEnabled(true);
        discourse.setTitle("");
        
        opinion = new Opinion();
        opinion.setOwner(JSFUtil.getCurrentUser());
        opinion.setParent(null);
        opinion.setTime(new Date());
        opinion.setTitle("");
        opinion.setContent(null);
        opinion.setOwner(JSFUtil.getCurrentUser());
        opinion.setTone(1);
        
        content = "";
        
        discourse.setRoot(opinion);
        
        emailRemind = false;
        
        return "newDiscourse";
    }//newDiscourse()
    
    
    public String saveNewDiscourse() throws Exception {
        try {
            String title = discourse.getTitle();
            if (title==null || "".equals(title)) return "failure";
            
            if (content==null || "".equals(content)) return "failure";
            
            TextContent text = new TextContent();
            text.setContent(content);
            opinion.setContent(text);
            
            if (emailRemind) opinion.setEmailRemind(true);
            
            DiscourseDAO.insertOrUpdate(opinion);
            DiscourseDAO.insertOrUpdate(discourse);
            
            return "success";
        } catch(Exception e) {
            return "failure";
        }
    }//saveNewDiscourse()
    
    
    public static ITree getDiscourse(ActionEvent event, Long id) {
        ITree tree = null;
        try {
            tree = (ITree) DiscourseDAO.load(Discourse.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return tree;
    }//getDiscourse()
    
    
    public static Opinion saveOpinion(ActionEvent event, Long id, Integer tone, String s) throws Exception {
        Opinion opinion = new Opinion();
        
        Opinion parent = (Opinion) DiscourseDAO.load(Opinion.class, id);
        opinion.setParent(parent);
        opinion.setOwner(JSFUtil.getCurrentUser());
        TextContent content = new TextContent();
        content.setContent(s);
        opinion.setContent(content);
        opinion.setTime(new Date());
        opinion.setTitle("");
        opinion.setTone(tone.intValue());
        DiscourseDAO.insert(opinion);
        
        return opinion;
    }//saveOpinion()


}//class DiscourseBean
