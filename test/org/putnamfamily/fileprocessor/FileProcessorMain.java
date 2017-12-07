
package org.putnamfamily.fileprocessor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.putnamfamily.fileprocessor.datafile.FileDefinitionFactory;
import org.putnamfamily.fileprocessor.datafile.FileParserException;
import org.putnamfamily.fileprocessor.datafile.DataFileParser;

/**
 * Created by IntelliJ IDEA.
 * User: david
 * Date: Oct 31, 2006
 * Time: 7:42:53 PM
 * File id: $Id: FileProcessorMain.java 12 2008-01-07 01:02:08Z david $
 *
 * @author $Author: david $
 * @version $Revision: 12 $
 */
public class FileProcessorMain {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PROGRESS_INTERVAL = 10;
    private static final int CORE_THREAD_POOL_SIZE = 5;
    private static final int MAX_THREAD_POOL_SIZE = CORE_THREAD_POOL_SIZE;
    private static final int QUEUE_LENGTH_FACTOR = 5;
    // how many seconds to wait for shutdown and how long before reaping idle threads
    private static final int THREAD_POOL_WAIT = 300;
    private BufferedReader dataFile;
    private DataFileParser parser;
    private ExecutorService executeQueue;

    public static void main(String args[]) {
        //LOGGER.info("Application build " + VersionUtil.getBuildTag(FileProcessorMain.class));
        try {
            LOGGER.info("Start Application");
            FileProcessorMain application = initialize(args);
            if (application != null) {
                application.run();
            } else {
                throw new Exception("Unable to initialize application: " + FileProcessorMain.class.getName());
            }
        } catch (Throwable ex) {
            usage();
            LOGGER.fatal("Fatal error", ex);
            System.exit(1);
        }
        LOGGER.info("Stop Application");
    }

    public static void usage() {
        System.out.println("Usage: <application> <datafile> <datadescription>");
    }

    public static FileProcessorMain initialize(String args[]) throws IllegalArgumentException {
        FileProcessorMain application = new FileProcessorMain();

        // VM argument -Dlog4j.configurationFile=log4j-config.xml

        // open the file that needs to be loaded.
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException("Unable to open: '" + args[0] + "'", ex);
        }
        application.setDataFile(reader);

        // create a data file parser for the given file definition.
        DataFileParser parser;
        try {
            parser = FileDefinitionFactory.create(args[1]);
        } catch (FileParserException ex) {
            throw new IllegalArgumentException("Unable to read the file definition file.", ex);
        }
        application.setParser(parser);

        // create a thread pool with a fixed size and a blocking queue. To throttle the amount
        // of data being pushed through the system make the caller run a process if the pool
        // is busy and the queue is full.
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE,
            THREAD_POOL_WAIT, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(MAX_THREAD_POOL_SIZE * QUEUE_LENGTH_FACTOR));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtHandlerImpl());
        application.setExecuteQueue(executor);

        LOGGER.info("Executor with {} to {} threads created.", CORE_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE);
        // database initialization would also go here.

        // application is now ready to run.
        return application;
    }

    public void run() throws IOException {
        BufferedReader reader = getDataFile();

        int recNum = 0;
        int objectNum = 0;
        Object target = null;
        String line = reader.readLine();
        while (line != null) {
            if (recNum % PROGRESS_INTERVAL == 0) {
                LOGGER.info(recNum + " records read.");
            }

            try {
                if (getParser().isFirstOfSet(line)) {
                    // this record is the first of a multiple part set,
                    // or the only record necessary to create the target.

                    // store the last object into the database
                    if (target != null) {
                        objectNum++;
                        getExecuteQueue().execute(new StoreObject("theManager", target));
                    }
                    target = getParser().createInstance(line);
                }
                getParser().assignAttributes(target, line);
            } catch (FileParserException ex) {
                // if there is an error, the record is unknown, log it and continue.
                LOGGER.error("Error on line " + recNum + " beginning with: '"
                        + line.substring(0, Math.min(20, line.length())) + "'.",
                    ex);
            }

            // get the next line
            line = reader.readLine();
            recNum++;
        }
        // store the last object into the database
        if (target != null) {
            objectNum++;
            getExecuteQueue().execute(new StoreObject("theManager", target));
        }

        // close the input file and report final status
        reader.close();
        if (recNum % PROGRESS_INTERVAL != 0) {
            LOGGER.info(recNum + " records read.");
        }
        LOGGER.info("File reading complete. {} object(s) stored.", objectNum);

        // shut down the executor queue
        getExecuteQueue().shutdown();
        try {
            getExecuteQueue().awaitTermination(THREAD_POOL_WAIT, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            LOGGER.error("Error shutting down the execution queue.", ex);
        }
    }

    public BufferedReader getDataFile() {
        return dataFile;
    }

    public void setDataFile(BufferedReader dataFile) {
        this.dataFile = dataFile;
    }

    public DataFileParser getParser() {
        return parser;
    }

    public void setParser(DataFileParser parser) {
        this.parser = parser;
    }

    public ExecutorService getExecuteQueue() {
        return executeQueue;
    }

    public void setExecuteQueue(ExecutorService executeQueue) {
        this.executeQueue = executeQueue;
    }
}
