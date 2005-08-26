package org.pgist.servlets;

import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.pgist.util.DurableObjectManager;


/**
 * This servlet is in charge of system initiation.
 * 
 * @author kenny
 *
 */
public class SystemInitServlet extends HttpServlet {

    
    public void init(ServletConfig config) throws ServletException {
        
        //this line is required by the servlet specification
        super.init(config);
        
        //create an instance of DurableObjectManager
        DurableObjectManager manager = DurableObjectManager.getInstance();
        
        //add to JNDI
        try {
            Context ctx = new InitialContext();
            ctx.unbind("org.pgist.DurableObjectManager");
            ctx.bind("org.pgist.DurableObjectManager", manager);
        } catch(Exception e) {
            e.printStackTrace();
            throw new ServletException("PGIST: Error initializing SystemInitServlet!");
        }
        
        //org.pgist.CONFIG_PATH
        String configPath = config.getInitParameter(DurableObjectManager.CONFIG_PATH);
        File path = new File(config.getServletContext().getRealPath("/"));
        path = new File(path, configPath);
        if (path.exists()) {
            DurableObjectManager.put(DurableObjectManager.CONFIG_PATH, path);
            System.out.println("PGIST: config path - "+path.getPath());
        } else {
            System.out.println("PGIST: config path - "+path.getPath()+" not exist");
        }
        
        //EMAIL_TEMPLATE_PATH
        String emailTemplatePath = config.getInitParameter(DurableObjectManager.EMAIL_TEMPLATE_PATH);
        File template = new File(path, emailTemplatePath);
        if (template.exists()) {
            DurableObjectManager.put(DurableObjectManager.EMAIL_TEMPLATE_PATH, template);
            System.out.println("PGIST: email template path - "+template.getPath());
        } else {
            System.out.println("PGIST: email template path - "+template.getPath()+" not exist");
        }
        
    }//init()
    
    
}//class SystemInitServlet
