
package org.putnamfamily.fileprocessor.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:43:57 PM
 * File id: $Id: ConvertDate.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertDate extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public Date read(final String field) throws ConverterException {
        LOGGER.trace("ConvertDate.read");
        // blank or zero filled field is "no date"
        if (field == null || StringUtils.isEmpty(StringUtils.strip(field, " 0"))) {
            return null;
        } else {
            try {
                return (Date) getFormat().parseObject(field);
            } catch (ParseException ex) {
                String message = "Date data does not match format. Format: '" + getFormatPattern() + "', Data: '"
                        + field + "'.";
                LOGGER.error(message, ex);
                throw new ConverterException(message, ex);
            }
        }
    }

    protected void setFormatPattern(final String formatPattern) {
        LOGGER.trace("ConvertDate.setFormatPattern");
        super.setFormatPattern(formatPattern);
        try {
            setFormat(new SimpleDateFormat(formatPattern));
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid format for Date Converter.", ex);
        }
    }
}
