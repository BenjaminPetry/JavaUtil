/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.math;

/**
 * Math util functions
 *
 * @author Benjamin Petry
 */
public class MathUtil
{

    /**
     * Rounds a double number to a certain precision. Originally from here:
     * https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
     *
     * @param value value to round
     * @param precision number of decimals
     * @return rounded value
     */
    public static double round(double value, int precision)
    {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

}
