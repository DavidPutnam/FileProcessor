
package org.putnamfamily.fileprocessor.datafile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:25:57 PM
 * File id: $Id: FileDefinitionFactory.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public final class FileDefinitionFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public static DataFileParser create(final String fileDefinitionFileName) throws FileParserException {
        Reader reader = null;
        try {
            reader = new FileReader(fileDefinitionFileName);
            return create(new BufferedReader(reader));
        } catch (FileNotFoundException ex) {
            throw new FileParserException("Cannot open FileDefinitionFile.", ex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    //throw new FileParserException("Cannot close FileDefinitionFile.", ex);
                }
            }
        }
    }

    public static DataFileParser create(final BufferedReader fileDefinition) throws FileParserException {
        // create the SAX DataFileParser Factory
        SAXParserFactory factory = createSaxParserFactory();

        // create the SAX XML data file.
        SAXParser saxParser = createSaxParser(factory);

        // Parse the specification definition file
        DataFileBase parser = parseSpecification(fileDefinition, saxParser);

        // initialize and validate the XML read.
        parser.initialize();

        LOGGER.debug("Created.");
        return parser;
    }

    private static SAXParserFactory createSaxParserFactory() throws FileParserException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        try {
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
        } catch (ParserConfigurationException ex) {
            throw new FileParserException("Invalid configuration parameter.", ex);
        } catch (SAXNotRecognizedException ex) {
            throw new FileParserException("Unrecognized configuration parameter.", ex);
        } catch (SAXNotSupportedException ex) {
            throw new FileParserException("Unsupported configuration parameter.", ex);
        }
        return factory;
    }

    private static SAXParser createSaxParser(final SAXParserFactory factory) throws FileParserException {
        SAXParser saxParser;
        try {
            saxParser = factory.newSAXParser();
        } catch (SAXException ex) {
            throw new FileParserException("Unable to get SAXParser.", ex);
        } catch (ParserConfigurationException ex) {
            throw new FileParserException("Unable to configure SAXParser.", ex);
        }
        return saxParser;
    }

    private static DataFileBase parseSpecification(final BufferedReader fileDefinition, final SAXParser saxParser)
            throws FileParserException {
        // create our SAX XML Handler
        SaxFileSpecificationHandler handler = new SaxFileSpecificationHandler();

        // parse the input.
        try {
            InputSource inputSource = new InputSource(fileDefinition);
            saxParser.parse(inputSource, handler);
        } catch (SAXException ex) {
            throw new FileParserException("Unable to parse file definition.", ex);
        } catch (IOException ex) {
            throw new FileParserException("Unable to read file definition.", ex);
        }
        return handler.getParser();
    }
}
