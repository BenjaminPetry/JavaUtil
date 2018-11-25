/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.events;

import de.bpetry.util.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Distributes events to listeners. Use it as a public final variable.
 *
 * @author Benjamin Petry
 * @param <T> the event type this handler throws
 */
public class EventHandler<T extends EventParam>
{

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private final List<Tuple<IListener<T>, Integer>> listOfListenersByPriority = new ArrayList<>();
    private final Map<IListener<T>, Integer> mapListenerToPriority = new HashMap<>();

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    public void add(IListener<T> listener)
    {
        add(listener, -1);
    }

    /**
     * Adds a listener with a specified priority. The higher the priority the
     * sooner it will be called.
     *
     * @param listener the listener
     * @param priority the listener's priority
     */
    public void add(IListener<T> listener, int priority)
    {
        synchronized (mapListenerToPriority)
        {
            mapListenerToPriority.put(listener, priority);
            updateListenerList();
        }
    }

    /**
     * Sets the priority of a listener that has been already added.
     *
     * @param listener the listener
     * @param priority the listener's priority
     */
    public void setPriority(IListener listener, int priority)
    {
        synchronized (mapListenerToPriority)
        {
            if (!mapListenerToPriority.containsKey(listener))
            {
                throw new IllegalArgumentException(
                        "Listener must be added to the EventHandler before its priority can be set.");
            }
            mapListenerToPriority.put(listener, priority);
            updateListenerList();
        }
    }

    public boolean contains(IListener listener)
    {
        synchronized (mapListenerToPriority)
        {
            return mapListenerToPriority.containsKey(listener);
        }
    }

    public void remove(IListener listener)
    {
        synchronized (mapListenerToPriority)
        {
            mapListenerToPriority.remove(listener);
            updateListenerList();
        }
    }

    private void updateListenerList()
    {
        listOfListenersByPriority.clear();
        mapListenerToPriority.keySet().forEach((listener) ->
        {
            listOfListenersByPriority.add(new Tuple<>(listener,
                    mapListenerToPriority.get(listener)));
        });

        listOfListenersByPriority.sort((a, b) ->
        {
            return b.Item2 - a.Item2;
        });

    }

    /**
     * Raises an event.
     *
     * @param event the event to raise
     * @return true, if the event was propagated without being stopped.
     * Otherwise false.
     */
    public boolean raise(T event)
    {
        return raise(null, event);
    }

    /**
     * Raises an event.
     *
     * @param sender the object that raised this event
     * @param event the event to raise
     * @return true, if the event was propagated without being stopped.
     * Otherwise false.
     */
    public boolean raise(Object sender, T event)
    {
        synchronized (mapListenerToPriority)
        {
            for (Tuple<IListener<T>, Integer> listener : listOfListenersByPriority)
            {
                if (!event.isPropagated())
                {
                    break;
                }
                listener.Item1.onEvent(sender, event);
            }
        }
        return event.isPropagated();
    }
}
