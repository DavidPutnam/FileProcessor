
package org.putnamfamily.fileprocessor.datafile;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.putnamfamily.fileprocessor.converter.Converter;
import org.putnamfamily.fileprocessor.converter.ConverterException;
import org.putnamfamily.fileprocessor.converter.ConverterFactory;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 12, 2006
 * Time: 7:31:55 PM
 * File id: $Id: Attribute.java 15 2008-10-02 00:36:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 15 $
 */
abstract class Attribute implements Comparable<Attribute> {
    private static final Logger LOGGER = LogManager.getLogger();
    // from the record definition
    private String name;
    private String format;
    // set during initialization
    private Method setMethod = null;
    private Method getMethod = null;
    private Field field = null;
    private Class<?> convertType;
    private Converter converter;
    private static final int SCALAR_ARGUMENT = -1;
    private int numberElements = SCALAR_ARGUMENT;

    protected abstract void validateBasicArgs();

    protected abstract int validateArrayArgs();

    protected abstract int validateCoverage(String recordId, int currentPosition);

    protected abstract String convertField(String wholeLine);

    protected abstract String convertField(String wholeLine, int index);

    protected abstract String writeAttribute(String fieldValue) throws FileParserException;

    protected final void initialize(Class<?> target) throws FileParserException {
        // verify the basic attributes.
        if (getName() == null || getName().length() <= 0) {
            String message = "Name is not valid. '" + getName() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        validateBasicArgs();

        Class<?> argumentType;
        // see if a setMethod exists on this class for this attribute
        argumentType = findSetMethod(target);

        if (setMethod == null) {
            // if we can't find a set method, see if we can directly access the field.
            argumentType = findSetField(target);
        }

        if (argumentType == null) {
            // we were unable to find any way to assign a value.
            return;
        }
        findGetMethod(target);

        // based on the attribute type
        // if it is an array, validate the array attributes and get the array component type
        // since that is what we need a converter for.
        if (argumentType.isArray()) {
            numberElements = validateArrayArgs();
            if (numberElements <= 0) {
                throw new FileParserException("Invalid array lengh.");
            }
            // if the argument is an array of ...,
            // we really want to read the array's component type.
            convertType = argumentType.getComponentType();
        } else {
            convertType = argumentType;
            numberElements = SCALAR_ARGUMENT;
        }

        // until we set the converter, this field will be ignored.
        try {
            converter = ConverterFactory.getInstance().create(convertType, getFormat());
        } catch (ConverterException ex) {
            throw new FileParserException("Unable to get converter for attribute: " + getName(), ex);
        }
    }

    private Class<?> findSetField(Class<?> target) throws FileParserException {
        try {
            field = target.getField(getName());
        } catch (NoSuchFieldException ex) {
            LOGGER.debug("No field for attribute: " + getName() + " in Class: " + target.getName());
        }
        if (field != null) {
            // The type we need to read to is the type of the field.
            return field.getType();
        } else {
            return null;
        }
    }

    private Class<?> findSetMethod(Class<?> target) throws FileParserException {
        // determine the setMethod function name
        char[] attributeName = getName().toCharArray();
        attributeName[0] = Character.toUpperCase(attributeName[0]);
        String setterName = "set" + new String(attributeName);

        // since we do not know the argument type, only the number of them,
        // we need to look through all of the methods looking for one with the
        // correct name and exactly one argument.
        int count = 0;
        Method saveMethod = null;
        for (Method method : target.getMethods()) {
            // Look for set methods with exactly one argument.
            if (setterName.equals(method.getName()) && method.getParameterTypes().length == 1) {
                saveMethod = method;
                count++;
            }
        }
        if (count == 0) {
            LOGGER.debug("No setMethod for attribute: " + getName() + " in Class: " + target.getName());
            // saveMethod will be null because no match occurred.
        } else if (count > 1) {
            // there could be more than one because the argument types may differ
            throw new FileParserException("Non-distinct setMethod for attribute: " + getName());
        }
        setMethod = saveMethod;
        if (setMethod != null) {
            // The type we need to read to is the type of the argument of the setter.
            // we know there is exactly one argument.
            return setMethod.getParameterTypes()[0];
        } else {
            return null;
        }
    }

    private Class<?> findGetMethod(Class<?> target) throws FileParserException {
        // determine the getMethod function name
        char[] attributeName = getName().toCharArray();
        attributeName[0] = Character.toUpperCase(attributeName[0]);
        String getterName = "get" + new String(attributeName);

        // since we do not know the result type,
        // we need to look through all of the methods looking for one with the
        // correct name and no arguments.
        int count = 0;
        Method saveMethod = null;
        for (Method method : target.getMethods()) {
            // Look for get methods with no argument.
            if (getterName.equals(method.getName()) && method.getParameterTypes().length == 0) {
                saveMethod = method;
                count++;
            }
        }
        if (count == 0) {
            LOGGER.debug("No getMethod for attribute: " + getName() + " in Class: " + target.getName());
            // saveMethod will be null because no match occurred.
        } else if (count > 1) {
            // count cannot be greater than 1 because return type is not part of the signature
            throw new FileParserException("Non-distinct getMethod for attribute: " + getName());
        }
        getMethod = saveMethod;
        if (getMethod != null) {
            // The type we need to read to is the class of the return type.
            return getMethod.getReturnType();
        } else {
            return null;
        }
    }

    protected final void assignValue(Object target, String wholeLine) throws FileParserException {
        LOGGER.trace("Attribute.assignValue");
        // if this attribute is not needed, skip it.
        if (converter == null) {
            return;
        }

        // value will be the object to assign to the target.
        Object value = readValue(wholeLine);

        if (setMethod != null) {
            try {
                Object[] args = { value };
                setMethod.invoke(target, args);
            } catch (IllegalAccessException ex) {
                throw new FileParserException("Unable to access setMethod for attribute: " + getName(), ex);
            } catch (InvocationTargetException ex) {
                throw new FileParserException("Unable to invoke setMethod for attribute: " + getName(), ex);
            } catch (IllegalArgumentException ex) {
                throw new FileParserException("Unable to assign value for attribute: " + getName(), ex);
            }
        } else if (field != null) {
            try {
                field.set(target, value);
            } catch (IllegalAccessException ex) {
                throw new FileParserException("Unable to access field for attribute: " + getName(), ex);
            }
        }
    }

    protected final String writeValue(Object target) throws FileParserException {
        LOGGER.trace("Attribute.writeValue");
        // value will be the attribute that is written.
        Object value = null;

        // if this attribute is not needed, skip it.
        if (converter == null) {
            value = "";
        } else if (getMethod != null) {
            try {
                value = getMethod.invoke(target);
            } catch (IllegalAccessException ex) {
                throw new FileParserException("Unable to access getMethod for attribute: " + getName(), ex);
            } catch (InvocationTargetException ex) {
                throw new FileParserException("Unable to invoke getMethod for attribute: " + getName(), ex);
            } catch (IllegalArgumentException ex) {
                throw new FileParserException("Unable to assign value for attribute: " + getName(), ex);
            }
        } else if (field != null) {
            try {
                value = field.get(target);
            } catch (IllegalAccessException ex) {
                throw new FileParserException("Unable to access field for attribute: " + getName(), ex);
            }
        }
        String fieldValue;
        if (converter != null) {
            try {
                fieldValue = converter.write(value);
            } catch (ConverterException ex) {
                throw new FileParserException("Unable to write field for attribute: " + getName(), ex);
            }
        } else {
            fieldValue = "";
        }
        return writeAttribute(fieldValue);
    }

    private Object readValue(String wholeLine) throws FileParserException {
        LOGGER.trace("Attribute.readValue");
        Object value;
        try {
            if (numberElements == SCALAR_ARGUMENT) {
                String field = convertField(wholeLine);
                value = converter.read(field);
            } else {
                value = Array.newInstance(convertType, numberElements);
                for (int i = 0; i < numberElements; i++) {
                    String field = convertField(wholeLine, i);
                    Array.set(value, i, converter.read(field));
                }
            }
        } catch (ConverterException ex) {
            throw new FileParserException("Unable to read value for attribute: " + getName(), ex);
        }
        return value;
    }

    /* accessors below here */

    protected Attribute(final String name, final String format) {
        this.name = name;
        this.format = format;
    }

    protected final String getName() {
        return name;
    }

    protected final String getFormat() {
        return format;
    }
}
