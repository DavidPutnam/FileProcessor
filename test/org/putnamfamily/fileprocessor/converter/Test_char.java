
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
 * File id: $Id: Test_char.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class Test_char {
    private static final Class<?> testClass = Character.TYPE;

    @Test
    public void testReadBlankField() {
        try {
            String original = "       ";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertNull(received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    public void testReadCharacter() {
        try {
            String original = "C      ";
            char expected = 'C';
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            Object received = converter.read(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    public void testWriteCharacter() {
        try {
            char original = 'C';
            String expected = "C";
            Converter converter = ConverterFactory.getInstance().create(testClass, null);
            String received = converter.write(original);
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }
}
