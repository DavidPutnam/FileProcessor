
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
 * Time: 7:17:12 PM
 * File id: $Id: TestLong.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestLong {
    private static final Class<?> testClass = Long.class;

    @Test
    public void testReadInt() {
        try {
            String original = "23456";
            Long expected = 23456l;
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
            Long expected = 0l;
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
            String original = "     ";
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
            Long original = 23456l;
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
            Long original = 0l;
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
            Long original = null;
            String expected = "";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
