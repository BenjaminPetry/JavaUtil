/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data.observer;

/**
 * Listens to events of a collection
 * @author Benjamin Petry
 * @param <E> the collection item's class
 */
public interface ICollectionListener<E>
{
    public void onEvent(CollectionAction action, E item);
}
