
package org.putnamfamily.fileprocessor.datafile;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:12:49 PM
 * File id: $Id: FixedFieldAttribute.java 15 2008-10-02 00:36:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 15 $
 */
final class FixedFieldAttribute extends Attribute {
    private static final Logger LOGGER = LogManager.getLogger();
    // from the attribute definition
    private final int begin;
    private final int length;
    private final int subLength;

    protected FixedFieldAttribute(String name, String format, int begin, int length, int subLength) {
        super(name, format);
        this.begin = begin;
        this.length = length;
        this.subLength = subLength;
    }

    static String convertField(final String wholeLine, final int begin, final int length) {
        if (wholeLine == null) {
            return null;
        }
        // if we are accessing past the end of the line, assume the spaces were removed.
        int beginIndex = begin - 1;
        if (beginIndex > wholeLine.length()) {
            return "";
        }
        int endIndex = beginIndex + length;
        if (endIndex > wholeLine.length()) {
            endIndex = wholeLine.length();
        }
        return wholeLine.substring(beginIndex, endIndex);
    }

    protected String convertField(final String wholeLine) {
        return convertField(wholeLine, getBegin(), getLength());
    }

    protected String convertField(final String wholeLine, final int index) {
        int subBegin = getBegin() + (getSubLength() * index);
        return convertField(wholeLine, subBegin, getSubLength());
    }

    protected void validateBasicArgs() {
        // verify the basic attributes.
        if (getBegin() <= 0 || getLength() <= 0) {
            String message = "Begin or length is not valid. Argument values: ";
            message = message + "begin: '" + getBegin() + "', ";
            message = message + "length: '" + getLength() + "'.";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    protected int validateArrayArgs() {
        if (getSubLength() <= 0) {
            String message = "Array element length not valid. Argument values: ";
            message = message + "sublength: " + getSubLength() + ".";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        // check that length is a whole multiple of subLength
        if (getLength() % getSubLength() != 0) {
            String message = "Field length not multiple of array element length. Argument values: ";
            message = message + "length: " + getLength() + ", ";
            message = message + "sublength: " + getSubLength() + ".";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        // return the number of expected elements in the array for this field.
        return (getLength() / getSubLength());
    }

    protected int validateCoverage(final String recordId, final int currentPosition) {
        int nextPosition = getBegin();
        if (nextPosition < currentPosition) {
            LOGGER.warn("Overlap found on record " + recordId + " between positions " + nextPosition + " and "
                    + currentPosition + ".");
        } else if (nextPosition > currentPosition) {
            LOGGER.warn("Gap found on record " + recordId + " between positions " + currentPosition + " and "
                    + nextPosition + ".");
        }
        return nextPosition + getLength();
    }

    protected String writeAttribute(String fieldValue) throws FileParserException {
        if (fieldValue == null) {
            throw new FileParserException("Unable to write null value.");
        }
        if (fieldValue.length() > getLength()) {
            throw new FileParserException("Field value does not fit in fixed field.");
        } else if (fieldValue.length() < getLength()) {
            fieldValue = StringUtils.leftPad(fieldValue, getLength(), ' ');
        }
        return fieldValue;
    }

    public int compareTo(final Attribute object) {
        int retvalue;
        FixedFieldAttribute compareTo = (FixedFieldAttribute) object;
        if (getBegin() < compareTo.getBegin()) {
            retvalue = -1;
        } else if (getBegin() > compareTo.getBegin()) {
            retvalue = 1;
        } else {
            retvalue = getName().compareTo(compareTo.getName());
        }
        return retvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixedFieldAttribute that = (FixedFieldAttribute) o;

        if (begin != that.begin) {
            return false;
        }
        if (length != that.length) {
            return false;
        }
        if (subLength != that.subLength) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = begin;
        result = 31 * result + length;
        result = 31 * result + subLength;
        return result;
    }

    // getters and setters below this
    public int getBegin() {
        return begin;
    }

    public int getLength() {
        return length;
    }

    public int getSubLength() {
        return subLength;
    }
}
