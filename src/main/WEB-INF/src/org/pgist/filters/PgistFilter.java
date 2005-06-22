package org.pgist.filters;

import java.io.IOException;

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
 */
public class PgistFilter implements Filter {

    
    protected boolean forceCloseConnection = true;
    protected FilterConfig filterConfig = null;
    protected String ignoreURL = null;

    
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        forceCloseConnection = "true".equals(filterConfig.getInitParameter("force-close-connection"));
        ignoreURL = filterConfig.getInitParameter("ignore-url");
    }

    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //check if the user logged in
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getServletPath();
        if (!path.equals(ignoreURL)) {
            HttpSession session = req.getSession();
            if (session.getAttribute("user")==null) {
                //user has not logged on
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect( req.getContextPath() + ignoreURL );
                return;
            }
        }
        
        try {
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
    

}
