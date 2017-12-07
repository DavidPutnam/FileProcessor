
package org.putnamfamily.fileprocessor.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:43:57 PM
 * File id: $Id: ConvertBigDecimal.java 7 2007-12-20 02:57:28Z david $
 *
 * @author $Author: david $
 * @version $Revision: 7 $
 */
final class ConvertBigDecimal extends DecimalConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public BigDecimal readImpl(Object value) {
        LOGGER.trace("ConvertBigDecimal.readImpl");
        BigDecimal returnValue = null;
        if (value instanceof BigDecimal) {
            returnValue = (BigDecimal) value;
        }
        return returnValue;
    }

    protected Format parseFormatImpl(String formatPattern) {
        LOGGER.trace("ConvertBigDecimal.parseFormatImpl");
        DecimalFormat decimalFormat = new DecimalFormat(formatPattern);
        decimalFormat.setParseBigDecimal(true);
        decimalFormat.setParseIntegerOnly(false);
        return decimalFormat;
    }
}
