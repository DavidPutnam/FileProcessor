
package org.putnamfamily.fileprocessor.datafile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.BeforeClass;
import org.junit.Test;
import org.putnamfamily.fileprocessor.domain.TestingClass;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 31, 2006
 * Time: 7:44:28 PM
 * File id: $Id: TestHandler.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestSaxHandler {
    private static SAXParser saxParser;

    @BeforeClass
    public static void setUp() throws Exception {
        // create the factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        try {
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("Invalid configuration parameter.", ex);
        } catch (SAXNotRecognizedException ex) {
            throw new RuntimeException("Unrecognized configuration parameter.", ex);
        } catch (SAXNotSupportedException ex) {
            throw new RuntimeException("Unsupported configuration parameter.", ex);
        }
        // create the datafile.
        saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (SAXException ex) {
            throw new RuntimeException("SAX Factory Exception.", ex);
        } catch (ParserConfigurationException ex) {
            throw new RuntimeException("DataFileParser configuration Exception.", ex);
        }
    }

    @Test
    public void testRegularFixed() {
        // create our XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            String datafilevalue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE File SYSTEM \"config/filespecification.dtd\">\n"
                    + "<File type=\"fixed\" idBegin=\"1\" idLength=\"6\">\n"
                    + "   <Record id=\"RTR001\" firstOfSet=\"true\" targetClassName=\"org.putnamfamily.fileprocessor.domain.TestingClass\">\n"
                    + "      <Attribute begin=\"1\" length=\"3\" name=\"recordType\"/>\n"
                    + "      <Attribute begin=\"4\" length=\"3\" name=\"sequenceNumber\"/>\n"
                    + "      <Attribute begin=\"7\" length=\"15\" name=\"fileType\"/>\n"
                    + "      <Attribute begin=\"22\" length=\"9\" name=\"intValue\"/>\n" + "   </Record>\n" + "</File>";
            InputSource is = new InputSource(new StringReader(datafilevalue));
            saxParser.parse(is, handler);
        } catch (SAXException ex) {
            fail(ex.getMessage());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        DataFileBase parser = handler.getParser();
        try {
            parser.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
        String line = "RTR001FINANCIALDIRECT000000027";
        TestingClass trailer = null;
        try {
            assertTrue(parser.isFirstOfSet(line));
            trailer = (TestingClass) parser.createInstance(line);
            parser.assignAttributes(trailer, line);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps  - Test all three attributes defined.
        int expectedIntValue = 27;
        assertEquals(expectedIntValue, trailer.getIntValue());
    }

    @Test
    public void testSameAs() {
        // create our XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            String datafilevalue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE File SYSTEM \"config/filespecification.dtd\">\n"
                    + "<File type=\"fixed\" idBegin=\"1\" idLength=\"6\">\n"
                    + "   <Record id=\"RTR001\" firstOfSet=\"true\" targetClassName=\"org.putnamfamily.fileprocessor.domain.TestingClass\">\n"
                    + "      <Attribute begin=\"1\" length=\"3\" name=\"recordType\"/>\n"
                    + "      <Attribute begin=\"4\" length=\"3\" name=\"sequenceNumber\"/>\n"
                    + "      <Attribute begin=\"7\" length=\"15\" name=\"fileType\"/>\n"
                    + "      <Attribute begin=\"22\" length=\"9\" name=\"intValue\"/>\n" + "   </Record>\n"
                    + "   <Record id=\"RTM001\" sameAs=\"RTR001\"/>" + "</File>";
            InputSource is = new InputSource(new StringReader(datafilevalue));
            saxParser.parse(is, handler);
        } catch (SAXException ex) {
            fail(ex.getMessage());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        DataFileBase parser = handler.getParser();
        try {
            parser.initialize();
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }
        String line = "RTM001FINANCIALDIRECT000000027";
        TestingClass trailer = null;
        try {
            trailer = (TestingClass) parser.createInstance(line);
            parser.assignAttributes(trailer, line);
        } catch (FileParserException ex) {
            fail(ex.getMessage());
        }

        // verification steps  - Test all three attributes defined.
        int expectedIntValue = 27;
        assertEquals(expectedIntValue, trailer.getIntValue());
    }

    @Test
    public void testSameAsNotExists() {
        // create our XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            String datafilevalue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE File SYSTEM \"config/filespecification.dtd\">\n"
                    + "<File type=\"fixed\" idBegin=\"1\" idLength=\"6\">\n"
                    + "   <Record id=\"RTR001\" firstOfSet=\"true\" targetClassName=\"org.putnamfamily.fileprocessor.domain.TestingClass\">\n"
                    + "      <Attribute begin=\"1\" length=\"3\" name=\"recordType\"/>\n"
                    + "      <Attribute begin=\"4\" length=\"3\" name=\"sequenceNumber\"/>\n"
                    + "      <Attribute begin=\"7\" length=\"15\" name=\"fileType\"/>\n"
                    + "      <Attribute begin=\"22\" length=\"9\" name=\"intValue\"/>\n" + "   </Record>\n"
                    + "   <Record id=\"RTM001\" sameAs=\"RTX001\"/>" + "</File>";
            InputSource is = new InputSource(new StringReader(datafilevalue));
            saxParser.parse(is, handler);
        } catch (SAXException ex) {
            fail(ex.getMessage());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        DataFileBase parser = handler.getParser();
        try {
            parser.initialize();
        } catch (FileParserException ex) {
            assertEquals("Record 'RTM001' references record 'RTX001' which does not exist.", ex.getMessage());
            return;
        }
        fail("RTM References sameAs RTX which should not exist.");
    }

    @Test
    public void testFixedBadBegin() {
        // create our XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            String datafilevalue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE File SYSTEM \"config/filespecification.dtd\">\n"
                    + "<File type=\"fixed\" idBegin=\"0\" idLength=\"6\">\n"
                    + "   <Record id=\"RTR001\" firstOfSet=\"true\" targetClassName=\"org.putnamfamily.fileprocessor.domain.TestingClass\">\n"
                    + "      <Attribute begin=\"1\" length=\"3\" name=\"recordType\"/>\n"
                    + "      <Attribute begin=\"4\" length=\"3\" name=\"sequenceNumber\"/>\n"
                    + "      <Attribute begin=\"7\" length=\"15\" name=\"fileType\"/>\n"
                    + "      <Attribute begin=\"22\" length=\"9\" name=\"intValue\"/>\n" + "   </Record>\n" + "</File>";
            InputSource is = new InputSource(new StringReader(datafilevalue));
            saxParser.parse(is, handler);
        } catch (SAXException ex) {
            fail(ex.getMessage());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        DataFileBase parser = handler.getParser();
        try {
            parser.initialize();
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("idBegin must be greater than 0."));
            return;
        }
        fail("Bad idBegin value should have been an exception.");
    }

    @Test
    public void testFixedBadLength() {
        // create our XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            String datafilevalue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                    + "<!DOCTYPE File SYSTEM \"config/filespecification.dtd\">\n"
                    + "<File type=\"fixed\" idBegin=\"1\" idLength=\"0\">\n"
                    + "   <Record id=\"RTR001\" firstOfSet=\"true\" targetClassName=\"org.putnamfamily.fileprocessor.domain.TestingClass\">\n"
                    + "      <Attribute begin=\"1\" length=\"3\" name=\"recordType\"/>\n"
                    + "      <Attribute begin=\"4\" length=\"3\" name=\"sequenceNumber\"/>\n"
                    + "      <Attribute begin=\"7\" length=\"15\" name=\"fileType\"/>\n"
                    + "      <Attribute begin=\"22\" length=\"9\" name=\"intValue\"/>\n" + "   </Record>\n" + "</File>";
            InputSource is = new InputSource(new StringReader(datafilevalue));
            saxParser.parse(is, handler);
        } catch (SAXException ex) {
            fail(ex.getMessage());
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
        DataFileBase parser = handler.getParser();
        try {
            parser.initialize();
        } catch (FileParserException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("idLength must be greater than 0."));
            return;
        }
        fail("Bad idLength value should have been an exception.");
    }
}
