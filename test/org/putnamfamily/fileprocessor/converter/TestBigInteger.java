
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:56:08 PM
 * File id: $Id: TestBigInteger.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestBigInteger {
    private static final Class<?> testClass = BigInteger.class;

    @Test
    public void testReadInt() {
        try {
            String original = "12345";
            String format = null;
            BigInteger expected = new BigInteger("12345");
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
            String original = "00,000";
            String format = "#,##0";
            BigInteger expected = BigInteger.ZERO;
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
            String original = "       ";
            String format = "0";
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
            BigInteger original = new BigInteger("12345");
            String format = null;
            String expected = "12345";
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
            BigInteger original = BigInteger.ZERO;
            String format = null;
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
            BigInteger original = null;
            String format = null;
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, format);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
