/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

/**
 * Describes a tuple
 *
 * @author Benjamin Petry
 * @param <U> class of the first item
 * @param <V> class of the second item
 */
public class Tuple<U, V>
{

    public U Item1 = null;
    public V Item2 = null;

    public Tuple(U item1, V item2)
    {
        this.Item1 = item1;
        this.Item2 = item2;
    }
}
