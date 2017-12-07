
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
 * File id: $Id: TestFloat.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestFloat {
    private static final Class<?> testClass = Float.class;

    @Test
    public void testReadInt() {
        try {
            String original = "23456";
            String format = "0";
            Float expected = 23456f;
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
            String original = "12345";
            String format = "0V00";
            // we need to use the string constructor because doubles are not presise
            Float expected = new Float("123.45");
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
            Float expected = 2345.6f;
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
            String original = "000.00";
            String format = "0";
            Float expected = 0f;
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
    public void testReadInvalidValue1() {
        try {
            String original = "abcde";
            String format = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
        }
    }

    @Test
    public void testReadInvalidValue2() {
        try {
            String original = "abcde.";
            String format = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
        }
    }

    @Test
    public void testInvalidFormat() {
        try {
            String original = "0000";
            String format = "0g";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
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
    public void testWriteInt() {
        try {
            Float original = 23456f;
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
            // we need to use the string constructor because doubles are not presise
            Float original = new Float("123.45");
            String format = "0V00";
            String expected = "12345";
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
            Float original = 2345.6f;
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
            Float original = 0f;
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
    public void testWriteNull() {
        try {
            Float original = null;
            String format = "0";
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
