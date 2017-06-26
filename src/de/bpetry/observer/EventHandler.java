/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.observer;

import java.util.HashSet;
import java.util.Set;

/**
 * Distributes events to listener. Use it as a public final variable
 * @author Benjamin Petry
 * @param <T> the event type this handler throws
 */
public class EventHandler<T extends Event>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private final Set<IListener> setOfListener = new HashSet<>();
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public void add(IListener listener)
    {
        synchronized(setOfListener)
        {
            setOfListener.add(listener);
        }
    }
    
    public void remove(IListener listener)
    {
        synchronized(setOfListener)
        {
            setOfListener.remove(listener);
        }
    }
    
    public void throwEvent(T event)
    {
        synchronized(setOfListener)
        {
            for (IListener<T> listener : setOfListener)
            {
                listener.onEvent(event);
            }
        }
    }
}
