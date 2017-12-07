
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.putnamfamily.fileprocessor.domain.TestingClass;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 16, 2006
 * Time: 7:18:52 PM
 * File id: $Id: TestDeliminatedAttribute.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestDeliminatedAttribute {
    //private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testValidString() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("attribute", null, '^', 2, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP001^7890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        String expected = "7890";
        assertEquals(expected, testingClass.getAttribute());
    }

    @Test
    public void testBLankName() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("", null, '|', 1, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Name is not valid."));
        }
    }

    @Test
    public void testNullName() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute(null, null, '|', 1, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Name is not valid."));
        }
    }

    @Test
    public void testNegFieldIndex() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("attribute", null, '|', -1, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Field is not valid."));
        }
    }

    @Test
    public void testZeroFieldIndex() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("attribute", null, '|', 0, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Field is not valid."));
        }
    }

    @Test
    public void testNullSubDelim() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("manyAttribs", null, '|', 11, ',');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Array element deliminator not valid."));
        }
    }

    @Test
    public void testBlankSubDelim() {
        // initialization steps
        DeliminatedAttribute attribute = new DeliminatedAttribute("manyAttribs", null, '|', 1, ' ');
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            //fail(ex.getMessage());
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Invalid array lengh."));
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Array element deliminator not valid."));
        }
    }
}
