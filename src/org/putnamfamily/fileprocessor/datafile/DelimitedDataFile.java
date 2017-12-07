
package org.putnamfamily.fileprocessor.datafile;

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

    protected void validateArgs() throws FileParserException {
        LOGGER.trace("DelimitedDataFile.validateArgs");
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isFirstOfSet(String line) throws FileParserException {
        return false; //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRecordId(String line) throws FileParserException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object createInstance(String line) throws FileParserException {
        return null; //To change body of implemented methods use File | Settings | File Templates.
    }

    public void assignAttributes(Object target, String line) throws FileParserException {
        LOGGER.trace("DelimitedDataFile.assignAttributes");
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
