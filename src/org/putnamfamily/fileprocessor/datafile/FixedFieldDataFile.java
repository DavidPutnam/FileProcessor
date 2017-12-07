
package org.putnamfamily.fileprocessor.datafile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:27:33 PM
 * File id: $Id: FixedFieldDataFile.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
final class FixedFieldDataFile extends DataFileBase {
    private static final Logger LOGGER = LogManager.getLogger();
    private int idBegin;
    private int idLength;

    public FixedFieldDataFile(int idBegin, int idLength) {
        this.idBegin = idBegin;
        this.idLength = idLength;
    }

    private DataFileRecord findRecord(String wholeLine) throws FileParserException {
        // get the line name and find it in the map.
        String recordId = FixedFieldAttribute.convertField(wholeLine, idBegin, idLength);
        DataFileRecord record = getRecord(recordId);
        if (record != null && record.getSameAs() != null) {
            recordId = record.getSameAs();
            record = getRecord(recordId);
        }

        if (record == null) {
            throw new FileParserException("Unknown record id. " + recordId);
        }
        return record;
    }

    public void assignAttributes(Object target, String wholeLine) throws FileParserException {
        LOGGER.trace("FixedFieldDataFile.assignAttributes");
        DataFileRecord record = findRecord(wholeLine);
        record.assignValues(target, wholeLine);
    }

    public Object createInstance(String wholeLine) throws FileParserException {
        DataFileRecord record = findRecord(wholeLine);
        return record.createInstance();
    }

    public boolean isFirstOfSet(String wholeLine) throws FileParserException {
        DataFileRecord record = findRecord(wholeLine);
        return record.isFirstOfSet();
    }

    public String getRecordId(String wholeLine) throws FileParserException {
        DataFileRecord record = findRecord(wholeLine);
        return record.getId();
    }

    protected void validateArgs() throws FileParserException {
        LOGGER.trace("FixedFieldDataFile.validateArgs");
        // make sure the arguments are valid.
        if (getIdBegin() <= 0) {
            String message = "idBegin must be greater than 0. '" + getIdBegin() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        if (getIdLength() <= 0) {
            String message = "idLength must be greater than 0. '" + getIdLength() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        //        for (String id : getRecords().keySet()) {
        //            if (id.length() != getIdLength()) {
        //                log.warn("record id '" + id + "' is not " + getIdLength() + " characters long.");
        //            }
        //        }
    }

    public int getIdBegin() {
        return idBegin;
    }

    public void setIdBegin(int idBegin) {
        this.idBegin = idBegin;
    }

    public int getIdLength() {
        return idLength;
    }

    public void setIdLength(int idLength) {
        this.idLength = idLength;
    }
}
