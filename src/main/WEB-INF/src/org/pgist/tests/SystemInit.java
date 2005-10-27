package org.pgist.tests;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.util.ArrayHelper;
import org.pgist.discourse.Discourse;
import org.pgist.discourse.Opinion;
import org.pgist.discourse.TextContent;
import org.pgist.emails.EmailTemplate;
import org.pgist.glossary.TermCategory;
import org.pgist.users.Role;
import org.pgist.users.User;


/**
 * Init the PGIST system
 * @author kenny
 *
 */
public class SystemInit extends MatchingTask {
    
    
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
                
                //role - member
                Role roleMember = new Role();
                roleMember.setName("admin");
                roleMember.setDescription("Administrator");
                roleMember.setInternal(true);
                session.save(roleMember);
                System.out.println("---- successfully inserte a role: admin");
                
                //role - admin
                Role roleAdmin = new Role();
                roleAdmin.setName("member");
                roleAdmin.setDescription("Member");
                roleAdmin.setInternal(true);
                session.save(roleAdmin);
                System.out.println("---- successfully inserte a role: member");
                
                //role - guest
                Role roleGuest = new Role();
                roleGuest.setName("guest");
                roleGuest.setDescription("Guest");
                roleGuest.setInternal(true);
                session.save(roleGuest);
                System.out.println("---- successfully inserte a role: guest");
                
                //role - test
                for (int i=1; i<20; i++) {
                    String s = "role-"+i;
                    Role role = new Role();
                    role.setDeleted(false);
                    role.setInternal(false);
                    role.setName(s);
                    role.setDescription("role "+i);
                    session.save(role);
                    System.out.println("---- successfully inserte a role: "+s);
                }//for i

                //user - admin
                User admin = new User();
                admin.setLoginname("admin");
                admin.setFirstname("Admin");
                admin.setLastname("Admin");
                admin.setPassword("admin");
                admin.encodePassword();
                admin.setEmail("admin@pgist.org");
                admin.setEnabled(true);
                admin.setDeleted(false);
                admin.setInternal(true);
                admin.getRoles().add(roleMember);
                admin.getRoles().add(roleAdmin);
                session.save(admin);
                System.out.println("---- successfully inserte a user: admin");
                
                //user - guest
                User guest = new User();
                guest.setLoginname("guest");
                guest.setFirstname("Guest");
                guest.setLastname("Guest");
                guest.setPassword("guest");
                guest.encodePassword();
                guest.setEmail("guest@pgist.org");
                guest.setEnabled(true);
                guest.setDeleted(false);
                guest.setInternal(true);
                guest.getRoles().add(roleGuest);
                session.save(guest);
                System.out.println("---- successfully inserte a user: guest");
                
                //user - test
                for (int i=1; i<20; i++) {
                    String s = "test-"+i;
                    User test = new User();
                    test.setEnabled(true);
                    test.setDeleted(false);
                    test.setInternal(false);
                    test.getRoles().add(roleGuest);
                    test.setLoginname(s);
                    test.setFirstname("Test"+i);
                    test.setLastname("Guest"+i);
                    test.setPassword(s);
                    test.encodePassword();
                    test.setEmail(s+"@pgist.org");
                    session.save(test);
                    System.out.println("---- successfully inserte a user: "+s);
                }//for i
                
                //discourse object
                Discourse discourse = new Discourse();
                discourse.setDeleted(false);
                discourse.setEnabled(true);
                discourse.setTitle("This is my test!");
                Opinion opinion = new Opinion();
                TextContent content = new TextContent();
                content.setContent("This is the contents!");
                opinion.setContent(content);
                opinion.setOwner(admin);
                opinion.setTime(new Date());
                opinion.setTitle("The root node");
                opinion.setTone(1);
                discourse.setRoot(opinion);
                session.save(content);
                
                Opinion opinion1 = new Opinion();
                content = new TextContent();
                content.setContent("This is the first reply!");
                opinion1.setContent(content);
                opinion1.setOwner(guest);
                opinion1.setTime(new Date());
                opinion1.setTitle("The second node");
                opinion1.setTone(1);
                opinion1.setParent(opinion);
                opinion.getChildren().add(opinion1);
                session.save(content);
                
                Opinion opinion2 = new Opinion();
                content = new TextContent();
                content.setContent("This is the second reply!");
                opinion2.setContent(content);
                opinion2.setOwner(guest);
                opinion2.setTime(new Date());
                opinion2.setTitle("The third node");
                opinion2.setTone(1);
                opinion2.setParent(opinion1);
                opinion1.getChildren().add(opinion2);
                session.save(content);

                session.save(opinion);
                session.save(opinion1);
                session.save(opinion2);
                session.save(discourse);
                
                //Predefined categories
                TermCategory category = new TermCategory();
                category.setName("Developer");
                category.setInternal(true);
                category.setDescription("category for developers");
                session.save(category);
                
                category = new TermCategory();
                category.setName("Public");
                category.setInternal(true);
                category.setDescription("category for public");
                session.save(category);
                
                category = new TermCategory();
                category.setName("Puget Sound Region");
                category.setInternal(true);
                category.setDescription("category for terms related to things in our area, specifically");
                session.save(category);

                category = new TermCategory();
                category.setName("Transportation Improvement Programming");
                category.setInternal(true);
                category.setDescription("For things related to planning and decision-making, as opposed to general terms like monorail, ethanol, etc.");
                session.save(category);
                
                EmailTemplate template = new EmailTemplate();
                template.setName("register user success");
                template.setDescription("template for informing the new registered user");
                template.setNotes("Required variables: ${user}");
                template.setContent("");
                session.save(template);
                
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

        String[] files = getFiles();
        for (int i = 0; i < files.length; i++) {
            String filename = files[i];
            if ( filename.endsWith(".jar") ) {
                cfg.addJar( new File(filename) );
            }
            else {
                cfg.addFile(filename);
            }
        }
        return cfg;
    }


}//class SystemInit
