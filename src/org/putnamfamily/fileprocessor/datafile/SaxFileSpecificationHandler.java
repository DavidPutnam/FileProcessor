
package org.putnamfamily.fileprocessor.datafile;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 31, 2006
 * Time: 7:51:34 PM
 * File id: $Id: SaxFileSpecificationHandler.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
final class SaxFileSpecificationHandler extends DefaultHandler {
    // strings that are part of the XML definition.
    private static final String FILE = "File";
    private static final String TYPE = "type";
    private static final String FIXED = "fixed";
    private static final String DELIMINATED = "deliminated";
    private static final String IDBEGIN = "idBegin";
    private static final String IDLENGTH = "idLength";
    private static final String IDFIELD = "idField";
    private static final String DELIMINATOR = "deliminator";
    private static final String RECORD = "Record";
    private static final String ID = "id";
    private static final String FIRSTOFSET = "firstOfSet";
    private static final String REQUIRED = "required";
    private static final String TARGETCLASSNAME = "targetClassName";
    private static final String SAMEAS = "sameAs";
    private static final String ATTRIBUTE = "Attribute";
    private static final String NAME = "name";
    private static final String FORMAT = "format";
    private static final String BEGIN = "begin";
    private static final String LENGTH = "length";
    private static final String SUBLENGTH = "sublength";
    private static final String FIELD = "field";
    private static final String SUBDELIMINATOR = "subdelim";

    // major modes of operation.
    private static final int FIXED_TYPE = 1;
    private static final int DELIM_TYPE = 2;

    private DataFileBase datafileParser;
    private DataFileRecord currentRecord;
    private int parserType;
    private char deliminator;

    /**
     * Receive notification of the start of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the start of
     * each element (such as allocating a new tree node or writing
     * output to a file).</p>
     *
     * @param uri   The Namespace URI, or the empty string if the
     *              element has no Namespace URI or if Namespace
     *              processing is not being performed.
     * @param lName The local name (without prefix), or the
     *              empty string if Namespace processing is not being
     *              performed.
     * @param qName The qualified name (with prefix), or the
     *              empty string if qualified names are not available.
     * @param attrs The attributes attached to the element.  If
     *              there are no attributes, it shall be an empty
     *              Attributes object.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#startElement
     */
    public void startElement(final String uri, final String lName, final String qName, final Attributes attrs) throws SAXException {
        super.startElement(uri, lName, qName, attrs);
        String eName = ("".equals(lName) ? qName : lName);

        if (FILE.equalsIgnoreCase(eName)) {
            // this is the file element
            // get the required type element to determine fixed or deliminated
            String type = attrs.getValue(TYPE);
            if (FIXED.equalsIgnoreCase(type)) {
                // fixed field file, get attributes required for this type.
                parserType = FIXED_TYPE;
                createFixedParser(attrs);
            } else if (DELIMINATED.equalsIgnoreCase(type)) {
                // deliminated file, get attributes required for this type.
                parserType = DELIM_TYPE;
                createDeliminatedParser(attrs);
            } else {
                throw new SAXException("Invalid data in File element. Type '" + type + "' is not supported.");
            }
        } else if (RECORD.equalsIgnoreCase(eName)) {
            currentRecord = createRecord(attrs);
            datafileParser.addRecord(currentRecord.getId(), currentRecord);
        } else if (ATTRIBUTE.equalsIgnoreCase(eName)) {
            if (currentRecord == null) {
                throw new SAXException("Invalid sequence of elements, " + ATTRIBUTE + " must follow " + RECORD + ".");
            } else if (currentRecord.getSameAs() != null) {
                throw new SAXException(RECORD + "s identified with sameAs cannot have " + ATTRIBUTE + "s.");
            } else {
                switch (parserType) {
                    case FIXED_TYPE:
                        currentRecord.addAttribute(createFixedAttribute(attrs));
                        break;
                    case DELIM_TYPE:
                        currentRecord.addAttribute(createDeliminatedAttribute(attrs));
                        break;
                }
            }
        }
    }

    /**
     * Receive notification of the end of an element.
     * <p/>
     * <p>By default, do nothing.  Application writers may override this
     * method in a subclass to take specific actions at the end of
     * each element (such as finalising a tree node or writing
     * output to a file).</p>
     *
     * @param uri   The Namespace URI, or the empty string if the
     *              element has no Namespace URI or if Namespace
     *              processing is not being performed.
     * @param lName The local name (without prefix), or the
     *              empty string if Namespace processing is not being
     *              performed.
     * @param qName The qualified name (with prefix), or the
     *              empty string if qualified names are not available.
     * @throws org.xml.sax.SAXException Any SAX exception, possibly
     *                                  wrapping another exception.
     * @see org.xml.sax.ContentHandler#endElement
     */
    @Override
    public void endElement(String uri, String lName, String qName) throws SAXException {
        super.endElement(uri, lName, qName); //To change body of overridden methods use File | Settings | File Templates.
        String eName = ("".equals(lName) ? qName : lName);
        /*
        if (FILE.equalsIgnoreCase(eName)) {
            // this is the end file element
        } else if (RECORD.equalsIgnoreCase(eName)) {
            // this is the end record element
            currentRecord = null;
        } else if (ATTRIBUTE.equalsIgnoreCase(eName)) {
            // this is the end attribute element
        }*/
        if (RECORD.equalsIgnoreCase(eName)) {
            // this is the end record element
            currentRecord = null;
        }
    }

    private DataFileRecord createRecord(final Attributes attrs) {
        // this is the record element.
        String id = attrs.getValue(ID);
        boolean firstOfSet = Boolean.parseBoolean(attrs.getValue(FIRSTOFSET));
        boolean required = Boolean.parseBoolean(attrs.getValue(REQUIRED));
        String targetClassName = attrs.getValue(TARGETCLASSNAME);
        String sameAs = attrs.getValue(SAMEAS);
        if (sameAs != null) {
            return new DataFileRecord(id, sameAs);
        } else {
            return new DataFileRecord(id, targetClassName, firstOfSet, required);
        }
    }

    private void createDeliminatedParser(final Attributes attrs) throws SAXException {
        int idField;
        try {
            idField = Integer.parseInt(attrs.getValue(IDFIELD));
            deliminator = attrs.getValue(DELIMINATOR).charAt(0);
        } catch (NumberFormatException ex) {
            throw new SAXException("Invalid data in File element.", ex);
        }
        // todo - create delim datafile
        //datafileParser = new DelimitedDataFile(idField, deliminator);
        throw new SAXException(
            "Unsupported Deliminated DataFileParser with idField: '" + idField + "' and deliminator: '" + deliminator + "'");
    }

    private void createFixedParser(final Attributes attrs) throws SAXException {
        int idBegin, idLength;
        try {
            idBegin = Integer.parseInt(attrs.getValue(IDBEGIN));
            idLength = Integer.parseInt(attrs.getValue(IDLENGTH));
        } catch (NumberFormatException ex) {
            throw new SAXException("Invalid data in File element.", ex);
        }
        datafileParser = new FixedFieldDataFile(idBegin, idLength);
    }

    private Attribute createDeliminatedAttribute(final Attributes attrs) {
        String name = attrs.getValue(NAME);
        String format = attrs.getValue(FORMAT);
        String fieldStr = attrs.getValue(FIELD);
        String subdelimStr = attrs.getValue(SUBDELIMINATOR);
        int field = 0;
        if (fieldStr != null) {
            field = Integer.parseInt(fieldStr);
        }
        char subdelim = ',';
        if (subdelimStr != null && subdelimStr.trim().length() > 0) {
            subdelim = subdelimStr.trim().charAt(0);
        }
        return new DeliminatedAttribute(name, format, deliminator, field, subdelim);
    }

    private Attribute createFixedAttribute(final Attributes attrs) {
        String name = attrs.getValue(NAME);
        String format = attrs.getValue(FORMAT);
        String beginStr = attrs.getValue(BEGIN);
        String lengthStr = attrs.getValue(LENGTH);
        String sublenStr = attrs.getValue(SUBLENGTH);
        int begin = 0;
        int length = 0;
        int sublength = 0;
        if (beginStr != null) {
            begin = Integer.parseInt(beginStr);
        }
        if (lengthStr != null) {
            length = Integer.parseInt(lengthStr);
        }
        if (sublenStr != null) {
            sublength = Integer.parseInt(sublenStr);
        }
        return new FixedFieldAttribute(name, format, begin, length, sublength);
    }

    public DataFileBase getParser() {
        return datafileParser;
    }
}
