
package org.putnamfamily.fileprocessor.converter;

import java.text.Format;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:23:38 PM
 * File id: $Id: BaseConverter.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
abstract class BaseConverter implements Converter {
    // BaseConverter implements Converter so the ConverterFactory can return an Converter
    // and create all subclasses with access to the setFormatPattern which is not part
    // of the Converter interface. (on purpose)
    private static final Logger LOGGER = LogManager.getLogger();
    private String formatPattern;
    private Format format;

    public String write(Object value) {
        LOGGER.trace("BaseConverter.write");
        String output;
        if (value == null) {
            output = "";
        } else if (format == null) {
            output = value.toString();
        } else {
            output = format.format(value);
        }
        return output;
    }

    protected String getFormatPattern() {
        return formatPattern;
    }

    protected void setFormatPattern(final String formatPattern) {
        LOGGER.trace("BaseConverter.setFormatPattern");
        this.formatPattern = formatPattern;
    }

    protected Format getFormat() {
        return format;
    }

    protected void setFormat(final Format format) {
        LOGGER.trace("BaseConverter.setFormat");
        this.format = format;
    }
}
