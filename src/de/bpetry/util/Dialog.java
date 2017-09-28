/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.util.Optional;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Shortcuts for displaying dialogs
 *
 * @author Benjamin Petry
 */
public class Dialog
{
    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------

    private static boolean dialogOpen = false;
    private static Optional<ButtonType> dialogResult;

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    // Help functions to display dialogs, such as errors, warnings or confirm-dialogs
    public static void error(String message)
    {
        dialog(Alert.AlertType.ERROR, "Error!", message);
    }

    public static void warning(String message)
    {
        dialog(Alert.AlertType.WARNING, "Warning", message);
    }

    public static void information(String message)
    {
        dialog(Alert.AlertType.INFORMATION, "Information", message);
    }

    public static boolean confirm(String message)
    {
        Optional<ButtonType> result = dialog(Alert.AlertType.CONFIRMATION,
                "Confirmation", message);
        return result != null && result.get() == ButtonType.OK;
    }

    public static Optional<ButtonType> dialog(Alert.AlertType type, String title,
            String message)
    {
        dialogOpen = true;
        dialogResult = null;
        if (Platform.isFxApplicationThread())
        {
            openDialog(type, title, message);
        }
        else
        {
            javafx.application.Platform.runLater(() ->
            {
                openDialog(type, title, message);
            });
            while (dialogOpen)
            {
                Util.sleep(20);
            }
        }
        return dialogResult;
    }

    //-------------------------------------------------------------------------
    //////////////////////////  Private Static Methods ////////////////////////
    //-------------------------------------------------------------------------
    private static void openDialog(Alert.AlertType type, String title,
            String message)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        dialogResult = alert.showAndWait();
        dialogOpen = false;
    }
}
