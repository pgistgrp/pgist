package org.pgist.util;

import java.util.Hashtable;


/**
 * Objects and classes are subject to be garbage collected by JVM gc.
 * DurableObjectManager is a special object which will be put into the
 * JNDI context and will be exempted from being garbage collected.
 * 
 * Put any objects you want to protected from being garbage collected to
 * DurableObjectManager.
 * 
 * DurableObjectManager adopts a singleton model.
 * 
 * SystemInitServlet will put the instance of DurableObjectManager to
 * the Tomcat JNDI context to protect the instance when it initializes.
 * 
 * @author kenny
 *
 */
public class DurableObjectManager {

    
    public static final String CONFIG_PATH = "org.pgist.CONFIG_PATH";
    public static final String EMAIL_CONFIG = "org.pgist.EMAIL_CONFIG";
    public static final String MANAGED_FILE_PATH = "org.pgist.MANAGED_FILE_PATH";
    
    
    private static Hashtable ht = new Hashtable();
    private static DurableObjectManager instance = new DurableObjectManager();
    
    
    private DurableObjectManager() {
    }


    public static DurableObjectManager getInstance() {
        return instance;
    }
    
    
    public static void put(String key, Object value) {
        ht.put(key, value);
    }
    
    
    public static Object get(String key) {
        return ht.get(key);
    }
    
    
}//class DurableObjectManager
