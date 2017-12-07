
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 28, 2006
 * Time: 7:47:39 PM
 * File id: $Id: TestShort.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestShort {
    private static final Class<?> testClass = Short.class;

    @Test
    public void testInt() {
        try {
            String original = "23456";
            Short expected = 23456;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testZero() {
        try {
            String original = "00000";
            Short expected = 0;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testBlankField() {
        try {
            String original = "     ";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testInvalidValue() {
        try {
            String original = "abcde";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            @SuppressWarnings("unused")
            Object received = converter.read(original);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Can not parse field."));
        }
    }

    @Test
    public void testWriteInt() {
        try {
            Short original = 23456;
            String expected = "23456";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteZero() {
        try {
            Short original = 0;
            String expected = "0";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteNull() {
        try {
            Short original = null;
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
