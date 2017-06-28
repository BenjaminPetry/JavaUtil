/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * Describes actions that may be executed on collections
 * @author Benjamin Petry
 */
public enum CollectionAction
{
    Add,
    AddAll,
    Remove,
    Clear;
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    
    public boolean isAddAction()
    {
        return this == Add || this == AddAll;
    }
    
    public boolean isRemoveAction()
    {
        return this == Remove || this == AddAll;
    }
}
