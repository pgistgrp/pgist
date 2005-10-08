package org.pgist.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pgist.dao.BaseDAO;
import org.pgist.util.PgistFile;


/**
 * This servlet is in charge of system initiation.
 * 
 * @author kenny
 *
 */
public class DownloadServlet extends HttpServlet {

    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ServletContext context = null;
    

    public void init(ServletConfig config) throws ServletException {
        //this line is required by the servlet specification
        super.init(config);
        
        context = config.getServletContext();
    }//init()
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OutputStream output = response.getOutputStream();
        
        try {
            Long id = new Long((String) request.getParameter("id"));
            PgistFile file = (PgistFile) BaseDAO.load(PgistFile.class, id);
            response.setContentType(context.getMimeType(file.getName()));
            InputStream stream = file.getStream();
            int n;
            byte[] b = new byte[1024];
            while ((n=stream.read(b))>0) {
                output.write(b, 0, n);
            }//while
        } catch(Exception e) {
            e.printStackTrace();
        }
    }//doGet()
    
    
}//class SystemInitServlet
