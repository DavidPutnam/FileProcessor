
package org.putnamfamily.fileprocessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: May 9, 2007
 * Time: 7:09:37 PM
 * File id: $Id: StoreObject.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class StoreObject implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String dbmgr;
    private final Object storable;

    public StoreObject(String dbmgr, Object storable) {
        this.dbmgr = dbmgr;
        this.storable = storable;
    }

    public void run() {
        // simulate processing effort between 1 and 10 seconds
        long milliseconds = Math.round(Math.random() * 4000) + 1000;

        LOGGER.info("Store '" + storable.toString() + "' with manager '" + dbmgr + "' taking " + milliseconds
                + " milliseconds.");
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            LOGGER.warn("I was not done with '" + storable.toString() + "' yet!");
        }
    }
}
