
package org.putnamfamily.fileprocessor.converter;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:00:47 PM
 * File id: $Id: ConverterException.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
public final class ConverterException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = -2302403693151096216L;

    ConverterException() {
        super();
    }

    ConverterException(String message) {
        super(message);
    }

    ConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    ConverterException(Throwable cause) {
        super(cause);
    }
}
