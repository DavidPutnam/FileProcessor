
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
 * File id: $Id: Test_float.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class Test_float {
    private static final Class<?> testClass = Float.TYPE;

    @Test
    public void testReadInt() {
        try {
            String original = "23456";
            String format = "0";
            float expected = 23456f;
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
            float expected = 234.56f;
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
            String format = "0.0";
            float expected = 2345.6f;
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
            float expected = 0f;
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
            String original = "        ";
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
    public void testInvalidFormat() {
        try {
            String format = "0g";
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            //fail("An Exception was expected.");
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Format must be integral for Float converter."));
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
            String format = "0V00";
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
            float original = 23456f;
            String format = "0";
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteValidImpliedDecimal() {
        try {
            float original = 234.56f;
            String format = "0V00";
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteValidSuppliedDecimal() {
        try {
            float original = 2345.6f;
            String format = "0.0";
            String expected = "2345.6";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteZero() {
        try {
            float original = 0f;
            String format = "0";
            String expected = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteTrailingZeros() {
        try {
            float original = 0f;
            String format = "0.0000";
            String expected = "0.0000";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
