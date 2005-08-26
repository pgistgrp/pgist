package org.pgist.emails;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.pgist.dao.EmailTemplateDAO;
import org.pgist.util.DurableObjectManager;


/**
 * A Util class for sending email
 * @author kenny
 *
 */
public class EmailSender {


    private static Properties props = new Properties();
    private static String contentType;
    private static String from;
    private static String username;
    private static String password;
    
    
    static {
        File config = new File( (String) DurableObjectManager.get(DurableObjectManager.EMAIL_CONFIG) );
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(config.toURL());
            
            List list = document.selectNodes( "//server/property" );
            for (Iterator iter=list.iterator(); iter.hasNext(); ) {
                Node property = (Node) iter.next();
                props.put(property.valueOf( "@name" ), property.getText());
            }//for iter
            
            Node node = document.selectSingleNode( "//server/from" );
            from = node.getText();
            
            node = document.selectSingleNode( "//server/username" );
            username = node.getText();

            node = document.selectSingleNode( "//server/password" );
            password = node.getText();

            node = document.selectSingleNode( "//server/content-type" );
            contentType = node.getText();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }//static
    
    
    public static void send(String to, String subject, String content) throws Exception {
        Session session = Session.getDefaultInstance(props, null);
        
        // create a message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        InternetAddress[] address = InternetAddress.parse(to);
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(content, contentType);
        Transport transport = session.getTransport();
        transport.connect(props.getProperty("mail.smtp.host"), username, password);
        transport.sendMessage(msg, address);
        transport.close();
    }//send()
    
    
    public static void send(String to, String subject, String template, Hashtable values) throws Exception {
        send(to, subject, merge(template, values));
    }//send()


    private static String merge(String name, Hashtable values) throws Exception {
        EmailTemplate template = EmailTemplateDAO.getTemplateByName(name);
        
        StringBuffer buf = new StringBuffer();
        Pattern pattern = Pattern.compile("\\$\\{.*?\\}");
        Matcher matcher = pattern.matcher(template.getContent());
        
        while(matcher.find()) {
            String matchStr = matcher.group();
            String bean = matchStr.substring(2, matchStr.length()-1);
            
            String s;
            int index = bean.indexOf(".");
            if (index<=0) {
                Object obj = values.get(bean);
                if (obj==null) {
                    s = matchStr;
                } else {
                    s = obj.toString();
                }
            } else {
                Object obj = values.get(bean.substring(0, index));
                if (obj==null) {
                    s = matchStr;
                } else {
                    s = BeanUtils.getNestedProperty(obj, bean.substring(index+1));
                }
            }
            
            matcher.appendReplacement(buf, s);
        }//while
        matcher.appendTail(buf);
        
        return buf.toString();
    }//merge()
    
    
}//class EmailSender
