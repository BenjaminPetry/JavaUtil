/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * Describes a list event that extends the collection event with the position of the element where the event took place
 * @author Benjamin Petry
 */
public class ListEvent<E> extends CollectionEvent<E>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private final int index;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public ListEvent(CollectionAction action, E element, int index)
    {
        super(action, element);
        this.index = index;
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public int getIndex()
    {
        return index;
    }

}
