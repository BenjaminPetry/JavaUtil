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
import java.util.List;

/**
 *
 * @author Benjamin Petry
 * @param <E> the collection's element's type
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
        instance = (e instanceof ObservableCollectionWrapper) ? ((ObservableCollectionWrapper) e).getCollection() : e;
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
        int index = (this.getCollection() instanceof List) ? this.getCollection().size() : -1; // specially implemented for lists
        if (instance.addAll(c))
        {
            for (E tmp : c)
            {
                throwEvent(CollectionAction.AddAll, tmp, index);
                index += (index == -1) ? 0 : 1;
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
            if (instance.contains((E) tmp))
            {
                throwEvent(CollectionAction.RemoveAll, (E) tmp,
                        getIndex((E) tmp));
            }
        }
        return instance.removeAll(c);
    }

    @Override
    public void clear()
    {
        for (E tmp : this)
        {
            throwEvent(CollectionAction.Clear, tmp, getIndex(tmp));
        }
        instance.clear();
    }

    @Override
    public boolean add(E e)
    {
        if (instance.add(e))
        {
            throwEvent(CollectionAction.Add, e, getIndex(e));
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object o)
    {
        List<E> list = (this.getCollection() instanceof List) ? ((List<E>) this.getCollection()) : null;
        int index = (list != null) ? list.indexOf(o) : -1; // specially implemented for lists
        if (instance.remove(o))
        {
            throwEvent(CollectionAction.Remove, (E) o, index);
            if (list != null)
            {
                for (int n = index; n < list.size(); n++)
                {
                    throwEvent(CollectionAction.Update, list.get(n), n);
                }
            }
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
    protected void throwEvent(CollectionAction action, E element, int index)
    {
        CollectionEvent event = (index == -1)
                ? new CollectionEvent(action, element)
                : new ListEvent(action, element, index);
        throwEvent(event);
    }

    protected void throwEvent(CollectionEvent<E> event)
    {
        if (action != null && event.getElement() != null)
        {
            action.onEvent(event);
        }
    }

    protected Collection<E> defaultCollection()
    {
        return new ArrayList<>();
    }

    private int getIndex(E object)
    {
        if (getCollection() instanceof List)
        {
            return ((List) getCollection()).indexOf(object);
        }
        return -1;
    }

}
