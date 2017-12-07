
package org.putnamfamily.fileprocessor.datafile;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 13, 2007
 * Time: 7:26:52 PM
 * File id: $Id: FileWriterException.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public final class FileWriterException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 3162025565068122177L;

    FileWriterException() {
        super();
    }

    FileWriterException(String message) {
        super(message);
    }

    FileWriterException(String message, Throwable cause) {
        super(message, cause);
    }

    FileWriterException(Throwable cause) {
        super(cause);
    }
}
