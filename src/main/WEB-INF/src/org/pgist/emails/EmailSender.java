package org.pgist.emails;

import java.io.File;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
        File path = (File) DurableObjectManager.get(DurableObjectManager.CONFIG_PATH);
    }//static
    
    
    public void send(String to, String subject, String content) throws Exception {
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
    }
    
    
    public void send(String to, String subject, String template, Hashtable values) throws Exception {
        send(to, subject, merge(template, values));
    }


    private String merge(String template, Hashtable values) {
        return null;
    }
    
    
}//class EmailSender
