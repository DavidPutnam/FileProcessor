
package org.putnamfamily.fileprocessor.datafile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 24, 2006
 * Time: 7:38:49 PM
 * File id: $Id: DelimitedDataParser.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */

final class DelimitedDataParser {
    // states used to determine what to do with each character.
    private static final int NORMAL = 0;
    private static final int AFTER_DELIM = 1;
    private static final int QUOTE = 2;
    private static final int AFTER_QUOTE = 3;
    private static final int QUOTE_ESC = 4;

    private DelimitedDataParser() {
    }

    public static List<String> getFieldsFromLine(final char delimChar, final char quoteChar, final char escapeChar,
            final String line) {
        // Initial guess for the number of fields in on the line.
        List<String> result = new ArrayList<String>(30);

        if (line == null) {
            return result;
        }
        StringBuffer field = new StringBuffer(line.length());
        field.setLength(0);

        // set the initial state and drop into the machine.
        int state = AFTER_DELIM;
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            switch (state) {
                case NORMAL:
                    if (character == delimChar) {
                        state = AFTER_DELIM;
                    } else {
                        field.append(character);
                    }
                    break;

                case AFTER_DELIM:
                    if (character == quoteChar) {
                        state = QUOTE;
                    } else if (character == delimChar) {
                        state = AFTER_DELIM;
                    } else {
                        field.append(character);
                        state = NORMAL;
                    }
                    break;

                case QUOTE:
                    if (character == quoteChar) {
                        state = AFTER_QUOTE;
                    } else if (character == escapeChar) {
                        state = QUOTE_ESC;
                    } else {
                        // in this state a delimChar is just another character
                        field.append(character);
                    }
                    break;

                case QUOTE_ESC:
                    if (character == 'n') {
                        field.append("\n");
                    } else {
                        // in this state a delimChar is just another character
                        field.append(character);
                    }
                    state = QUOTE;
                    break;

                case AFTER_QUOTE:
                    if (character == delimChar) {
                        state = AFTER_DELIM;
                    }
                    break;
            }
            if (state == AFTER_DELIM) {
                result.add(field.toString());
                field.setLength(0);
            }
        }
        if (state == NORMAL || state == AFTER_QUOTE || state == AFTER_DELIM) {
            result.add(field.toString());
            field.setLength(0);
        }
        return result;
    }

    public static List<String> getFieldsFromLine1(final char delimChar, final char quoteChar, final char escapeChar,
            final String line) {
        // original subroutine from checkpointfileloader, but with a signature to match above function.
        List<String> result;
        StringBuffer field;
        int state = AFTER_DELIM;

        if (line == null || line.length() == 0) {
            return null;
        }

        field = new StringBuffer(line.length());
        result = new ArrayList<String>(30);

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            switch (state) {
                case NORMAL:
                    if (character == delimChar) {
                        result.add(field.toString());
                        field.setLength(0);
                        state = AFTER_DELIM;
                    } else {
                        field.append(character);
                    }
                    break;

                case AFTER_DELIM:
                    if (character == quoteChar) {
                        state = QUOTE;
                    } else if (character == delimChar) {
                        result.add("");
                        field.setLength(0);
                    } else {
                        field.append(character);
                        state = NORMAL;
                    }
                    break;

                case QUOTE:
                    if (character == quoteChar) {
                        result.add(field.toString());
                        field.setLength(0);
                        state = AFTER_QUOTE;
                    } else if (character == escapeChar) {
                        state = QUOTE_ESC;
                    } else {
                        field.append(character);
                    }
                    break;

                case QUOTE_ESC:
                    if (character == 'n') {
                        field.append("\n");
                    } else {
                        field.append(character);
                    }
                    state = QUOTE;
                    break;

                case AFTER_QUOTE:
                    if (character == delimChar) {
                        state = AFTER_DELIM;
                    }
                    break;
            }
        }

        if (state != AFTER_QUOTE) {
            result.add(field.toString());
        }

        return result;
    }
}
