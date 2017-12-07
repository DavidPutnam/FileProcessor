
package org.putnamfamily.fileprocessor.domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Sep 27, 2006
 * Time: 7:47:33 PM
 * File id: $Id: TestingClass.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class TestingClass {
    //private static final Logger LOGGER = LogManager.getLogger();
    private String attribute;
    private String[] manyAttribs;
    private int intValue;
    //private int nonexistent; this variable must not exist.
    private Date dateValue;
    private double _double;
    private Object objectAttr;
    private double indistinct;
    public int publicInt; // no set accessor
    private int privateInt;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String[] getManyAttribs() {
        return manyAttribs.clone();
    }

    public void setManyAttribs(String[] manyAttribs) {
        this.manyAttribs = manyAttribs.clone();
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Date getDateValue() {
        return (Date) dateValue.clone();
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = (Date) dateValue.clone();
    }

    public double get_double() {
        return _double;
    }

    public void set_double(double _double) {
        this._double = _double;
    }

    public Object getObjectAttr() {
        return objectAttr;
    }

    public void setObjectAttr(Object objectAttr) {
        this.objectAttr = objectAttr;
    }

    public double getIndistinct() {
        return indistinct;
    }

    public void setIndistinct(double indistinct) {
        this.indistinct = indistinct;
    }

    public void setIndistinct(String indistinctString) {
        this.indistinct = Double.parseDouble(indistinctString);
    }

    public int getPrivateInt() {
        return privateInt;
    }

    @SuppressWarnings("unused")
    private void setPrivateInt(int privateInt) {
        this.privateInt = privateInt;
    }
}
