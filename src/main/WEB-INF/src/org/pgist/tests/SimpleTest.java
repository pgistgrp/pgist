package org.pgist.tests;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.pgist.discourse.Opinion;


/**
 * Simple Test of the PGIST system
 * @author kenny
 *
 */
public class SimpleTest extends MatchingTask {
    
    
    private List fileSets = new LinkedList();
    private String configurationFile = null;

    
    public void addFileset(FileSet set) {
        fileSets.add(set);
    }

    
    /**
     * Set a properties file
     * @param propertiesFile the properties file name
     */
    public void setProperties(File propertiesFile) {
        if ( !propertiesFile.exists() ) {
            throw new BuildException("Properties file: " + propertiesFile + " does not exist.");
        }

        log("Using properties file " + propertiesFile, Project.MSG_DEBUG);
    }
    

    /**
     * Set a <literal>.cfg.xml</literal> file, which will be
     * loaded as a resource, from the classpath
     * @param configurationFile the path to the resource
     */
    public void setConfig(String configurationFile) {
        this.configurationFile = configurationFile;
    }


    /**
     * Execute the task
     */
    public void execute() throws BuildException {
        try {
            Configuration cfg = getConfiguration();
            SessionFactory sessionFactory = cfg.buildSessionFactory();
            
            //initialization begins here
            
            /* create administrator user */
            Session session = null;
            try {
                session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                
                Query query = session.createQuery("from Opinion");
                for (Iterator iter=query.iterate(); iter.hasNext(); ) {
                    Opinion one = (Opinion) iter.next();
                    //Opinion two = (Opinion) one.getRoot();
                    System.out.println("---> "+one.getContent());
                }//for iter
                
                transaction.commit();
            } catch(Exception ex) {
                ex.printStackTrace();
            } finally {
                session.close();
            }
            
            //initialization ends here
            
        } catch (Exception e) {
            throw new BuildException(e);
        }
    }
    

    private Configuration getConfiguration() throws Exception {
        Configuration cfg = new Configuration();
        if (configurationFile != null) cfg.configure( new File(configurationFile) );
        return cfg;
    }


}//class GloassaryImport
