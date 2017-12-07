
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 2, 2006
 * Time: 7:18:04 PM
 * File id: $Id: TestDeliminatedParser.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestDeliminatedParser {

    @Test
    public void testBasic() {
        char delim = '|';
        char escape = '?';
        char quote = '"';
        String line = "id|field 1|field2";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(3);
        expected.add(0, "id");
        expected.add(1, "field 1");
        expected.add(2, "field2");
        assertEquals(expected, received);
    }

    @Test
    public void testTrailingDelim() {
        char delim = '|';
        char escape = '?';
        char quote = '"';
        String line = "id|field 1|field2|";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(4);
        expected.add(0, "id");
        expected.add(1, "field 1");
        expected.add(2, "field2");
        expected.add(3, "");
        assertEquals(expected, received);
    }

    @Test
    public void testQuotedDelim() {
        char delim = '|';
        char escape = '?';
        char quote = '.';
        String line = "id|.field 1|field2.";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(2);
        expected.add(0, "id");
        expected.add(1, "field 1|field2");
        assertEquals(expected, received);
    }

    @Test
    public void testNullField() {
        char delim = ',';
        char escape = '?';
        char quote = '"';
        String line = "id,,field2";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(3);
        expected.add(0, "id");
        expected.add(1, "");
        expected.add(2, "field2");
        assertEquals(expected, received);
    }

    @Test
    public void testCharsTrailingQuote() {
        char delim = '|';
        char escape = '?';
        char quote = '"';
        String line = "\"field2\"xyz";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "field2");
        assertEquals(expected, received);
    }

    @Test
    public void testFieldAfterQuote() {
        char delim = '|';
        char escape = '?';
        char quote = '"';
        String line = "\"field2\"xyz|field 3";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "field2");
        expected.add(1, "field 3");
        assertEquals(expected, received);
    }

    @Test
    public void testNoQuoteEscDelim() {
        char delim = '|';
        char escape = '?';
        char quote = '.';
        String line = "id|field 1?|field2";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(3);
        expected.add(0, "id");
        expected.add(1, "field 1?");
        expected.add(2, "field2");
        assertEquals(expected, received);
    }

    @Test
    public void testEscNewline() {
        char delim = '|';
        char escape = '?';
        char quote = '.';
        String line = ".field 1?nfield2.";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "field 1\nfield2");
        assertEquals(expected, received);
    }

    @Test
    public void testEscQuote() {
        char delim = '|';
        char escape = 'P';
        char quote = '.';
        String line = ".field 1P.field2.";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "field 1.field2");
        assertEquals(expected, received);
    }

    @Test
    public void testNonEscQuote() {
        char delim = '|';
        char escape = 'P';
        char quote = '.';
        String line = "field 1.field2";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "field 1.field2");
        assertEquals(expected, received);
    }

    @Test
    public void testNullLine() {
        char delim = '|';
        char escape = '}';
        char quote = '.';
        String line = null;
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(0);
        assertEquals(expected, received);
    }

    @Test
    public void testZeroLengthLine() {
        char delim = '|';
        char escape = '$';
        char quote = '.';
        String line = "";
        List<String> received = DelimitedDataParser.getFieldsFromLine(delim, quote, escape, line);
        List<String> expected = new ArrayList<String>(1);
        expected.add(0, "");
        assertEquals(expected, received);
    }
}
