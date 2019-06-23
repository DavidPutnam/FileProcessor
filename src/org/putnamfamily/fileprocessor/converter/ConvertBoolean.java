
package org.putnamfamily.fileprocessor.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 4, 2006
 * Time: 7:13:14 PM
 * File id: $Id: ConvertBoolean.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertBoolean extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public Boolean read(final String field) throws ConverterException {
        LOGGER.trace("ConvertBoolean.read");
        if (field == null || "".equals(field.trim())) {
            // returning null could cause NPE if auto unboxing to boolean
            return null;
        } else {
            return Boolean.valueOf(field);
        }
    }
}
