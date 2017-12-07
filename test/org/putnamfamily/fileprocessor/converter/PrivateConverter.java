
package org.putnamfamily.fileprocessor.converter;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Nov 1, 2006
 * Time: 7:37:58 PM
 * File id: $Id: PrivateConverter.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
public class PrivateConverter extends BaseConverter {
    private PrivateConverter() {
        super();
    }

    @Override
    public String write(Object field) {
        return field.toString();
    }

    public Object read(String field) {
        return field;
    }
}
