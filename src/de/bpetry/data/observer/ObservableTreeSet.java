/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

import java.util.Collection;
import java.util.TreeSet;

/**
 * An observable tree set
 * @author Benjamin Petry
 * @param <E> the collection element's class
 */
public class ObservableTreeSet<E> extends TreeSet<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private ICollectionListener<E> action = null;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public ObservableTreeSet() {}
    
    public ObservableTreeSet(ICollectionListener<E> action)
    {
        this.action = action;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    
    @Override
    public  boolean addAll(Collection<? extends E> c)
    {
        if (super.addAll(c))
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
    public void clear()
    {
        for (E tmp : this)
        {
            throwEvent(CollectionAction.Clear, tmp);
        }
        super.clear();
    }
    
    @Override
    public boolean add(E item)
    {
        if(super.add(item))
        {
            throwEvent(CollectionAction.Add, item);
            return true;
        }
        return false;
    }
    
    
    @Override
    public boolean remove(Object item)
    {
        if(super.remove(item))
        {
            throwEvent(CollectionAction.Remove, (E)item);
            return true;
        }
        return false;
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Private Methods ////////////////////////////
    //-------------------------------------------------------------------------
    
    protected void throwEvent(CollectionAction actionType, E item)
    {
        if (action != null)
        {
            action.onEvent(actionType, item);
        }
    }
}
