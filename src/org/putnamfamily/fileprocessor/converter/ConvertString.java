
package org.putnamfamily.fileprocessor.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:43:57 PM
 * File id: $Id: ConvertString.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertString extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public String read(final String field) throws ConverterException {
        LOGGER.trace("ConvertString.read");
        if (field == null || "".equals(field.trim())) {
            return null;
        } else {
            return field.trim();
        }
    }
}
