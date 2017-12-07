
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
 * File id: $Id: Test_int.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class Test_int {
    private static final Class<?> testClass = Integer.TYPE;

    @Test
    public void testReadInt() {
        try {
            String original = "23456";
            int expected = 23456;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
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
            int expected = 0;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
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
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
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
            Converter converter = ConverterFactory.getInstance().create(testClass, "2");
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
            int original = 23456;
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteZero() {
        try {
            int original = 0;
            String expected = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteLeadingZeros() {
        try {
            int original = 56;
            String expected = "00056";
            Converter converter = ConverterFactory.getInstance().create(testClass, "00000");
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
