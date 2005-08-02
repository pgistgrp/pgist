package org.pgist.filters;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.pgist.util.HibernateUtil;


/**
 * Filter for PGIST
 * @author kenny
 * 
 * Function 1: check if the user already logged in for most requests.
 *          2: automacticly close any database connection involved in one request
 *
 */
public class PgistFilter implements Filter {

    
    protected FilterConfig filterConfig = null;
    
    /*
     * If forceCloseConnection is true, each request will cause the system to try to close
     * any database connection involved in this request.
     */
    protected boolean forceCloseConnection = true;
    
    /*
     * The login URL.
     */
    protected String loginURL = null;
    
    /*
     * All request urls in the ignoreURLs will not be checked if the user has logged in.
     */
    protected HashSet ignoreURLs = new HashSet();

    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        
        forceCloseConnection = "true".equals(filterConfig.getInitParameter("force-close-connection"));
        
        /*
         * loginURL should be in the ignoreURLs.
         */
        loginURL = filterConfig.getInitParameter("login-url");
        ignoreURLs.add(loginURL);
        
        String ignoreURL = filterConfig.getInitParameter("ignore-url");
        String[] array = ignoreURL.split(",");
        for (int i=0; i<array.length; i++) {
            ignoreURLs.add(array[i]);
        }
    }

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //check if the user logged in
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI();
        String ctxPath = req.getContextPath();
        path = path.substring(ctxPath.length());
        
        System.out.println("---> @ PgistFilter.doFilter "+req.getRequestURI());
        System.out.println("---> "+ignoreURLs+"       "+ignoreURLs.contains(path));
        System.out.println("getContextPath() ---> "+req.getContextPath());
        System.out.println("getPathInfo()    ---> "+req.getPathInfo());
        System.out.println("getRequestURI()  ---> "+req.getRequestURI());
        System.out.println("getRequestURL()  ---> "+req.getRequestURL());
        System.out.println("getServletPath()  ---> "+req.getServletPath());
        if (!ignoreURLs.contains(path)) {
            HttpSession session = req.getSession();
            if (session.getAttribute("user")==null) {
                //user has not logged on
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect( req.getContextPath() + loginURL );
                return;
            }
        }
        
        try {
            System.out.println("---> ok, we now go to service");
            chain.doFilter(request, response);
        } finally {
            if (forceCloseConnection) {
                try {
                    HibernateUtil.rollback();
                    HibernateUtil.closeSession();
                } catch (Exception e) {
                }
            }
        }
    }
    

    public void destroy() {
    }
    

}//class PgistFilter

