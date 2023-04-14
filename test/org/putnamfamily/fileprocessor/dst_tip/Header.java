
package org.putnamfamily.fileprocessor.dst_tip;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Jan 15, 2007
 * Time: 7:52:37 PM
 * File id: $Id: Header.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Header extends DstTipBaseRecord {
    //rrrssstttttttttttttttssssssssppppppppppppppppjjjjjjjjfffqqqqqqq
    //RHR001ACCT ACTIVITY  200601102006011022153866UBDMU1380130000003
    //private static final Logger LOGGER = LogManager.getLogger();
    private String fileType;
    private Date superSheetDate;
    private Date processedDate;
    private String jobName;
    private String fileFormatCode;
    private String requestNumber;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Date getSuperSheetDate() {
        return (Date) superSheetDate.clone();
    }

    public void setSuperSheetDate(Date superSheetDate) {
        this.superSheetDate = (Date) superSheetDate.clone();
    }

    public Date getProcessedDate() {
        return (Date) processedDate.clone();
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = (Date) processedDate.clone();
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getFileFormatCode() {
        return fileFormatCode;
    }

    public void setFileFormatCode(String fileFormatCode) {
        this.fileFormatCode = fileFormatCode;
    }

    public String getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(String requestNumber) {
        this.requestNumber = requestNumber;
    }
}
