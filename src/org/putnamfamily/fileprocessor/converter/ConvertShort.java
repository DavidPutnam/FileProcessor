
package org.putnamfamily.fileprocessor.converter;

import java.text.DecimalFormat;
import java.text.Format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 28, 2006
 * Time: 7:07:57 PM
 * File id: $Id: ConvertShort.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertShort extends IntegerConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public Short readImpl(Object value) {
        LOGGER.trace("ConvertShort.readImpl");
        Short returnValue = null;
        if (value instanceof Long) {
            returnValue = ((Long) value).shortValue();
        }
        return returnValue;
    }

    protected Format parseFormatImpl(String formatPattern) {
        LOGGER.trace("ConvertShort.parseFormatImpl");
        DecimalFormat decimalFormat = new DecimalFormat(formatPattern);
        decimalFormat.setParseBigDecimal(false);
        decimalFormat.setParseIntegerOnly(true);
        return decimalFormat;
    }
}
