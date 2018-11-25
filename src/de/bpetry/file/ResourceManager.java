/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.file;

import de.bpetry.util.Log;
import de.bpetry.util.Util;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * Provides easy access to resource files
 *
 * @author Benjamin Petry
 */
public class ResourceManager
{
    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------

    public static String resourcePath = "";

    //-------------------------------------------------------------------------
    ////////////////////////////  Init and Dispose ////////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Initializes the resource manager
     *
     * @param path the path where the resources are (package name in path
     * writing) Example: de.bpetry.util -> de/bpetry/util/
     */
    public static void init(String path)
    {
        resourcePath = Util.normalizePath(path);
        if (!resourcePath.startsWith("/"))
        {
            resourcePath = "/" + resourcePath;
        }
        Log.info("ResourceManager initialized");
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Retrieves a resource
     *
     * @param filename the name of the file
     * @return the URL that leads to the resource file
     */
    public static URL get(String filename)
    {
        return ResourceManager.class.getResource(resourcePath + filename);
    }

    /**
     * Retrieves the path to the file using URI of the URL of get()
     *
     * @param filename the name of the file
     * @return the path or null if the URI threw an exception
     */
    public static String getPath(String filename)
    {
        try
        {
            return ResourceManager.get(filename).toURI().toString();
        }
        catch (URISyntaxException ex)
        {
            Log.error("Could not retrieve URI for file " + filename, ex);
        }
        return null;
    }

    /**
     * Retrieves a resource as stream (might not always work!)
     *
     * @param filename the name of the file
     * @return the input stream or null if opening the resource as stream
     * failed.
     */
    public static Reader read(String filename)
    {
        InputStream is = ResourceManager.class.getResourceAsStream(
                resourcePath + filename);
        return (is == null) ? null : new InputStreamReader(is);
    }

    /**
     * Retrieves an image from the resources
     *
     * @param filename name of the image
     * @return the image or null if the image could not be retrieved
     */
    public static Image getImage(String filename)
    {
        String path = ResourceManager.getPath(filename);
        return (path == null) ? null : new Image(path);
    }
}
