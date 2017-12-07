
package org.putnamfamily.fileprocessor.datafile;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 13, 2007
 * Time: 7:24:11 PM
 * File id: $Id: DataFileWriter.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public interface DataFileWriter {
    String[] write(Object source) throws FileWriterException;

    String createLine(String recordId, Object source) throws FileWriterException;
}
