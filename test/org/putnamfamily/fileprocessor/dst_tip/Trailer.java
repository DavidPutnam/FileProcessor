
package org.putnamfamily.fileprocessor.dst_tip;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:47:33 PM
 * File id: $Id: Trailer.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Trailer {
    //rrrssstttttttttttttttccccccccc
    //RTR001ACCT ACTIVITY  000000002
    //private static final Logger LOGGER = LogManager.getLogger();
    private String recordType;
    private String sequenceNumber;
    private String fileType;
    private int recordCount;

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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }
}
