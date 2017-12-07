
package org.putnamfamily.fileprocessor.datafile;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:01:34 PM
 * File id: $Id: FileParserException.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public final class FileParserException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 7870568595992400443L;

    FileParserException() {
        super();
    }

    FileParserException(String message) {
        super(message);
    }

    FileParserException(String message, Throwable cause) {
        super(message, cause);
    }

    FileParserException(Throwable cause) {
        super(cause);
    }
}
