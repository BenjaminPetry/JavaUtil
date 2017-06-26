/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.util.HashSet;

/**
 * Util class to convert objects to strings/string arrays
 *
 * @author Benjamin Petry
 */
public class StringMaker
{
    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    
    public static String[] hashSetToStringArray(HashSet<String> set)
    {
        if (set == null || set.isEmpty())
        {
            return new String[0];
        }
        String[] result = new String[set.size()];
        int counter = 0;
        for (String s : set)
        {
            result[counter] = s;
            counter++;
        }
        return result;
    }
}
