/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2018 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.math.statistics;

import de.bpetry.util.Tuple;
import java.util.List;

/**
 * Contains methods for statistical calculations. This class is not
 * comprehensive and will be complemented based on requirements.
 *
 * @author Benjamin Petry
 */
public class StatisticUtil
{

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
        CohensKappaTable<T> table = new CohensKappaTable<>(ratingPairs);
        return cohensKappa(table);
    }

    /**
     * Calculates Cohen's Kappa based on a frequency table
     *
     * @param <T> the rating type (e.g. integers for Likert scales)
     * @param ratingTable the table that contains the frequencies for each
     * rating pair
     * @return Cohen's Kappa
     */
    public static <T extends Object> double cohensKappa(
            CohensKappaTable<T> ratingTable)
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
