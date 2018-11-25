/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.events;

/**
 * Describes Parameters of an event
 *
 * @author Benjamin Petry
 */
public class EventParam
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------

    private boolean propagation = true;

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Variables ///////////////////////////
    //-------------------------------------------------------------------------
    public final String Type;

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public EventParam()
    {
        this("");
    }

    public EventParam(String type)
    {
        this.Type = type;
    }

    //-------------------------------------------------------------------------
    //////////////////////////////  Getter & Setter ///////////////////////////
    //-------------------------------------------------------------------------
    public boolean isPropagated()
    {
        return this.propagation;
    }

    public void stopPropagation()
    {
        this.propagation = false;
    }

}
