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
import org.pgist.emails.EmailTemplate;
import org.pgist.glossary.Term;
import org.pgist.nlp.ConversationThread;
import org.pgist.nlp.Post;
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
                
                //test nlp
                ConversationThread thread = new ConversationThread();
                thread.setEnabled(true);
                thread.setDeleted(false);
                thread.setTitle("This is the first thread");
                Post post = new Post();
                post.setTitle("This is A");
                post.setOwner(admin);
                post.setParent(null);
                post.setContent("content of A");
                post.setTime(new Date());
                post.setTone(1);
                thread.setRoot(post);
                Post post1 = new Post();
                post1.setTitle("This is B");
                post1.setOwner(admin);
                post1.setParent(post);
                post.addPost(post1);
                post1.setContent("content of B");
                post1.setTime(new Date());
                post1.setTone(2);
                session.saveOrUpdate(post);
                session.saveOrUpdate(post1);
                session.saveOrUpdate(thread);
                
                Term term = new Term();
                term.setName("transport");
                term.setShortDefinition("to move from one place to another");
                term.setExtDefinition("The percific method to move from one place to another, especially with the help of some kind of vehcles.");
                term.setInternal(true);
                term.setLink1("link1");
                term.setLink2("link2");
                term.setLink3("link3");
                term.setSource1("source1");
                term.setSource2("source2");
                term.setSource3("source3");
                term.setRelatedTerm1("term1");
                term.setRelatedTerm2("term2");
                term.setRelatedTerm3("term3");
                term.setRelatedTerm4("term4");
                term.setRelatedTerm5("term5");
                term.setOwner(admin);
                term.setCategory("");
                session.save(term);
                
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


}
