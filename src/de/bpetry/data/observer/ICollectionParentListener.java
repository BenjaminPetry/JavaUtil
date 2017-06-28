/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * Listener that explicitely is called if a parent (T) should be changed in the child (E)
 * @author Benjamin Petry
 * @param <T> the parent's class (can be null if the parent has to be removed)
 * @param <E> the collection element's class
 */
public interface ICollectionParentListener<T,E>
{
    public void onParentChange(T parent, E item);
}
