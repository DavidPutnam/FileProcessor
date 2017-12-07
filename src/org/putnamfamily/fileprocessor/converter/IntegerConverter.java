
package org.putnamfamily.fileprocessor.converter;

import java.text.Format;
import java.text.ParsePosition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Jan 23, 2008
 * Time: 2:40:33 PM
 * File id: $Id$
 *
 * @author $Author$
 * @version $Revision$
 */
abstract class IntegerConverter extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    protected abstract Object readImpl(Object value);

    protected abstract Format parseFormatImpl(String formatPattern);

    public Object read(String field) throws ConverterException {
        LOGGER.trace("IntegerConverter.read");
        if (field == null || "".equals(field.trim())) {
            return null;
        } else {
            ParsePosition pos = new ParsePosition(0);
            Object value = getFormat().parseObject(field.trim(), pos);
            if (pos.getIndex() < field.trim().length()) {
                // there are characters in the field after what was parsed
                throw new ConverterException(
                    "Can not parse field. '" + field + "' with format '" + getFormatPattern() + "'");
            }
            return readImpl(value);
        }
    }

    protected void setFormatPattern(String formatPattern) {
        LOGGER.trace("IntegerConverter.setFormatPattern");
        super.setFormatPattern(formatPattern);

        if (formatPattern == null || "".equals(formatPattern.trim())) {
            setFormat(parseFormatImpl("0"));
        } else {
            setFormat(parseFormatImpl(formatPattern.trim()));
        }
    }
}
