/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * Describes a collection event
 * @author Benjamin Petry
 */
public class CollectionEvent<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private final CollectionAction action;
    private final E element;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public CollectionEvent(CollectionAction action, E element)
    {
        this.action = action;
        this.element = element;
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public CollectionAction getAction()
    {
        return action;
    }

    public E getElement()
    {
        return element;
    }
    
    public CollectionActionCategory getActionCategory()
    {
        return action.getCategory();
    }

}
