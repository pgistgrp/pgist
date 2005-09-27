package org.pgist.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.ArrayHelper;
import org.pgist.glossary.Term;
import org.pgist.glossary.TermCategory;
import org.pgist.glossary.TermLink;
import org.pgist.glossary.TermSource;

import com.Ostermiller.util.ExcelCSVParser;


/**
 * Import initial data the PGIST system
 * @author kenny
 *
 */
public class GloassaryImport extends MatchingTask {
    
    
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
                
                //load predefined categories
                Query query = session.createQuery("from TermCategory where name=:name");
                query.setString("name", "Developer");
                TermCategory categoryDeveloper = (TermCategory) query.iterate().next();
                
                query.setString("name", "Public");
                TermCategory categoryPublic = (TermCategory) query.iterate().next();
                
                query.setString("name", "Puget Sound Region");
                TermCategory categoryPSR = (TermCategory) query.iterate().next();

                query.setString("name", "Transportation Improvement Programming");
                TermCategory categoryTIP = (TermCategory) query.iterate().next();
                
                String[] csvFiles = getFiles();
                for (int i=0; i<csvFiles.length; i++) {
                    String fileName = csvFiles[i];
                    System.out.println(" Process file "+fileName);
                    
                    BufferedReader in = new BufferedReader(
                        new FileReader(fileName)
                    );
                    
                    String line;
                    int count = 1;
                    
                    //first pass
                    //read and reject the first line
                    in.readLine();
                    while ((line = in.readLine())!=null) {
                        if ("".equals(line)) continue;
                        String[] values = ((String[][]) ExcelCSVParser.parse(line))[0];
                        
                        System.out.println(" Line "+(++count)+"  --->  "+values[0]);
                        
                        Term term = new Term();
                        
                        //name
                        term.setName(values[0]);
                        
                        //short definition
                        if (values.length>1) {
                            term.setShortDefinition(values[1]);
                        }
                        
                        //ext definition
                        if (values.length>2) {
                            term.setExtDefinition(values[2]);
                        }
                        
                        //sources
                        for (int j=3; j<6; j++) {
                            if (values.length<=j) break;
                            if (values[j]!=null && !"".equals(values[j])) {
                                TermSource source = new TermSource();
                                source.setSource(values[j]);
                                session.save(source);
                                term.getSources().add(source);
                            }
                        }//for j
                        
                        //related terms
                        //delay to another pass
                        for (int j=6; j<11; j++) {
                            if (values.length<=j) break;
                        }//for j
                        
                        //links
                        for (int j=11; j<14; j++) {
                            if (values.length<=j) break;
                            if (values[j]!=null && !"".equals(values[j])) {
                                TermLink link = new TermLink();
                                link.setLink(values[j]);
                                session.save(link);
                                term.getLinks().add(link);
                            }
                        }//for j
                        
                        //Public Category
                        if (values.length>14 && "1".equals(values[14])) {
                            term.getCategories().add(categoryPublic);
                        }

                        //Developer Category
                        if (values.length>15 && "1".equals(values[15])) {
                            term.getCategories().add(categoryDeveloper);
                        }

                        //TIP Category
                        if (values.length>16 && "1".equals(values[16])) {
                            term.getCategories().add(categoryTIP);
                        }

                        //TIP Category
                        if (values.length>17 && "1".equals(values[17])) {
                            term.getCategories().add(categoryPSR);
                        }
                        
                        session.save(term);
                        //if (count==10) break;
                    }//while first pass
                    
                    in.close();
                    in = new BufferedReader(new FileReader(fileName));
                    
                    //second pass, process related terms
                    //read and reject the first line
                    in.readLine();
                    count = 1;
                    while ((line = in.readLine())!=null) {
                        
                        if ("".equals(line)) continue;
                        String[] values = ((String[][]) ExcelCSVParser.parse(line))[0];
                        //System.out.println(" Related terms, Line "+(++count)+"  --->  "+values[0]);
                        
                        query = session.createQuery("from Term where name=:name");
                        query.setString("name", values[0]);
                        Term theTerm = (Term) query.iterate().next();
                        
                        for (int j=6; j<11; j++) {
                            if (values.length>j && !"".equals(values[j])) {
                                query = session.createQuery("from Term where name=:name");
                                query.setString("name", values[j]);
                                //System.out.println("---> "+values[j]);
                                Iterator iter = query.iterate();
                                if (iter.hasNext()) {
                                    Term one = (Term) iter.next();
                                    if (one!=null) {
                                        //System.out.println("found ---> "+one.getName());
                                        theTerm.getRelatedTerms().add(one);
                                        one.getRelatedTerms().add(theTerm);
                                    }
                                } else {
                                    System.out.println("xxxx term "+values[j]+" not found!");
                                }
                            }
                        }//for j
                        
                        session.saveOrUpdate(theTerm);
                    }//while second pass
                    
                    in.close();
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
    

    private String[] getFiles() {

        List files = new LinkedList();
        for ( Iterator i = fileSets.iterator(); i.hasNext(); ) {

            FileSet fs = (FileSet) i.next();
            DirectoryScanner ds = fs.getDirectoryScanner( getProject() );

            String[] dsFiles = ds.getIncludedFiles();
            for (int j = 0; j < dsFiles.length; j++) {
                File f = new File(dsFiles[j]);
                if ( !f.isFile() ) {
                    f = new File( ds.getBasedir(), dsFiles[j] );
                }

                files.add( f.getAbsolutePath() );
            }
        }

        return ArrayHelper.toStringArray(files);
    }
    

    private Configuration getConfiguration() throws Exception {
        Configuration cfg = new Configuration();
        if (configurationFile != null) cfg.configure( new File(configurationFile) );
        return cfg;
    }


}//class GloassaryImport
