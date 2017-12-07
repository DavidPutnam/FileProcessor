
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 16, 2006
 * Time: 7:10:03 PM
 * File id: $Id: TestConverterException.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
public class TestConverterException {

    @Test
    public void testNoArgConstructor() {
        ConverterException ex = new ConverterException();
        assertEquals(null, ex.getMessage());
    }

    @Test
    public void testStringConstructor() {
        String message = "This is just a test.";
        ConverterException ex = new ConverterException(message);
        assertEquals(message, ex.getMessage());
    }

    @Test
    public void testThrowableConstructor() {
        Throwable cause = new Throwable();
        ConverterException ex = new ConverterException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testStringThrowableConstructor() {
        String message = "This is just a test.";
        Throwable cause = new Throwable();
        ConverterException ex = new ConverterException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
