
package org.putnamfamily.fileprocessor.converter;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:29:34 PM
 * File id: $Id: ConverterFactory.java 6 2007-12-20 02:50:49Z david $
 *
 * @author $Author: david $
 * @version $Revision: 6 $
 */
public final class ConverterFactory {
    private static final Logger LOGGER = LogManager.getLogger(ConverterFactory.class);
    private static volatile ConverterFactory ourInstance;
    private static Map<Class<?>, Class<? extends BaseConverter>> converterMap = null;

    public static ConverterFactory getInstance() {
        //Double check locking pattern
        if (ourInstance == null) { //Check for the first time

            synchronized (ConverterFactory.class) { //Check for the second time.
                //if there is no instance available... create new one
                if (ourInstance == null) {
                    ourInstance = new ConverterFactory();
                    //ourInstance.initializeMap();
                }
            }
        }

        return ourInstance;
    }

    private ConverterFactory() {
        if (ourInstance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    //Make singleton from serialize and deserialize operation.
    protected ConverterFactory readResolve() {
        return getInstance();
    }

    private synchronized void initializeMap() throws ConverterException {
        if (converterMap == null) {
            converterMap = new HashMap<Class<?>, Class<? extends BaseConverter>>(20);
            // To support any new type simply write the converter, extending BaseConverter,
            // and add to this map by registering them.

            // These represent primitive types
            register(Boolean.TYPE, ConvertBoolean.class);
            register(Byte.TYPE, ConvertByte.class);
            register(Character.TYPE, ConvertCharacter.class);
            register(Double.TYPE, ConvertDouble.class);
            register(Float.TYPE, ConvertFloat.class);
            register(Integer.TYPE, ConvertInteger.class);
            register(Long.TYPE, ConvertLong.class);
            register(Short.TYPE, ConvertShort.class);

            // These represent typical target classes.
            register(BigDecimal.class, ConvertBigDecimal.class);
            register(BigInteger.class, ConvertBigInteger.class);
            register(Boolean.class, ConvertBoolean.class);
            register(Byte.class, ConvertByte.class);
            register(Character.class, ConvertCharacter.class);
            register(Date.class, ConvertDate.class);
            register(Double.class, ConvertDouble.class);
            register(Float.class, ConvertFloat.class);
            register(Integer.class, ConvertInteger.class);
            register(Long.class, ConvertLong.class);
            register(Short.class, ConvertShort.class);
            register(String.class, ConvertString.class);

            // These represent custom domain objects
            //register(.class, null);
        }
    }

    //   public static Converter create(Class targetClazz, String format)
    //      throws converterException
    //   {
    //      return converterFactory.getInstance().create(targetClazz, format);
    //   }

    /**
     * The <code>converterFactory</code> creates conversion functions from strings
     * to defined classes suitable to call set methods with.
     *
     * @param targetClazz This will be the return type of <code>converter.read</code> method.
     * @param format      Some Classes have special formating requirements. i.e. Date, BigDecimal
     * @return Converter that implements the above rules.
     * @throws ConverterException if Converter cannot be created or accessed.
     */
    public Converter create(final Class<?> targetClazz, final String format) throws ConverterException {
        LOGGER.trace("ConverterFactor.create");
        // make sure the converter map is filled in.
        if (converterMap == null) {
            initializeMap();
        }

        // validate arguments
        if (targetClazz == null) {
            final String message = "Class argument cannot be null.";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        // get the converter class appropriate for the given target class.
        Class<?> converterClazz = converterMap.get(targetClazz);
        if (converterClazz == null) {
            final String message = "Unsupported type: " + targetClazz.getName();
            LOGGER.error(message);
            throw new ConverterException(message);
        }

        // create a new converter
        BaseConverter converter;
        try {
            converter = (BaseConverter) converterClazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException ex) {
            throw new ConverterException("Unable to create converter: " + converterClazz.getName() + ".", ex);
        } catch (IllegalAccessException ex) {
            throw new ConverterException("Unable to access converter: " + converterClazz.getName() + ".", ex);
        } catch (IllegalArgumentException ex) {
            throw new ConverterException("No zero arg constructor for converter: " + converterClazz.getName() + ".", ex);
        } catch (InvocationTargetException ex) {
            throw new ConverterException("No constructor for converter: " + converterClazz.getName() + ".", ex);
        } catch (NoSuchMethodException ex) {
            throw new ConverterException("Unable to access converter: " + converterClazz.getName() + ".", ex);
        } catch (SecurityException ex) {
            throw new ConverterException("Unable to access converter: " + converterClazz.getName() + ".", ex);
        }

        // set the format attribute of the new converter and return
        if (converter != null) {
            converter.setFormatPattern(format);
        }
        return converter;
    }

    public synchronized void register(final Class<?> clazz, final Class<? extends BaseConverter> converter)
            throws ConverterException {
        if (clazz == null || converter == null) {
            throw new ConverterException("Must provide both the class and converter to register.");
        }
        // make sure the converter map is filled in.
        if (converterMap == null) {
            initializeMap();
        }
        deregister(clazz);
        converterMap.put(clazz, converter);
    }

    public synchronized void deregister(final Class<?> clazz) throws ConverterException {
        if (clazz == null) {
            throw new ConverterException("Must provide the class to deregister.");
        }
        // make sure the converter map is filled in.
        if (converterMap == null) {
            initializeMap();
        }
        if (converterMap.containsKey(clazz)) {
            converterMap.remove(clazz);
        }
    }
}
