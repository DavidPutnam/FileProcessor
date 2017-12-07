
package org.putnamfamily.fileprocessor.converter;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 25, 2006
 * Time: 7:05:26 PM
 * File id: $Id: Converter.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
public interface Converter {
    /**
     * The read method will read a section of the whole line
     * into an <code>Object</code> based on the arguments to the
     * factory.
     *
     * @param field character string to read to object
     * @return an appropriate object
     * @throws ConverterException if any problems occur during conversion
     */
    Object read(final String field) throws ConverterException;

    String write(final Object field) throws ConverterException;
}
