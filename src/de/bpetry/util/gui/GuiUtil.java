/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util.gui;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Utility functions for GUI related issues
 *
 * @author Benjamin Petry
 */
public class GuiUtil
{

    /**
     * Makes a TextField numeric From:
     * https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
     *
     * @param field the text field
     */
    public static void makeTextFieldNumeric(TextField field)
    {
        // force the field to be numeric only
        field.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) ->
                {
                    if (!newValue.matches("\\d*"))
                    {
                        field.setText(newValue.replaceAll("[^\\d.]", ""));
                    }
                });
    }

    /**
     * Sets all items inside a comboBox to enumeration values
     *
     * @param box the combo box
     * @param arrayOfEnumeration the enumeration array
     */
    public static void enumComboBox(ComboBox box, Enum[] arrayOfEnumeration)
    {
        box.getItems().clear();
        for (Enum type : arrayOfEnumeration)
        {
            box.getItems().add(type);
        }
    }
}
