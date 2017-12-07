
package org.putnamfamily.fileprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: May 9, 2007
 * Time: 7:36:49 PM
 * File id: $Id: UncaughtHandlerImpl.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class UncaughtHandlerImpl implements Thread.UncaughtExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    public void uncaughtException(Thread t, Throwable e) {
        LOGGER.fatal("Thread terminated with exception: " + t.getName(), e);
    }
}
