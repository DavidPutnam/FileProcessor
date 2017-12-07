
package org.putnamfamily.fileprocessor.datafile;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 19, 2006
 * Time: 7:54:54 PM
 * File id: $Id: DataFileBase.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
abstract class DataFileBase implements DataFileParser {
    private static final Logger LOGGER = LogManager.getLogger();
    private Map<String, DataFileRecord> records = new HashMap<String, DataFileRecord>();

    protected abstract void validateArgs() throws FileParserException;

    public final void initialize() throws FileParserException {
        // make sure the subclasses think they are OK.
        validateArgs();

        // propagate the validation through the object graph
        Map<String, DataFileRecord> validated = new HashMap<String, DataFileRecord>();
        Map<String, DataFileRecord> original = records;
        // validate all records with attributes. (not sameAs)
        for (DataFileRecord record : original.values()) {
            if (record.getSameAs() == null) {
                record.initialize();
                validated.put(record.getId(), record);
            }
        }

        // for all of the sameAs records make sure the referenced record has been validated.
        for (DataFileRecord record : original.values()) {
            if (record.getSameAs() != null) {
                DataFileRecord referenced = validated.get(record.getSameAs());
                if (referenced == null) {
                    throw new FileParserException("DataFileRecord '" + record.getId() + "' references record '"
                            + record.getSameAs() + "' which does not exist.");
                } else {
                    validated.put(record.getId(), referenced);
                }
            }
        }

        // replace the record set with the validated one.
        records = validated;
        LOGGER.debug("Datafile Initialized.");
    }

    protected final void addRecord(String id, DataFileRecord record) {
        this.records.put(id, record);
    }

    protected final DataFileRecord getRecord(String id) {
        return records.get(id);
    }
}
