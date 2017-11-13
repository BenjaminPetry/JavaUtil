/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util.math;

import de.bpetry.util.Tuple;
import java.util.List;

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

    /**
     * Calculates Cohen's Kappa based on a list of ratings
     *
     * @param <T> the rating type (e.g. integers for Likert scales)
     * @param ratingPairs the rating pairs of 2 reviewers
     * @return Cohen's Kappa
     */
    public static <T extends Object> double cohensKappa(
            List<Tuple<T, T>> ratingPairs)
    {
        FrequencyTable<T> table = new FrequencyTable<>(ratingPairs);
        return cohensKappa(table);
    }

    /**
     * Calculates Cohen's Kappa based on a frequency table
     *
     * @param <T> the rating type (e.g. integers for Likert scales)
     * @param ratingTable the table that contains the frequencies for each
     * rating pair
     * @return Cohen's Kappa;
     */
    public static <T extends Object> double cohensKappa(
            FrequencyTable<T> ratingTable)
    {
        int length = ratingTable.length();
        double agreement = 0;
        int[] agreementByChanceX = new int[length];
        int[] agreementByChanceY = new int[length];
        for (int x = 0; x < length; x++)
        {
            for (int y = 0; y < length; y++)
            {
                long count = ratingTable.getField(x, y);
                agreement += (x == y) ? count : 0;
                agreementByChanceX[x] += count;
                agreementByChanceY[y] += count;
            }
        }
        double agreementByChanceSum = 0;
        for (int n = 0; n < length; n++)
        {
            agreementByChanceSum += agreementByChanceX[n] * agreementByChanceY[n] / (double) ratingTable.getSum();
        }
        return (agreement - agreementByChanceSum) / (ratingTable.getSum() - agreementByChanceSum);
    }
}
