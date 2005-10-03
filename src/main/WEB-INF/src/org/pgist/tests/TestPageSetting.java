package org.pgist.tests;

import org.pgist.util.PageSetting;

import junit.framework.TestCase;


/**
 * Unit Test for class: org.pgist.util.PageSetting
 * @author kenny
 *
 */
public class TestPageSetting extends TestCase {

    
    public void testGetRowBound1() {
        PageSetting setting = new PageSetting();
        setting.setPage(0);
        setting.setRowOfPage(15);
        //Query db for the row size
        setting.setRowSize(85);
        assertEquals(0, setting.getFirstRow());
        assertEquals(14, setting.getLastRow());
    }//testGetRowBound1()

    
    public void testGetRowBound2() {
        PageSetting setting = new PageSetting();
        setting.setPage(3);
        setting.setRowOfPage(15);
        //Query db for the row size
        setting.setRowSize(85);
        assertEquals(30, setting.getFirstRow());
        assertEquals(44, setting.getLastRow());
    }//testGetRowBound2()

    
    public void testAttributes() {
        PageSetting setting = new PageSetting();
        setting.set("key", "key test");
        assertEquals("key test", setting.get("key"));
    }//testGetRowBound2()

    
}//class TestPageSetting
