
package org.putnamfamily.fileprocessor.datafile;
/*
 * @(#)Echo01.java 1.5 99/02/09
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 *
 * File id: $Id: Echo01.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Echo01 extends DefaultHandler {
    public static void main(String argv[]) {
        if (argv.length != 1) {
            System.err.println("Usage: cmd filename");
            System.exit(1);
        }

        // Use an instance of ourselves as the SAX event handler
        DefaultHandler handler = new Echo01();
        // Use the default (non-validating) data file
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            // Set up output stream
            out = new OutputStreamWriter(System.out, "UTF8");

            // Parse the input
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(new File(argv[0]), handler);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(0);
    }

    static private Writer out;

    //===========================================================
    // SAX DocumentHandler methods
    //===========================================================

    public void startDocument() throws SAXException {
        emit("<?xml version='1.0' encoding='UTF-8'?>");
        nl();
    }

    public void endDocument() throws SAXException {
        try {
            nl();
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    public void startElement(String namespaceURI, String lName, // local name
            String qName, // qualified name
            Attributes attrs) throws SAXException {
        String eName = lName; // element name
        if ("".equals(eName)) {
            eName = qName; // namespaceAware = false
        }
        emit("<" + eName);
        if (attrs != null) {
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr name
                if ("".equals(aName)) {
                    aName = attrs.getQName(i);
                }
                emit(" ");
                emit(aName + "=\"" + attrs.getValue(i) + "\"");
            }
        }
        emit(">");
    }

    public void endElement(String namespaceURI, String lName, // simple name
            String qName // qualified name
    ) throws SAXException {
        String eName = lName; // element name
        if ("".equals(eName)) {
            eName = qName; // namespaceAware = false
        }
        emit("</" + eName + ">");
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String s = new String(buf, offset, len);
        emit(s);
    }

    //===========================================================
    // Utility Methods ...
    //===========================================================

    // Wrap I/O exceptions in SAX exceptions, to
    // suit handler signature requirements

    private void emit(String s) throws SAXException {
        try {
            out.write(s);
            out.flush();
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }

    // Start a new line
    private void nl() throws SAXException {
        String lineEnd = System.getProperty("line.separator");
        try {
            out.write(lineEnd);
        } catch (IOException e) {
            throw new SAXException("I/O error", e);
        }
    }
}
