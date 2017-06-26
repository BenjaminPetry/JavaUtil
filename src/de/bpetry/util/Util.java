/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.io.File;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility functions
 * @author Benjamin Petry
 */
public class Util
{
    
    public static void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
        for (File file : f.listFiles()) {
            if (file.isFile() && file.getName().startsWith("hs_err_pid") && file.getName().endsWith(".log"))
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
     * Adds a closing slash if not existing
     * @param path the path to normalize
     * @return path with an ending slash
     */
    public static String normalizePath(String path)
    {
        if (!path.endsWith("/") || !path.endsWith("\\"))
        {
            path += "/";
        }
        return path;
    }
}
