
package org.putnamfamily.fileprocessor.datafile;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 13, 2007
 * Time: 7:26:52 PM
 * File id: $Id: DataFileException.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public final class DataFileException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 654776978107638503L;

    DataFileException() {
        super();
    }

    DataFileException(String message) {
        super(message);
    }

    DataFileException(String message, Throwable cause) {
        super(message, cause);
    }

    DataFileException(Throwable cause) {
        super(cause);
    }
}
