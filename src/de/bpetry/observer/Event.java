/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.observer;

/**
 * Describes an Event
 * @author Benjamin Petry
 */
public class Event
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private String type;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    
    public Event()
    {
        this("");
    }
    
    public Event(String type)
    {
        this.type = type;
    }
    
    
    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------

    public String getType()
    {
        return type;
    }

}
