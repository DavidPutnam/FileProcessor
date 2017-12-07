
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:49:45 PM
 * File id: $Id: Test_boolean.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class Test_boolean {
    private static final Class<?> testClass = Boolean.TYPE;

    @Test
    public void testReadTrue() {
        try {
            String original = "true";
            boolean expected = true;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testReadFalse() {
        try {
            String original = "false";
            boolean expected = false;
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
            String original = "abcde";
            boolean expected = false;
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteTrue() {
        try {
            boolean original = true;
            String expected = "true";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testWriteFalse() {
        try {
            boolean original = false;
            String expected = "false";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
