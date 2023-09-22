
package org.putnamfamily.fileprocessor.datafile;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 12, 2007
 * Time: 7:18:07 PM
 * File id: $Id: DelimitedDataFile.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
final class DelimitedDataFile extends DataFileBase {
    private static final Logger LOGGER = LogManager.getLogger();
    char delimitor;
    int keyField;

    DelimitedDataFile(char delimitor, int keyField) {
        this.delimitor = delimitor;
        this.keyField = keyField;
    }

    public String getRecordId(String wholeLine) throws FileParserException {
        List<String> fields = DelimitedDataParser.getFieldsFromLine(delimitor, '"', '\\', wholeLine);
        return fields.get(keyField);
    }

    protected void validateArgs() throws FileParserException {
        LOGGER.trace("DelimitedDataFile.validateArgs");
        // validate delimitor
        if (getKeyField() <= 0) {
            String message = "keyField must be greater than 0. '" + getKeyField() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        //        for (String id : getRecords().keySet()) {
        //            if (id.length() != getIdLength()) {
        //                log.warn("record id '" + id + "' is not " + getIdLength() + " characters long.");
        //            }
        //        }
    }

    public char getDelimitor() {
        return delimitor;
    }

    public void setDelimitor(char delimitor) {
        this.delimitor = delimitor;
    }

    public int getKeyField() {
        return keyField;
    }

    public void setKeyField(int keyField) {
        this.keyField = keyField;
    }
    
}
