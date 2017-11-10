/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javafx.application.Platform;

/**
 * Utility functions
 *
 * @author Benjamin Petry
 */
public class Util
{

    /**
     * Executes a runnable after a given duration in the UI-Thread
     *
     * @param ms duration to wait in ms
     * @param run runnable to execute
     */
    public static void executeLaterUI(int ms, Runnable run)
    {
        new Thread(() ->
        {
            sleep(ms);
            Platform.runLater(() -> run.run());
        }).start();
    }

    /**
     * Makes the current thread sleep. Uses Thread.sleep and catches the
     * InterruptedExpection.
     *
     * @param ms duration in ms
     */
    public static void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        {
            Log.warning("Thread could not been interupted", ex);
        }
    }

    /**
     * Formats a date
     *
     * @param date the date to format
     * @param format the format the date should be converted to. E.g.
     * "yyyy-MM-dd hh:mm:ss.SSS"
     * @return string representation of the date
     */
    public static String formatDate(Date date, String format)
    {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        return dt.format(date);
    }

    /**
     * Parses a date
     *
     * @param date the date to parse as string
     * @param format the format the date is in
     * @return the parsed date or null if the date could not been parsed
     */
    public static Date parseDate(String date, String format)
    {
        SimpleDateFormat dt = new SimpleDateFormat(format);
        try
        {
            return dt.parse(date);
        }
        catch (ParseException ex)
        {
            Log.warning(
                    "Could not parse the date '" + date + "' with format '" + format + "'",
                    ex);
            return null;
        }
    }

    /**
     * Converts an double array into a string. Uses ';' as delimiter.
     *
     * @param array array to convert
     * @return string representation of an array
     */
    public static String doubleArrayToString(double[] array)
    {
        StringBuilder buf = new StringBuilder();
        for (double d : array)
        {
            if (buf.length() != 0)
            {
                buf.append(';');
            }
            buf.append(d);
        }
        return buf.toString();
    }

    /**
     * Parses a string into a double array. Expects ';' as delimiter.
     *
     * @param s string to parse
     * @return array
     */
    public static double[] stringToDoubleArray(String s)
    {
        String[] onsetsS = s.split(";");
        double[] dArray = new double[onsetsS.length];
        int counter = 0;
        for (String o : onsetsS)
        {
            dArray[counter] = Double.parseDouble(o);
            counter++;
        }
        return dArray;
    }

    /**
     * Assumes all integers are < 10
     *
     * @param array the integer array
     * @return the array as string
     */
    public static String intArrayToString(int[] array)
    {
        StringBuilder b = new StringBuilder();
        for (int i : array)
        {
            b.append(i);
        }
        return b.toString();
    }

    public static int[] stringToIntArray(String s)
    {
        char[] array = s.toCharArray();
        int[] result = new int[array.length];
        for (int n = 0; n < result.length; n++)
        {
            result[n] = Character.getNumericValue(array[n]);
        }
        return result;
    }

    public static void removeOpenCVLogs()
    {
        File f = new File(System.getProperty("user.dir"));
        for (File file : f.listFiles())
        {
            if (file.isFile() && file.getName().startsWith("hs_err_pid") && file.getName().endsWith(
                    ".log"))
            {
                file.delete();
            }
        }
    }

    public static <V> void shuffleArray(V[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            V a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    /**
     * Adds a closing slash if not existing and the path is a directory
     *
     * @param path the path to normalize
     * @return path with an ending slash
     */
    public static String normalizePath(String path)
    {
        if (!new File(path).isFile())
        {
            if (!path.endsWith("/") || !path.endsWith("\\"))
            {
                path += "/";
            }
        }
        return path;
    }

    /**
     * Creates the directory if it does not exists
     *
     * @param dir directory
     * @return the created directory
     */
    public static File makeDir(String dir)
    {
        File f = new File(dir);
        if (!f.exists() || !f.isDirectory())
        {
            f.mkdirs();
        }
        return f;
    }

    public static Thread run(Runnable r)
    {
        Thread t = new Thread(r);
        t.start();
        return t;
    }

    public static Thread timer(int triggerInMS, Runnable r)
    {
        return run(() ->
        {
            Util.sleep(triggerInMS);
            r.run();
        });
    }

    public static boolean openUrl(String url)
    {
        try
        {
            Desktop.getDesktop().browse(new URI(url));
            return true;
        }
        catch (IOException | URISyntaxException ex)
        {
            Log.error("Could not open " + url, ex);
            return false;
        }
    }
}
