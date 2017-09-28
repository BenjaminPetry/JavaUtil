/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.config;

import de.bpetry.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads data from a configuration file
 *
 * @author Benjamin Petry
 */
public class Config
{

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------       
    private Properties p = null;
    private File file = null;
    private boolean hasToBeUpdated = false;

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public Config()
    {
        super();
        p = getAlphabeticalSortedProperties();
        update();
    }

    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------
    public File getFile()
    {
        return file;
    }

    public String getPath(String key)
    {
        String path = get(key);
        if (path == null)
        {
            return "";
        }
        return Util.normalizePath(path);
    }

    public double getDouble(String key)
    {
        return Double.parseDouble(get(key));
    }

    public int getInt(String key)
    {
        return Integer.parseInt(get(key));
    }

    public boolean getBool(String key)
    {
        return Boolean.parseBoolean(get(key));
    }

    public long getLong(String key)
    {
        return Long.parseLong(get(key));
    }

    public boolean isNull(String key)
    {
        return get(key).equals("null");
    }

    public String get(String key)
    {
        return p.getProperty(key);
    }

    public void set(String key, String value)
    {
        p.setProperty(key, value);
    }

    public void setNull(String key)
    {
        set(key, "null");
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    public void load(File f)
    {
        this.file = f;
        if (p != null)
        {
            p.clear();
        }
        if (f.exists())
        {
            try (InputStream is = new FileInputStream(f))
            {
                p.loadFromXML(is);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        }
        update();
        afterLoad();
    }

    public void save()
    {
        if (file == null)
        {
            throw new IllegalStateException(
                    "The save() method can only be called after loading a file.");
        }
        save(file);
    }

    public void save(File file)
    {
        this.file = file;
        if (!beforeSave())
        {
            return;
        }
        try (OutputStream is = new FileOutputStream(file))
        {
            p.storeToXML(is, getDescription());
        }
        catch (IOException ex)
        {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
        afterSave();
    }

    //-------------------------------------------------------------------------
    ////////////////////////////  Protected Methods ///////////////////////////
    //-------------------------------------------------------------------------
    protected void checkDefaultProperty(String key, String value)
    {
        if (!p.containsKey(key))
        {
            p.setProperty(key, value);
            hasToBeUpdated = true;
        }
    }

    /**
     * Is called after the property file is loaded and should check if for new
     * keys and set them to default values. Use checkDefaultProperty() to check
     * if the properties are set. It will automatically save the new values into
     * the file
     *
     * @see Config.checkDefaultProperty()
     */
    protected void checkDefaultProperties()
    {
    }

    protected void afterLoad()
    {
    }

    protected boolean beforeSave()
    {
        return true;
    }

    protected void afterSave()
    {
    }

    /**
     * For the XML file
     *
     * @return the description of this property file
     */
    protected String getDescription()
    {
        return "Default Config File";
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Private Methods ////////////////////////////
    //-------------------------------------------------------------------------
    private void update()
    {
        hasToBeUpdated = false;

        checkDefaultProperties();

        if (hasToBeUpdated && file != null)
        {
            save();
        }
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    public static Properties getAlphabeticalSortedProperties()
    {
        return new Properties()
        {
            //-------------------------------------------------------------------------
            //////////////////////  Parent Methods Implementation /////////////////////
            //-------------------------------------------------------------------------

            @Override
            public synchronized Enumeration<Object> keys()
            {
                Set<Object> tmpSet = new TreeSet<>(super.keySet());
                for (Object s : tmpSet)
                {
                    System.out.println(s.toString());
                }
                return Collections.enumeration(tmpSet);
            }

            @Override
            public Set<Object> keySet()
            {
                return Collections.unmodifiableSet(new TreeSet<Object>(
                        super.keySet()));
            }

            @Override
            public Set<Map.Entry<Object, Object>> entrySet()
            {
                Set<Map.Entry<Object, Object>> tmpSet = new LinkedHashSet<>();
                TreeMap<String, Map.Entry<Object, Object>> tmpMap = new TreeMap<>();
                for (Map.Entry<Object, Object> tmp : super.entrySet())
                {
                    tmpMap.put(tmp.getKey().toString(), tmp);
                }
                for (String key : tmpMap.keySet())
                {
                    Map.Entry<Object, Object> obj = tmpMap.get(key);
                    tmpSet.add(obj);
                }
                return tmpSet;
            }

            @Override
            public Set<String> stringPropertyNames()
            {
                Set<String> tmpSet = new TreeSet<>();
                for (Object key : super.keySet())
                {
                    tmpSet.add(key.toString());
                }
                return tmpSet;
            }

        };
    }

}
