
package org.putnamfamily.fileprocessor.converter;

import java.text.Format;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Jan 9, 2008
 * Time: 7:40:33 PM
 * File id: $Id$
 *
 * @author $Author$
 * @version $Revision$
 */
abstract class DecimalConverter extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();
    private int scale;
    private boolean virtual;

    protected abstract Object readImpl(Object value);

    protected abstract Format parseFormatImpl(String formatPattern);

    public Object read(String field) throws ConverterException {
        LOGGER.trace("DecimalConverter.read");
        if (field == null || "".equals(field.trim())) {
            return null;
        } else {
            try {
                if (virtual) {
                    // insert a decimal point at the scaled position
                    int decimalPoint = field.trim().length() - scale;
                    field = field.substring(0, decimalPoint) + "." + field.substring(decimalPoint);
                }
                Object value = getFormat().parseObject(field);
                return readImpl(value);
            } catch (ParseException ex) {
                throw new ConverterException(
                    "Can not parse field. '" + field + "' with format '" + getFormatPattern() + "'", ex);
            }
        }
    }

    public String write(Object field) {
        LOGGER.trace("DecimalConverter.write");
        String output;
        if (field == null) {
            output = "";
        } else {
            output = getFormat().format(field);
            if (virtual) {
                output = StringUtils.remove(output, '.');
            }
        }
        return output;
    }

    protected void setFormatPattern(String formatPattern) {
        LOGGER.trace("DecimalConverter.setFormatPattern");
        super.setFormatPattern(formatPattern);
        if (formatPattern == null || "".equals(formatPattern.trim())) {
            String message = "Formats are required for Decimals. Format: '" + formatPattern + "'.";
            throw new IllegalArgumentException(message);
        } else {
            if (formatPattern.indexOf('V') > -1) {
                // if this format specifies a virtual decimal point
                // (not part of a valid format string)
                // remove it but remember where it was.
                scale = formatPattern.length() - formatPattern.indexOf('V') - 1;
                virtual = true;
                // replace the V with a decimal point
                int decimalPoint = formatPattern.length() - scale - 1;
                formatPattern = formatPattern.substring(0, decimalPoint) + "."
                        + formatPattern.substring(decimalPoint + 1);
            }
            setFormat(parseFormatImpl(formatPattern.trim()));
        }
    }
}
