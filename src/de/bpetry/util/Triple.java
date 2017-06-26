/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

/**
 * Describes a triple
 *
 * @author Benjamin Petry
 * @param <U> class of the first item
 * @param <V> class of the second item
 * @param <T> class of the third item
 */
public class Triple<U, V, T> extends Tuple<U, V>
{

    public T Item3 = null;

    public Triple(U item1, V item2, T item3)
    {
        super(item1, item2);
        Item3 = item3;
    }
}
