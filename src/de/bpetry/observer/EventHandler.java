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
    private boolean abort = false;
    
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
    
    public void abort()
    {
        abort = true;
    }
    
    public boolean throwEvent(T event)
    {
        return throwEvent(null, event);
    }
    
    public boolean throwEvent(Object sender, T event)
    {
        abort = false;
        synchronized(setOfListener)
        {
            for (IListener<T> listener : setOfListener)
            {
                listener.onEvent(sender, event);
                if (abort)
                {
                    break;
                }
            }
        }
        boolean tmp = abort;
        abort = false;
        return !tmp;
    }
}
