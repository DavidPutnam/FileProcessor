
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.putnamfamily.fileprocessor.domain.TestingClass;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:37:43 PM
 * File id: $Id: TestFixedFieldAttribute.java 15 2008-10-02 00:36:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 15 $
 */
public class TestFixedFieldAttribute {
    //private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testValidString() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        String expected = "7890";
        assertEquals(expected, testingClass.getAttribute());
    }

    @Test
    public void testValidStringArray() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 7, 4, 1);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        String[] expected = { "7", "8", "9", "0" };
        String[] received = testingClass.getManyAttribs();
        assertArrayEquals(expected, received);
    }

    @Test
    public void testZeroSubLength() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Array element length not valid."));
        }
    }

    @Test
    public void testNegitiveSubLength() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 7, 4, -1);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Array element length not valid."));
        }
    }

    @Test
    public void testNonMultipleSubLength() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 7, 4, 3);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Field length not multiple of array element length."));
        }
    }

    @Test
    public void testBlankName() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("", null, 1, 4, 2);
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
        FixedFieldAttribute attribute = new FixedFieldAttribute(null, null, 1, 4, 0);
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
    public void testInvalidBegin() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 0, 4, 2);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Begin or length is not valid."));
        }
    }

    @Test
    public void testInvalidLength() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 1, 0, 2);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Begin or length is not valid."));
        }
    }

    @Test
    public void testStringArrayWithNull() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("manyAttribs", null, 7, 4, 1);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017 90");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        String[] expected = { "7", null, "9", "0" };
        String[] received = testingClass.getManyAttribs();
        assertArrayEquals(expected, received);
    }

    @Test
    public void testNoAttibute() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("nonexistant", null, 7, 4, 1);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        //assertTrue(ObjectUtils.equals(new TestingClass(), testingClass));
    }

    @Test
    public void test_int() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("intValue", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        int expected = 7890;
        assertEquals(expected, testingClass.getIntValue());
    }

    @Test
    public void test_double() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("_double", "00V00", 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "AMP0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        double expected = 78.90d;
        assertEquals(expected, testingClass.get_double(), 0.001);
    }

    @Test
    public void testPublicInt() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("publicInt", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "0017890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps
        int expected = 17890;
        assertEquals(expected, testingClass.publicInt);
    }

    @Test
    public void testNullLine() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("publicInt", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, null);
            fail("Exception expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            // exepect this to happen
        }
    }

    @Test
    public void testBeginAfterEnd() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 32, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "less than 32 chars");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
        assertEquals(null, testingClass.getAttribute());
    }

    @Test
    public void testEndAfterEnd() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 18, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "12345678901234567890");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
        assertEquals("890", testingClass.getAttribute());
    }

    @Test
    public void testObjectValue() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("objectAttr", "2", 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unable to get converter for attribute"));
        }
    }

    @Test
    public void testNonDistinctSetter() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("indistinct", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Non-distinct setMethod for attribute"));
        }
    }

    @Test
    public void testBadData() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("publicInt", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "abcdefg");
            fail("Exception expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unable to read value for attribute"));
        }
    }

    @Test
    public void testPrivateSetter() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        try {
            attribute.assignValue(testingClass, "1234   ");
            // test passes because the private setter is not found when looking at the
            // list of methods to invoke.  Therefore this field is invisable to the framework.
            //fail("Exception expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unable to access setMethod for attribute:"));
        }
    }

    @Test
    public void testCompareSame() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        FixedFieldAttribute attribute2 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        assertEquals("attribute1.compareTo(attribute2)", 0, attribute1.compareTo(attribute2));
        assertEquals("attribute2.compareTo(attribute1)", 0, attribute2.compareTo(attribute1));
    }

    @Test
    public void testSort() {
        List<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(new FixedFieldAttribute("sequence", null, 7, 7, 0));
        attributes.add(new FixedFieldAttribute("recType", null, 1, 6, 0));
        assertTrue("sequence should be first", "sequence".equals(attributes.get(0).getName()));
        Collections.sort(attributes);
        assertTrue("recType should be first", "recType".equals(attributes.get(0).getName()));
    }

    @Test
    public void testEqualsTrue() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        FixedFieldAttribute attribute2 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        assertTrue("attribute1.equals(attribute2)", attribute1.equals(attribute2));
        assertTrue("attribute2.equals(attribute1)", attribute2.equals(attribute1));
        assertEquals("hash codes", attribute1.hashCode(), attribute2.hashCode());
    }

    @Test
    public void testEqualsSame() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        assertTrue("attribute1.equals(attribute1)", attribute1.equals(attribute1));
    }

    @Test
    public void testNotEqualsNull() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        assertTrue("attribute1.equals(null)", !attribute1.equals(null));
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testNotEqualsClassDiff() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        DeliminatedAttribute attribute2 = new DeliminatedAttribute("privateInt", "", ';', 1, '.');
        assertTrue("attribute1.equals(attribute2)", !attribute1.equals(attribute2));
    }

    @Test
    public void testNotEqualsBegin() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        FixedFieldAttribute attribute2 = new FixedFieldAttribute("privateInt", null, 2, 7, 0);
        assertTrue("attribute1.equals(attribute2)", !attribute1.equals(attribute2));
        assertTrue("attribute2.equals(attribute1)", !attribute2.equals(attribute1));
    }

    @Test
    public void testNotEqualsLength() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        FixedFieldAttribute attribute2 = new FixedFieldAttribute("privateInt", null, 1, 8, 0);
        assertTrue("attribute1.equals(attribute2)", !attribute1.equals(attribute2));
        assertTrue("attribute2.equals(attribute1)", !attribute2.equals(attribute1));
    }

    @Test
    public void testNotEqualsSublength() {
        FixedFieldAttribute attribute1 = new FixedFieldAttribute("privateInt", null, 1, 7, 0);
        FixedFieldAttribute attribute2 = new FixedFieldAttribute("privateInt", null, 1, 7, 1);
        assertTrue("attribute1.equals(attribute2)", !attribute1.equals(attribute2));
        assertTrue("attribute2.equals(attribute1)", !attribute2.equals(attribute1));
    }

    @Test
    public void testWriteNull() {
        String expected = "    ";
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 1, 4, 0);
        try {
            String result = attribute.writeValue(null);
            assertEquals("Written String", expected, result);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteString() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        testingClass.setAttribute("7890");

        // verification steps
        String expected = "7890";
        try {
            assertEquals(expected, attribute.writeValue(testingClass));
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteInt() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("publicInt", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        testingClass.publicInt = 7890;

        // verification steps
        String expected = "7890";
        try {
            assertEquals(expected, attribute.writeValue(testingClass));
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteShort() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        testingClass.setAttribute("789");

        // verification steps
        String expected = " 789";
        try {
            assertEquals(expected, attribute.writeValue(testingClass));
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteLong() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 7, 4, 0);
        try {
            // The initialize class would come from the record data.
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass testingClass = new TestingClass();
        testingClass.setAttribute("78910");

        // verification steps
        @SuppressWarnings("unused")
        String expected;
        try {
            expected = attribute.writeValue(testingClass);
        } catch (FileParserException ex) {
            assertEquals("Field value does not fit in fixed field.", ex.getMessage());
        }
    }

    @Test
    public void testWriteNullDirect() {
        FixedFieldAttribute attribute = new FixedFieldAttribute("attribute", null, 7, 4, 0);
        @SuppressWarnings("unused")
        String expected;
        try {
            expected = attribute.writeAttribute(null);
        } catch (FileParserException ex) {
            assertEquals("Unable to write null value.", ex.getMessage());
        }
    }

    @Test
    public void testMultiArgSetter() {
        // initialization steps
        FixedFieldAttribute attribute = new FixedFieldAttribute("multiSetInt", null, 1, 7, 0);
        try {
            attribute.initialize(TestingClass.class);
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Non-distinct setMethod for attribute:"));
            return;
        }
        fail("Non-distinct setMethods should exist for multiSetInt.");
    }
}
