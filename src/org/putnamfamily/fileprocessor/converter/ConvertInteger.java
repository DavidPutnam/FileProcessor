
package org.putnamfamily.fileprocessor.converter;

import java.text.DecimalFormat;
import java.text.Format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:33:50 PM
 * File id: $Id: ConvertInteger.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertInteger extends IntegerConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public Integer readImpl(Object value) {
        LOGGER.trace("ConvertInteger.readImpl");
        Integer returnValue = null;
        if (value instanceof Long) {
            returnValue = ((Long) value).intValue();
        }
        return returnValue;
    }

    protected Format parseFormatImpl(String formatPattern) {
        LOGGER.trace("ConvertInteger.parseFormatImpl");
        DecimalFormat decimalFormat = new DecimalFormat(formatPattern);
        decimalFormat.setParseBigDecimal(false);
        decimalFormat.setParseIntegerOnly(true);
        return decimalFormat;
    }
}
