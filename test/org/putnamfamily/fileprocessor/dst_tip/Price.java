
package org.putnamfamily.fileprocessor.dst_tip;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:47:33 PM
 * File id: $Id: Account.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Price {
    // records PRI
    //private static final Logger LOGGER = LogManager.getLogger();
    private String recordType;
    private String sequenceNumber;

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
