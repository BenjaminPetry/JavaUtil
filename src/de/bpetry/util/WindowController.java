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
        if (this.window != null)
        {
            this.window.getWindow().setOnCloseRequest(null);
            this.window.getWindow().setOnHiding(null);
            this.window.getWindow().setOnHidden(null);
            this.window.getWindow().setOnShowing(null);
            this.window.getWindow().setOnShown(null);
        }

        this.window = window;

        if (this.window != null)
        {
            this.window.getWindow().setOnCloseRequest((event) ->
            {
                if (!onClose())
                {
                    event.consume();
                }
            });
            this.window.getWindow().setOnHiding((event) -> beforeHidden());
            this.window.getWindow().setOnHidden((event) -> onHide());
            this.window.getWindow().setOnShowing((event) -> beforeShowing());
            this.window.getWindow().setOnShown((event) -> onShow());
        }
    }

    //-------------------------------------------------------------------------
    ////////////////////////////  Protected Methods ///////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Is called just before the window is shown
     */
    protected void beforeShowing()
    {
    }

    /**
     * Is called when the window is shown
     */
    protected void onShow()
    {
    }

    /**
     * Is called before the window is hidden
     */
    protected void beforeHidden()
    {
    }

    /**
     * Is called when the window is hidden
     */
    protected void onHide()
    {
    }

    /**
     * Is called when the window is closed
     *
     * @return return false if you want to prevent window closing
     */
    protected boolean onClose()
    {
        return true;
    }

}
