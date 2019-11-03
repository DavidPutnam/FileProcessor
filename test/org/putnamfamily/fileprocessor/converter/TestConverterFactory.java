
package org.putnamfamily.fileprocessor.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:45:28 PM
 * File id: $Id: TestConverterFactory.java 13 2008-01-25 03:28:07Z david $
 *
 * @author $Author: david $
 * @version $Revision: 13 $
 */
public class TestConverterFactory {

    @Test
    public void testTypeNull() {
        try {
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(null, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Class argument cannot be null."));
        }
    }

    @Test
    public void testGoodArgs() {
        try {
            Object expected = "12345";
            Converter converter = ConverterFactory.getInstance().create(String.class, null);
            Object received = converter.read("12345");
            assertEquals(expected, received);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testUnsupportedType() {
        try {
            @SuppressWarnings("unused")
            Converter converter = ConverterFactory.getInstance().create(Object.class, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unsupported type:"));
        } catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testNoArgConstructor() {
        @SuppressWarnings("unused")
        ConverterFactory ef = ConverterFactory.getInstance();
    }

    @Test
    public void testRegisterGood() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(Class.class, BaseConverter.class);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testRegisterNoClass() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(null, BaseConverter.class);
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(),
                ex.getMessage().startsWith("Must provide both the class and converter to register."));
        }
    }

    @Test
    public void testRegisterNoConverter() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(Class.class, null);
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(),
                ex.getMessage().startsWith("Must provide both the class and converter to register."));
        }
    }

    @Test
    public void testDeregister() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.deregister(Class.class);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testNullRegister() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(null, null);
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(),
                ex.getMessage().startsWith("Must provide both the class and converter to register."));
        }
    }

    @Test
    public void testNullDeregister() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.deregister(null);
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Must provide the class to deregister."));
        }
    }

    @Test
    public void testPrivateconverter() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(Class.class, PrivateConverter.class);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
        try {
            @SuppressWarnings("unused")
            Converter converter = ef.create(Class.class, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unable to access converter:"));
        } catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testAbstractconverter() {
        ConverterFactory ef = ConverterFactory.getInstance();
        try {
            ef.register(Class.class, AbstractConverter.class);
        } catch (ConverterException ex) {
            fail(ex.getMessage());
        }
        try {
            @SuppressWarnings("unused")
            Converter converter = ef.create(Class.class, null);
            fail("An Exception was expected.");
        } catch (ConverterException ex) {
            assertTrue(ex.getMessage(), ex.getMessage().startsWith("Unable to create converter:"));
        } catch (IllegalArgumentException ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testSerialization() {
        try {
            ConverterFactory instance1 = ConverterFactory.getInstance();
            ObjectOutput out = null;
            out = new ObjectOutputStream(new FileOutputStream("filename.ser"));
            out.writeObject(instance1);
            out.close();

            //de-serialize from file to object
            ObjectInput in = new ObjectInputStream(new FileInputStream("filename.ser"));
            ConverterFactory instance2 = (ConverterFactory) in.readObject();
            in.close();

            assertEquals("Created a duplicate instance", instance1.hashCode(), instance2.hashCode());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
