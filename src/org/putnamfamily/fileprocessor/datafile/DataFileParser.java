
package org.putnamfamily.fileprocessor.datafile;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 2, 2006
 * Time: 7:24:24 PM
 * File id: $Id: DataFileParser.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public interface DataFileParser {
    boolean isFirstOfSet(String line) throws FileParserException;

    String getRecordId(String line) throws FileParserException;

    Object createInstance(String line) throws FileParserException;

    void assignAttributes(Object target, String line) throws FileParserException;
}
