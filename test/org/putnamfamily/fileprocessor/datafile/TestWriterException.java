
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 16, 2006
 * Time: 7:07:54 PM
 * File id: $Id: TestParseException.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestWriterException {

    @Test
    public void testNoArgConstructor() {
        FileWriterException ex = new FileWriterException();
        assertEquals(null, ex.getMessage());
    }

    @Test
    public void testStringConstructor() {
        String message = "This is just a test.";
        FileWriterException ex = new FileWriterException(message);
        assertEquals(message, ex.getMessage());
    }

    @Test
    public void testThrowableConstructor() {
        Throwable cause = new Throwable();
        FileWriterException ex = new FileWriterException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testStringThrowableConstructor() {
        String message = "This is just a test.";
        Throwable cause = new Throwable();
        FileWriterException ex = new FileWriterException(message, cause);
        assertEquals(message, ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
