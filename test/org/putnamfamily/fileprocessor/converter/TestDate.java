
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:49:45 PM
 * File id: $Id: TestDate.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestDate {
    private static final Class<?> testClass = Date.class;

    @Test
    public void testNullFormat() {
        try {
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Invalid format for Date Converter."));
        }
    }

    @Test
    public void testInvalidFormat() {
        // I had expected that formats without substitutable characters would be invalid.
        // apparently even a blank format is not 'invalid'
    }

    @Test
    public void testReadZeroField() {
        try {
            String original = "00000000";
            String format = "yyyyMMdd";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadBlankField() {
        try {
            String original = "        ";
            String format = "yyyyMMdd";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadJan1() {
        try {
            String original = "19800101";
            String format = "yyyyMMdd";
            Calendar cal = new GregorianCalendar(1980, Calendar.JANUARY, 1);
            Date expected = cal.getTime();
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadDateTime() {
        try {
            String original = "20060926130317";
            String format = "yyyyMMddHHmmss";
            Calendar cal = new GregorianCalendar(2006, Calendar.SEPTEMBER, 26, 13, 3, 17);
            Date expected = cal.getTime();
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadTimeZone() {
        try {
            // Current time zone is CDT (Two hours ahead of PDT)
            String original = "2001.07.04 AD at 12:08:56 PDT";
            String format = "yyyy.MM.dd G 'at' HH:mm:ss z";
            Calendar cal = new GregorianCalendar(2001, Calendar.JULY, 4, 14, 8, 56);
            Date expected = cal.getTime();
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadBadFormat() {
        try {
            String original = "2001.07.04";
            String format = "yyyyMMdd";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage().startsWith("Date data does not match format."));
        }
    }

    @Test
    public void testReadInvalidValue() {
        try {
            String original = "abcdef";
            String format = "yyyyMMdd";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("converterException Expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Date data does not match format."));
        }
    }

    @Test
    public void testWriteNullValue() {
        try {
            String format = "yyyyMMdd";
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(null);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteJan1() {
        try {
            Calendar cal = new GregorianCalendar(1980, Calendar.JANUARY, 1);
            Date original = cal.getTime();
            String format = "yyyyMMdd";
            String expected = "19800101";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteDateTime() {
        try {
            Calendar cal = new GregorianCalendar(2006, Calendar.SEPTEMBER, 26, 13, 3, 17);
            Date original = cal.getTime();
            String format = "yyyyMMddHHmmss";
            String expected = "20060926130317";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
