/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.util.Objects;

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

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.Item1);
        hash = 17 * hash + Objects.hashCode(this.Item2);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        return (Objects.equals(this.Item1, other.Item1) && Objects.equals(
                this.Item2, other.Item2));
    }

    @Override
    public String toString()
    {
        return "Tuple{" + "Item1=" + Item1 + ", Item2=" + Item2 + '}';
    }

}
