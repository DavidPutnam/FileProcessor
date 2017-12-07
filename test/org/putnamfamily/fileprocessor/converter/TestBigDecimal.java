
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:56:08 PM
 * File id: $Id: TestBigDecimal.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestBigDecimal {
    private static final Class<?> testClass = BigDecimal.class;

    @Test
    public void testReadInt() {
        try {
            String original = "12345";
            String format = "0";
            BigDecimal expected = new BigDecimal(12345);
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
            String format = "000V00";
            // we need to use the string constructor because doubles are not presise
            BigDecimal expected = new BigDecimal("123.45");
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
            BigDecimal expected = new BigDecimal("2345.6");
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    //    @Test
    //    public void testScaleFormat() {
    //        try {
    //            converter converter = converterFactory.getInstance().create(testClass, "j");
    //            fail("No format exception received.");
    //        } catch (IllegalArgumentException ex) {
    //            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Format must be integral for BigDecimal converter."));
    //        } catch (converterException ex) {
    //            fail(ex.getMessage());
    //        }
    //    }

    @Test
    public void testNullFormat() {
        try {
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            fail("No format exception received.");
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Formats are required for Decimals."));
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadZero() {
        try {
            String original = "00000.";
            String format = "0";
            BigDecimal expected = new BigDecimal(0);
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
            String original = "      ";
            String format = "0V0";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadInvalidValue() {
        try {
            String original = "abcdef";
            String format = "0";
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
            BigDecimal original = new BigDecimal(12345);
            String format = "0";
            String expected = "12345";
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
            BigDecimal original = new BigDecimal("123.45");
            String format = "000V00";
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
            BigDecimal original = new BigDecimal("2345.6");
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
    public void testWriteNull() {
        try {
            BigDecimal original = null;
            String format = "0.0";
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
