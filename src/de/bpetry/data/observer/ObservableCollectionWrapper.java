/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Benjamin Petry
 * @param <E> the collection's element's type
 * @param <U> the collection's type
 */
public class ObservableCollectionWrapper<E> implements Collection<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private Collection<E> instance;
    private ICollectionListener<E> action = null;

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public ObservableCollectionWrapper()
    {
        instance = defaultCollection();
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public ICollectionListener<E> getAction()
    {
        return action;
    }

    public void setAction(ICollectionListener<E> action)
    {
        this.action = action;
    }

    public Collection<E> getCollection()
    {
        return instance;
    }
    
    public void setCollection(Collection<E> e)
    {
        if (e == null)
        {
            instance = defaultCollection();
            return;
        }
        instance = (e instanceof ObservableCollectionWrapper) ? ((ObservableCollectionWrapper)e).getCollection() : e;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    
    @Override
    public int size()
    {
        return instance.size();
    }

    @Override
    public boolean isEmpty()
    {
        return instance.isEmpty();
    }

    @Override
    public boolean contains(Object o)
    {
        return instance.contains(o);
    }

    @Override
    public Iterator<E> iterator()
    {
        return instance.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return instance.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a)
    {
        return instance.toArray(a);
    }

    @Override
    public boolean containsAll(
            Collection<?> c)
    {
        return instance.containsAll(c);
    }

    @Override
    public boolean retainAll(
            Collection<?> c)
    {
        return instance.retainAll(c);
    }

    
    @Override
    public boolean addAll(
            Collection<? extends E> c)
    {
        if (instance.addAll(c))
        {
            for (E tmp : c)
            {
                throwEvent(CollectionAction.AddAll, tmp);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(
            Collection<?> c)
    {
        for (Object tmp : c)
        {
            if (instance.contains(tmp))
            {
                throwEvent(CollectionAction.RemoveAll, (E)tmp);
            }
        }
        return instance.removeAll(c);
    }

    
    @Override
    public void clear()
    {
        for (E tmp : this)
        {
            throwEvent(CollectionAction.Clear, tmp);
        }
        instance.clear();
    }
    
    @Override
    public boolean add(E e)
    {
        if(instance.add(e))
        {
            throwEvent(CollectionAction.Add, e);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o)
    {
        if(instance.remove(o))
        {
            throwEvent(CollectionAction.Remove, (E)o);
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return this.instance.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.instance.equals(obj);
    }

    @Override
    public String toString()
    {
        return instance.toString();
    }
    
    //-------------------------------------------------------------------------
    ////////////////////////////  Protected Methods ///////////////////////////
    //-------------------------------------------------------------------------

    protected void throwEvent(CollectionAction actionType, E item)
    {
        if (action != null && item != null)
        {
            action.onEvent(actionType, item);
        }
    }
    
    protected Collection<E> defaultCollection()
    {
        return new ArrayList<E>();
    }
    
}
