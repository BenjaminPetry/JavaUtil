/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 *
 * @author Benjamin Petry
 * @param <T> the parent's type
 * @param <E> the collection's element's type
 */
public class ObservableParentSetWrapper<T,E> extends ObservableSetWrapper<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private ICollectionParentListener<T,E> parentAction = null;
    private T parent = null;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public ICollectionParentListener<T, E> getParentAction()
    {
        return parentAction;
    }

    public void setParentAction(ICollectionParentListener<T, E> parentAction)
    {
        this.parentAction = parentAction;
    }

    public T getParent()
    {
        return parent;
    }

    public void setParent(T parent)
    {
        this.parent = parent;
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public static <T,E> ObservableParentSetWrapper<T,E> create(T parent, ICollectionParentListener<T, E> parentAction)
    {
        ObservableParentSetWrapper<T,E> wrapper = new ObservableParentSetWrapper<>();
        wrapper.setParent(parent);
        wrapper.setParentAction(parentAction);
        return wrapper;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    
    @Override
    protected void throwEvent(CollectionEvent<E> event)
    {
        super.throwEvent(event);
        if (parentAction != null && event.getElement() != null)
        {
            T par = (event.getActionCategory() == CollectionActionCategory.Add) ? parent : null;
            parentAction.onParentChange(par, event.getElement());
        }
    }
}
