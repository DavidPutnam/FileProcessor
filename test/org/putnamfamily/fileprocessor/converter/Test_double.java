
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:49:45 PM
 * File id: $Id: Test_double.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class Test_double {
    private static final Class<?> testClass = Double.TYPE;

    @Test
    public void testReadInt() {
        try {
            String original = "23456";
            String format = "0";
            double expected = 23456d;
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadValidImpliedDecimal() {
        try {
            String original = "23456";
            String format = "0V00";
            double expected = 234.56d;
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadValidSuppliedDecimal() {
        try {
            String original = "2345.6";
            String format = "000.0";
            double expected = 2345.6d;
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadZero() {
        try {
            String original = "00000";
            String format = "0";
            double expected = 0d;
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadBlankField() {
        try {
            String original = "     ";
            String format = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadInvalidNumber() {
        try {
            String original = "abcde";
            String format = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().contains("abcde"));
        }
    }

    @Test
    public void testReadInvalidFormat() {
        try {
            String original = "0000";
            String format = "0g";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
        } catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testNullFormat() {
        try {
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Formats are required for Decimals."));
        }
    }

    @Test
    public void testReadInvalidValue() {
        try {
            String original = "abcdef";
            String format = "2";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("converterException Expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
        }
    }

    @Test
    public void testWriteInt() {
        try {
            double original = 23456d;
            String format = "0";
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteValidImpliedDecimal() {
        try {
            double original = 234.56d;
            String format = "0V00";
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteValidSuppliedDecimal() {
        try {
            double original = 2345.6d;
            String format = "000.0";
            String expected = "2345.6";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteZero() {
        try {
            double original = 0d;
            String format = "0";
            String expected = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteLeadingZero() {
        try {
            double original = 234.5d;
            String format = "0000.0";
            String expected = "0234.5";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteLossPrecision() {
        try {
            double original = 23.456d;
            String format = "####0.00";
            String expected = "23.46";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
