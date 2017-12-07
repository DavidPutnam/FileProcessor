
package org.putnamfamily.fileprocessor.nwd22c2;

import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 12, 2007
 * Time: 7:03:34 PM
 * File id: $Id: Trailer.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class Trailer {
    //private static final Logger LOGGER = LogManager.getLogger();
    private int recordCount;
    private BigDecimal amountTotal;

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }
}
