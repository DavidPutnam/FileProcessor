
package org.putnamfamily.fileprocessor.converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.Format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:33:50 PM
 * File id: $Id: ConvertBigInteger.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertBigInteger extends IntegerConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public BigInteger readImpl(Object value) {
        LOGGER.trace("ConvertBigInteger.readImpl");
        BigInteger returnValue = null;
        if (value instanceof BigDecimal) {
            returnValue = ((BigDecimal) value).toBigInteger();
        }
        return returnValue;
    }

    protected Format parseFormatImpl(String formatPattern) {
        LOGGER.trace("ConvertBigInteger.parseFormatImpl");
        DecimalFormat decimalFormat = new DecimalFormat(formatPattern);
        decimalFormat.setParseBigDecimal(true);
        decimalFormat.setParseIntegerOnly(true);
        return decimalFormat;
    }
}
