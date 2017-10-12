/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import javafx.fxml.Initializable;

/**
 * Basic class for a window controller
 *
 * @author Benjamin Petry
 */
public abstract class WindowController implements Initializable
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------

    private Window window;

    public Window getWindow()
    {
        return window;
    }

    public void setWindow(Window window)
    {
        this.window = window;
    }

}
