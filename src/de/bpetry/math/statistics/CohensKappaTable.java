/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.math.statistics;

import de.bpetry.util.Tuple;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates a frequency table for Cohen's Kappa calculations.
 *
 * @author Benjamin Petry
 * @param <T> The type of the values of value-pairs.
 */
public class CohensKappaTable<T extends Object>
{

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private Object[] uniqueValues = new Object[0];
    private final Set<T> uniqueValuesTmp = new HashSet<>();
    private final Map<Tuple<T, T>, Long> tableFields = new HashMap<>();
    private long sum = 0;

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public CohensKappaTable()
    {
    }

    public CohensKappaTable(List<Tuple<T, T>> pairs)
    {
        pairs.forEach((pair) ->
        {
            addValue(pair);
        });
    }

    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------
    public void addValue(T value1, T value2)
    {
        addValue(new Tuple<>(value1, value2));
    }

    public final void addValue(Tuple<T, T> value)
    {
        if (uniqueValuesTmp.add(value.Item1) || uniqueValuesTmp.add(value.Item2))
        {
            this.uniqueValues = uniqueValuesTmp.toArray();
        }
        tableFields.put(value,
                (tableFields.containsKey(value)) ? tableFields.get(value) + 1 : 1);
        sum += 1;
    }

    public T[] getUniqueValues()
    {
        return (T[]) uniqueValues;
    }

    public int length()
    {
        return uniqueValues.length;
    }

    public long getField(int x, int y)
    {
        if (x < 0 || y < 0 || x >= uniqueValues.length || y >= uniqueValues.length)
        {
            return 0;
        }
        return getField(new Tuple<>(getUniqueValues()[x],
                getUniqueValues()[y]));
    }

    public long getField(Tuple<T, T> pair)
    {
        return (tableFields.containsKey(pair)) ? (long) tableFields.get(pair) : 0;
    }

    public long getSum()
    {
        return sum;
    }

}
