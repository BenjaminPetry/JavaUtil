/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * An observable array list
 *
 * @author Benjamin Petry
 */
public class ObservableListWrapper<E> extends ObservableCollectionWrapper<E> implements List<E>
{

    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    @Override
    public void setCollection(Collection<E> c)
    {
        if (!(c instanceof List))
        {
            throw new IllegalArgumentException(
                    "ObservableListWrapper only accepts lists");
        }
        super.setCollection(c);
    }

    public List<E> getList()
    {
        return (List<E>) getCollection();
    }

    public void setList(List<E> s)
    {
        super.setCollection(s);
    }

    @Override
    public E get(int index)
    {
        return getList().get(index);
    }

    @Override
    public int indexOf(Object o)
    {
        return getList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return getList().lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator()
    {
        return getList().listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index)
    {
        return getList().listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex)
    {
        return getList().subList(fromIndex, toIndex);
    }

    @Override
    public boolean addAll(int index,
            Collection<? extends E> c)
    {
        int counter = index;
        if (getList().addAll(index, c))
        {
            for (E tmp : c)
            {
                throwEvent(new ListEvent(CollectionAction.AddAll, tmp, counter));
                counter++;
            }
            return true;
        }
        return false;
    }

    @Override
    public E set(int index, E element)
    {
        E oldElement = getList().set(index, element);
        throwEvent(new ListEvent(CollectionAction.SetRemoved, oldElement, index));
        throwEvent(new ListEvent(CollectionAction.SetInsert, element, index));
        return oldElement;
    }

    @Override
    public void add(int index, E element)
    {
        getList().add(index, element);
        throwEvent(new ListEvent(CollectionAction.Add, element, index));
    }

    @Override
    public E remove(int index)
    {
        E oldElement = getList().remove(index);
        throwEvent(new ListEvent(CollectionAction.Remove, oldElement, index));
        for (int n = index; n < getList().size(); n++)
        {
            throwEvent(CollectionAction.Update, getList().get(n), n);
        }
        return oldElement;
    }

    @Override
    protected Collection<E> defaultCollection()
    {
        return new ArrayList<>();
    }

    public void moveElement(E element, int indexNew)
    {
        int indexOld = getList().indexOf(element);
        if (indexOld == -1)
        {
            throw new IllegalArgumentException(
                    "Element is not in the list and hence, cannot be moved inside the list");
        }
        else if (indexOld == indexNew)
        {
            return;
        }
        int indexMin = Math.min(indexNew, indexOld);
        int indexMax = Math.max(indexNew, indexOld);
        int rotationDirection = (indexMax == indexOld) ? 1 : -1;
        Collections.rotate(this.getList().subList(indexMin, indexMax + 1),
                rotationDirection);
        for (int n = indexMin; n <= indexMax; n++)
        {
            throwEvent(new ListEvent(CollectionAction.Update,
                    this.getList().get(n), n));
        }
    }

}
