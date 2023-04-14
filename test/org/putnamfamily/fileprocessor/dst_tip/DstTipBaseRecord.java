
package org.putnamfamily.fileprocessor.dst_tip;

public class DstTipBaseRecord {

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
