
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.putnamfamily.fileprocessor.domain.TestingClass;
import org.putnamfamily.fileprocessor.domain.TestingClass2;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 9, 2006
 * Time: 7:10:02 PM
 * File id: $Id: TestRecord.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestRecord {
    //private static final Logger LOGGER = LogManager.getLogger();

    @Test
    public void testValidFixedFields() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("intValue", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        Object received = null;
        try {
            if (record.isFirstOfSet()) {
                received = record.createInstance();
            }
            record.assignValues(received, "AMP0017890123420061009");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        if (received == null) {
            fail("record.createInstance failed but did not throw an Exception or firstOfSet is false.");
        }

        // verification steps
        String expectedAttribute = "7890";
        assertEquals(expectedAttribute, ((TestingClass) received).getAttribute());
        int expectedIntValue = 1234;
        assertEquals(expectedIntValue, ((TestingClass) received).getIntValue());
        Calendar cal = new GregorianCalendar(2006, Calendar.OCTOBER, 9);
        Date expectedDateValue = cal.getTime();
        assertEquals(expectedDateValue, ((TestingClass) received).getDateValue());
    }

    @Test
    public void testSuperField() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("null", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass2 received = null;
        try {
            if (record.isFirstOfSet()) {
                received = (TestingClass2) record.createInstance();
            }
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
        if (received == null) {
            fail("Unable to create instance.");
        }

        try {
            record.assignValues(received, "AMP0017890123420061009");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps  - Test all three attributes defined.
        String expectedAttributeValue = "7890";
        assertEquals(expectedAttributeValue, received.getAttribute());
        int expectedIntValue = 1234;
        assertEquals(expectedIntValue, received.publicInt);
        Calendar cal = new GregorianCalendar(2006, Calendar.OCTOBER, 9);
        Date expectedDateValue = cal.getTime();
        assertEquals(expectedDateValue, received.getDateValue());
    }

    @Test
    public void testUninitCreate() {
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        // do not invoke record.initialize
        // utilization steps
        try {
            record.createInstance();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("createInstance invoked without being initialized."));
        }
    }

    @Test
    public void testUninitAssign() {
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        // do not invoke record.initialize
        // utilization steps
        TestingClass2 received = new TestingClass2();
        try {
            record.assignValues(received, "AMP0017890123420061009");
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("assignValues invoked without being initialized."));
        }
    }

    @Test
    public void testNullName() {
        // initialization steps
        DataFileRecord record = new DataFileRecord(null, "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Name is not valid."));
        }
    }

    @Test
    public void testBlankName() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Name is not valid."));
        }
    }

    @Test
    public void testUnknownClass() {
        // initialization steps
        // class name is not org.putnamfamily.fileprocessor.domain.TestingClass2
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass3", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("intValue", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Cannot load class:"));
        }
    }

    @Test
    public void testMissmatchedClasses() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass received = new TestingClass();
        try {
            record.assignValues(received, "AMP0017890123420061009");
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Target class missmatch, meant to assign values on"));
        }
    }

    @Test
    public void testNullTarget() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("null", "org.putnamfamily.fileprocessor.domain.TestingClass", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("intValue", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        TestingClass received = null;
        try {
            record.assignValues(received, "AMP0017890123420061009");
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Source or destination value is 'null'."));
        }
    }

    @Test
    public void testNullSource() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("intValue", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        Object received;
        try {
            received = record.createInstance();
            record.assignValues(received, null);
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Source or destination value is 'null'."));
        }
    }

    @Test
    public void testNullSameAs() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("AMP001", null);

        try {
            record.initialize();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(),
                ex.getMessage().startsWith("Either targetClassName or sameAs must be set, and are mutually exclusive."));
        }
    }

    @Test
    public void testBlankSameAs() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("AMP001", "");

        try {
            record.initialize();
            fail("Execption Expected.");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(),
                ex.getMessage().startsWith("Either targetClassName or sameAs must be set, and are mutually exclusive."));
        }
    }

    @Test
    public void testFixedCoverageComplete() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 1, 6, 0));
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        record.verifyCoverage();
    }

    @Test
    public void testFixedCoverageGap() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        record.verifyCoverage();
    }

    @Test
    public void testFixedCoverageOverlap() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new FixedFieldAttribute("attribute", null, 1, 12, 0));
        record.addAttribute(new FixedFieldAttribute("attribute", null, 7, 4, 0));
        record.addAttribute(new FixedFieldAttribute("publicInt", null, 11, 4, 0));
        record.addAttribute(new FixedFieldAttribute("dateValue", "yyyyMMdd", 15, 8, 0));

        record.verifyCoverage();
    }

    @Test
    public void testDelimCoverageComplete() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 1, ','));
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 2, ','));
        record.addAttribute(new DeliminatedAttribute("publicInt", null, '|', 3, ','));
        record.addAttribute(new DeliminatedAttribute("dateValue", "yyyyMMdd", '|', 4, ','));

        record.verifyCoverage();
    }

    @Test
    public void testDelimCoverageGap() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 1, ','));
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 5, ','));
        record.addAttribute(new DeliminatedAttribute("publicInt", null, '|', 3, ','));
        record.addAttribute(new DeliminatedAttribute("dateValue", "yyyyMMdd", '|', 4, ','));

        record.verifyCoverage();
    }

    @Test
    public void testDelimCoverageOverlap() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("anything", "org.putnamfamily.fileprocessor.domain.TestingClass2", true, true);
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 1, ','));
        record.addAttribute(new DeliminatedAttribute("attribute", null, '|', 1, ','));
        record.addAttribute(new DeliminatedAttribute("publicInt", null, '|', 2, ','));
        record.addAttribute(new DeliminatedAttribute("dateValue", "yyyyMMdd", '|', 3, ','));

        record.verifyCoverage();
    }

    //'Unable to create ' (InstantiationException IllegalAccessException)
    @Test
    public void testNoAttributes() {
        // initialization steps
        DataFileRecord record = new DataFileRecord("AMP001", "org.putnamfamily.fileprocessor.domain.TestingClass", true, true);

        try {
            record.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // utilization steps
        Object received = null;
        try {
            if (record.isFirstOfSet()) {
                received = record.createInstance();
            }
            record.assignValues(received, "AMP0017890123420061009");
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        if (received == null) {
            fail("record.createInstance failed but did not throw an Exception or firstOfSet is false.");
        }
    }
}
