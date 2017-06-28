/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes a observable set
 * @author Benjamin Petry
 * @param <E> the sets collection
 */
public class ObservableSetWrapper<E> extends ObservableCollectionWrapper<E> implements Set<E>
{
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public Set<E> getSet()
    {
        return (Set<E>)getCollection();
    }
    
    public void setSet(Set<E> s)
    {
        super.setCollection(s);
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    
    public static <E> ObservableSetWrapper<E> create(ICollectionListener<E> action)
    {
        ObservableSetWrapper<E> wrapper = new ObservableSetWrapper<>();
        wrapper.setAction(action);
        return wrapper;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------

    @Override
    public void setCollection(Collection<E> c)
    {
        if (!(c instanceof Set))
        {
            throw new IllegalArgumentException("ObservableSetWrapper only accepts sets");
        }
        super.setCollection(c);
    }
    
    @Override
    protected Collection<E> defaultCollection()
    {
        return new HashSet<>();
    }
}
