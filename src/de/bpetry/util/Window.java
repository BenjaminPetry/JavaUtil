/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Opens an FXML window
 * @author Benjamin Petry
 * @param <T> the class of the window controller
 */
public class Window<T extends Initializable>
{
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private Stage window = null;
    private T windowController = null;
    private Scene scene = null;
    
    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------

    private static Image icon = null;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    public Window(String title, String fxmlFile, Class<T> controller)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(controller.getResource(fxmlFile));
            Parent root1 = (Parent) fxmlLoader.load();
            window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.initStyle(StageStyle.DECORATED);
            window.setTitle(title);
            if (icon != null)
            {
                window.getIcons().add(icon);
            }
            scene = new Scene(root1);
            window.setScene(scene);
            windowController = (T)fxmlLoader.getController();
            if (windowController instanceof WindowController)
            {
                ((WindowController)windowController).setWindow(this);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    //-------------------------------------------------------------------------
    ////////////////////////////  Init and Dispose ////////////////////////////
    //-------------------------------------------------------------------------

    public static void setIcon(String iconPath)
    {
        icon = new Image(iconPath);
    }
    
    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------

    public Stage getWindow()
    {
        return window;
    }

    public Scene getScene()
    {
        return scene;
    }
    
    public T getController()
    {
        return windowController;
    }

    
    
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    public void show()
    {
        
        Platform.runLater(() ->
        {
            this.window.show();
        });
    }
    
    public void hide()
    {
        
        Platform.runLater(() ->
        {
            this.window.hide();
        });
    }
    
}