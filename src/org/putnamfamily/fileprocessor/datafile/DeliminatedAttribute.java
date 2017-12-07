
package org.putnamfamily.fileprocessor.datafile;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 10, 2006
 * Time: 7:27:44 PM
 * File id: $Id: DeliminatedAttribute.java 15 2008-10-02 00:36:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 15 $
 */
final class DeliminatedAttribute extends Attribute {
    private static final Logger LOGGER = LogManager.getLogger();
    private char delim = '|';
    private int field = 0;
    private char subDelim = ',';

    DeliminatedAttribute(String name, String format, char delim, int field, char subDelim) {
        super(name, format);
        this.delim = delim;
        this.field = field;
        this.subDelim = subDelim;
    }

    protected void validateBasicArgs() {
        // verify the basic attributes.
        if (getField() <= 0) {
            String message = "Field is not valid. '" + getField() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    protected int validateArrayArgs() {
        // if we are working with an array validate the array specific attributes.
        if (!Character.isLetterOrDigit(getSubDelim())) {
            String message = "Array element deliminator not valid. '" + getSubDelim() + "'.";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        // TODO - How do we determine how many elements given just the deliminator?
        // this is important because it determines the index values on the call to convertField.
        // By returning 0 we will cause a FileParserException to be thrown by the caller.
        return 0;
    }

    protected String convertField(final String wholeLine) {
        // this method is to return the string that is the index'th field on the wholeLine.
        // this may actually be better implemented by having the record break the line into
        // an array of fields once, then each field would would return its element from that
        // list.
        // TODO implementation of this may require refactoring of the Attribute entity.
        List<String> fields = DelimitedDataParser.getFieldsFromLine(delim, '"', '\\', wholeLine);
        return fields.get(field - 1);
    }

    protected String convertField(final String wholeLine, final int index) {
        String rawField = convertField(wholeLine);
        // now split rawField into subFields based on subDelim and get the index'th part
        List<String> subFields = DelimitedDataParser.getFieldsFromLine(subDelim, '"', '\\', rawField);
        return subFields.get(index);
    }

    protected String writeAttribute(String fieldValue) {
        LOGGER.trace("DeliminatedAttribute.writeAttribute");
        return fieldValue + delim;
    }

    public int compareTo(final Attribute object) {
        int retvalue;
        DeliminatedAttribute compareTo = (DeliminatedAttribute) object;
        if (getField() < compareTo.getField()) {
            retvalue = -1;
        } else if (getField() > compareTo.getField()) {
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

        DeliminatedAttribute that = (DeliminatedAttribute) o;

        if (field != that.field) {
            return false;
        }
        if (subDelim != that.subDelim) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) subDelim;
        result = 31 * result + field;
        return result;
    }

    protected int validateCoverage(final String recordId, final int currentPosition) {
        int nextPosition = getField();
        if (currentPosition < nextPosition) {
            LOGGER.warn("Gap found on record " + recordId + " between fields " + (currentPosition - 1) + " and "
                    + nextPosition + ".");
        } else if (currentPosition > nextPosition) {
            LOGGER.warn("Overlap found on record " + recordId + " between fields " + (currentPosition - 1) + " and "
                    + nextPosition + ".");
        }
        return nextPosition + 1;
    }

    /* accessors below here */

    public char getSubDelim() {
        return subDelim;
    }

    public int getField() {
        return field;
    }
}
