/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.observer;

/**
 * Describes a listener
 * @author Benjamin Petry
 * @param <T> Events that this listener can work with
 */
public interface IListener<T extends Event>
{
    public void onEvent(Object sender, T e);
}
