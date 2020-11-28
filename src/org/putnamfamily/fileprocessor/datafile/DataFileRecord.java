
package org.putnamfamily.fileprocessor.datafile;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 26, 2006
 * Time: 7:12:36 PM
 * File id: $Id: DataFileRecord.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
final class DataFileRecord {
    private static final Logger LOGGER = LogManager.getLogger();
    // from the record definition
    private List<Attribute> attributes;
    private boolean firstOfSet = false;
    private boolean required = false;
    private String id;
    private String sameAs;
    private String targetClassName;
    // filled in during validation
    private Class<?> targetClass;

    /**
     * Constructor for a real record definition.
     *
     * @param id              record identifier
     * @param targetClassName class to set values in
     * @param firstOfSet      determines when to stop reading the prior object and start a new one.
     * @param required        When writing, output the fields even if they are all blank.
     */
    DataFileRecord(String id, String targetClassName, boolean firstOfSet, boolean required) {
        this.id = id;
        this.sameAs = null;
        this.targetClassName = targetClassName;
        this.firstOfSet = firstOfSet;
        this.required = required;
    }

    /**
     * Constructor for a record that is really defined some where else.
     *
     * @param id     record identifier.
     * @param sameAs record identifier for the real definition.
     */
    DataFileRecord(String id, String sameAs) {
        this.id = id;
        this.sameAs = sameAs;
        this.targetClassName = null;
        this.firstOfSet = false;
        this.required = false;
    }

    Object createInstance() throws FileParserException {
        LOGGER.trace("DataFileRecord.createInstance");
        // make sure target class is what we expect.
        if (targetClass == null) {
            throw new FileParserException("createInstance invoked without being initialized.");
        }

        Object target;
        try {
            target = targetClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        } catch (IllegalAccessException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        } catch (IllegalArgumentException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        } catch (InvocationTargetException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        } catch (NoSuchMethodException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        } catch (SecurityException ex) {
            throw new FileParserException("Unable to create " + targetClass.getName(), ex);
        }
        return target;
    }

    void assignValues(Object target, String wholeLine) throws FileParserException {
        LOGGER.trace("DataFileRecord.assignValues");
        if (target == null || wholeLine == null) {
            throw new FileParserException("Source or destination value is 'null'.");
        }

        // make sure target class is what we expect.
        if (targetClass == null) {
            throw new FileParserException("assignValues invoked without being initialized.");
        }
        if (!targetClass.equals(target.getClass())) {
            throw new FileParserException("Target class missmatch, meant to assign values on '" + targetClass.getName()
                    + "' not '" + target.getClass().getName() + "' for record: " + getId() + ".");
        }

        // have each attribute assign its value to the target.
        if (getAttributes() != null) {
            for (Attribute attribute : getAttributes()) {
                attribute.assignValue(target, wholeLine);
            }
        }
    }

    void initialize() throws FileParserException {
        // verify the basic attributes.
        // Name is always required. (check length?)
        // TargetClassName is mutually exclusive with SameAs record spec.
        //  -- maybe the following are done at the file level
        // One record per target class should be first.
        // Should have one header, one trailer and one or more detail records
        if (getId() == null || getId().length() <= 0) {
            String message = "Name is not valid. '" + getId() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }
        boolean targetMissing = ((getTargetClassName() == null) || (getTargetClassName().length() <= 0));
        boolean sameasMissing = ((getSameAs() == null) || (getSameAs().length() <= 0));
        if ((targetMissing && sameasMissing) || (!targetMissing && !sameasMissing)) {
            String message = "Either targetClassName or sameAs must be set, and are mutually exclusive. ";
            message = message + "targetClass: '" + getTargetClassName() + "', ";
            message = message + "sameAs: '" + getSameAs() + "'. ";
            LOGGER.error(message);
            throw new IllegalArgumentException(message);
        }

        // load the class specified by the configuration.
        try {
            ClassLoader cl = DataFileRecord.class.getClassLoader();
            targetClass = cl.loadClass(getTargetClassName());
        } catch (ClassNotFoundException ex) {
            throw new FileParserException("Cannot load class: '" + getTargetClassName() + "' for record: " + getId(),
                ex);
        }

        // Initialize all of the attributes on this record, if any.
        if (getAttributes() != null) {
            for (Attribute attribute : getAttributes()) {
                attribute.initialize(targetClass);
            }
        }
    }

    void verifyCoverage() {
        LOGGER.trace("DataFileRecord.verifyCoverage");
        // make sure that all characters on the line are read (no gaps between fields)
        // and that the fields do not overlap.
        Collections.sort(getAttributes());
        int currentPosition = 1;
        for (Attribute attribute : getAttributes()) {
            currentPosition = attribute.validateCoverage(getId(), currentPosition);
        }
    }
    /* accessors below here */

    void addAttribute(Attribute attribute) {
        if (attributes == null) {
            //attributes = Collections.synchronizedList(new LinkedList<FixedFieldAttribute>());
            attributes = new LinkedList<Attribute>();
        }
        attributes.add(attribute);
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public boolean isFirstOfSet() {
        return firstOfSet;
    }

    public void setFirstOfSet(boolean firstOfSet) {
        this.firstOfSet = firstOfSet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSameAs() {
        return sameAs;
    }

    public void setSameAs(String sameAs) {
        this.sameAs = sameAs;
    }

    public String getTargetClassName() {
        return targetClassName;
    }

    public void setTargetClassName(String targetClassName) {
        this.targetClassName = targetClassName;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequred(boolean required) {
        this.required = required;
    }
}
