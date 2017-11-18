/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wraps Logger functionality
 *
 * @author Benjamin Petry
 */
public class Log
{
    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------

    public static final String LOG_PATH = "log/";
    private static String currentLogFile = null;
    private static Handler currentFileHandler = null;

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Initializes saving the logs into a file
     *
     * @param path path for the file. Use null for the standard path (LOG_PATH)
     * @param filename the filename of the log file. Use null for the format:
     * log_[yyyy-MM-dd_HH-mm-ss].log
     */
    public static void init(String path, String filename)
    {
        if (path == null)
        {
            path = LOG_PATH;
        }
        if (filename == null)
        {
            String date = Util.formatDate(new Date(),
                    "yyyy-MM-dd_HH-mm-ss");
            filename = "log_" + date + ".log";
        }
        currentLogFile = Util.normalizePath(path) + filename;
        try
        {
            File f = new File(currentLogFile);
            Util.makeDir(f.getParent());
            currentFileHandler = new FileHandler(f.getAbsolutePath());
            Logger.getLogger("").addHandler(currentFileHandler);
        }
        catch (IOException | SecurityException ex)
        {
            Log.error("Could not initialize file logging", ex);
            return;
        }
        Log.info("Logging initialized");
    }

    public static String getCurrentFile()
    {
        return currentLogFile;
    }

    public static void flush()
    {
        if (currentFileHandler != null)
        {
            currentFileHandler.flush();
        }
    }

    public static void info(String message)
    {
        log(getCallingMethod(), Level.INFO, message, null);
    }

    public static void info(String message, Exception ex)
    {
        log(getCallingMethod(), Level.INFO, message, null);
    }

    public static void warning(String message)
    {
        log(getCallingMethod(), Level.WARNING, message, null);
    }

    public static void warning(String message, Exception ex)
    {
        log(getCallingMethod(), Level.WARNING, message, ex);
    }

    public static void error(String message, Exception ex)
    {
        log(getCallingMethod(), Level.SEVERE, message, ex);
    }

    public static void log(Level level, String message, Exception ex)
    {
        log(getCallingMethod(), level, message, ex);
    }

    private static void log(StackTraceElement info, Level level, String message,
            Exception ex)
    {
        String className = (info == null) ? "" : info.getClassName();
        String methodName = (info == null) ? "" : info.getMethodName();
        Logger.getLogger(className).logp(level, className, methodName, message,
                ex);
    }

    //-------------------------------------------------------------------------
    //////////////////////////  Private Static Methods ////////////////////////
    //-------------------------------------------------------------------------
    private static StackTraceElement getCallingMethod()
    {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length >= 4)
        {
            return stackTraceElements[3];
        }
        return null;
    }

}
