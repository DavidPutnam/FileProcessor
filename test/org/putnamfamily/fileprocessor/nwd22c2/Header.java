
package org.putnamfamily.fileprocessor.nwd22c2;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 12, 2007
 * Time: 7:02:44 PM
 * File id: $Id: Header.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Header {
    //private static final Logger LOGGER = LogManager.getLogger();
    private Date cycleDate;
    private Date createDate;
    private String company;

    public Date getCycleDate() {
        return cycleDate;
    }

    public void setCycleDate(Date cycleDate) {
        this.cycleDate = cycleDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
