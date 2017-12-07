
package org.putnamfamily.fileprocessor.nwd22c2;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 12, 2007
 * Time: 7:07:36 PM
 * File id: $Id: Transaction.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Transaction {
    //private static final Logger LOGGER = LogManager.getLogger();
    private String fundHouseName;
    private String clientLastName;
    private String clientFirstName;
    private String clientSsn;
    private String cusip;

    public Object extractLoadAccount() {
        return new Object();
    }

    public Object extractLoadTransaction() {
        return new Object();
    }

    public String getFundHouseName() {
        return fundHouseName;
    }

    public void setFundHouseName(String fundHouseName) {
        this.fundHouseName = fundHouseName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientSsn() {
        return clientSsn;
    }

    public void setClientSsn(String clientSsn) {
        this.clientSsn = clientSsn;
    }

    public String getCusip() {
        return cusip;
    }

    public void setCusip(String cusip) {
        this.cusip = cusip;
    }
}
