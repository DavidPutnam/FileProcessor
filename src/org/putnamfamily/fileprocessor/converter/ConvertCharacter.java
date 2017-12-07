
package org.putnamfamily.fileprocessor.converter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 4, 2006
 * Time: 7:14:46 PM
 * File id: $Id: ConvertCharacter.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
final class ConvertCharacter extends BaseConverter {
    private static final Logger LOGGER = LogManager.getLogger();

    public Character read(final String field) throws ConverterException {
        LOGGER.trace("ConvertCharacter.read");
        if (field == null || "".equals(field.trim())) {
            return null;
        } else {
            return Character.valueOf(field.charAt(0));
        }
    }
}
