
package org.putnamfamily.fileprocessor.dtcc_sdr;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 15, 2007
 * Time: 7:59:29 PM
 * File id: $Id: Request.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Request {
    public static final String ORIGINATOR_TYPE = "F";
    public static final String RECORD_TYPE = "20";
    public static final String RECORD_01 = "01";

    //private static final Logger LOGGER = LogManager.getLogger();

    public String sdrControlNumber;
    public String fundNumber;
    public String securityIssueId;
    public String underlyingFirmNumber;
    public String underlyingFirmIndicator;
}
