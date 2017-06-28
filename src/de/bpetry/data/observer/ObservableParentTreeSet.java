/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * An observable tree set
 * @author Benjamin Petry
 */
public class ObservableParentTreeSet<T,E> extends ObservableTreeSet<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private ICollectionParentListener<T,E> parentAction = null;
    private T parent = null;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public ObservableParentTreeSet() {}
    
    public ObservableParentTreeSet(ICollectionParentListener<T,E> parentAction, T parent)
    {
        this.parent = parent;
        this.parentAction = parentAction;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    
    @Override
    protected void throwEvent(CollectionAction actionType, E item)
    {
        super.throwEvent(actionType, item);
        if (parentAction != null)
        {
            T par = (actionType.isAddAction()) ? parent : null;
            parentAction.onParentChange(par, item);
        }
    }
}
