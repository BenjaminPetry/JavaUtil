/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.file;

import de.bpetry.util.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

/**
 * Class that provides functionality to write data into a CSV file
 *
 * @author Benjamin Petry
 */
public class CSVWriter
{
    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------

    final public static String SEPARATOR_COMMA = ",";
    final public static String SEPARATOR_TAB = "\t";
    final public static String SEPARATOR_PIPE = "|";

    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private FileWriter writer;
    private File file;
    private String fieldSeperator = SEPARATOR_COMMA;
    private String lineSeparator = System.getProperty("line.separator");
    private int bytesWritten = 0;
    private boolean isCurrentLineEmpty = true;
    private boolean waitForNewLine = false;

    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    public CSVWriter(String filename)
    {
        this(new File(filename));
    }

    public CSVWriter(File f)
    {
        file = f;
    }
    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------

    public String getFieldSeperator()
    {
        return fieldSeperator;
    }

    public void setFieldSeperator(String seperator)
    {
        this.fieldSeperator = seperator;
    }

    public String getLineSeparator()
    {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator)
    {
        this.lineSeparator = lineSeparator;
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    public boolean open()
    {
        close();
        try
        {
            writer = new FileWriter(file);
            bytesWritten = 0;
            isCurrentLineEmpty = true;
            waitForNewLine = false;
        }
        catch (IOException ex)
        {
            Log.error("Could not open csv file for writing", ex);
            writer = null;
            return false;
        }
        return true;
    }

    public boolean close()
    {
        if (writer != null)
        {
            try
            {
                writer.close();
            }
            catch (IOException ex)
            {
                Log.error("Could not close csv file", ex);
                return false;
            }
            finally
            {
                writer = null;
                bytesWritten = 0;
                isCurrentLineEmpty = true;
                waitForNewLine = false;
            }
        }
        return true;
    }

    public boolean print(Collection<? extends Object> fields)
    {
        boolean result = true;
        for (Object field : fields)
        {
            result &= print(field.toString());
        }
        return result;
    }

    public boolean print(Object... fields)
    {
        boolean result = true;
        for (Object field : fields)
        {
            result &= print(field.toString());
        }
        return result;
    }

    public boolean print(Object fieldO)
    {
        String field = handleField(fieldO);
        if (!isCurrentLineEmpty)
        {
            field = this.fieldSeperator + field;
        }
        return write(field);
    }

    public boolean println(Collection<? extends Object> fields)
    {
        return print(fields) && newLine();
    }

    public boolean println(Object... fields)
    {
        return print(fields) && newLine();
    }

    public boolean println(Object field)
    {
        return print(field.toString()) && newLine();
    }

    /**
     * Adds a new line to the file (only if content is following)
     *
     * @return always true
     */
    public boolean newLine()
    {
        waitForNewLine = true;
        isCurrentLineEmpty = true;
        return true;
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Private Methods ////////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Writes a string into the currently open file. If the command newline has
     * been called before, it also adds a new line before continue to write.
     * Note: if the first part to write in the file starts with "ID", an
     * apostrophe is added before that to make it compatible with Excel.
     *
     * @param s the string to write
     * @return true if the writing operation was successful
     * @throws IllegalStateException in case the csv file is not opened yet.
     */
    private boolean write(String s)
    {
        if (writer == null)
        {
            throw new IllegalStateException("The csv file is not opened yet.");
        }
        try
        {
            if (waitForNewLine)
            {
                writer.write(lineSeparator);
                waitForNewLine = false;
            }
            if (bytesWritten == 0 && s.startsWith("ID"))
            {
                s = "'" + s;
            }
            writer.write(s);
            bytesWritten += s.length();
            isCurrentLineEmpty = false;
        }
        catch (IOException ex)
        {
            Log.error("Could not write into csv file", ex);
            return false;
        }
        return true;
    }

    /**
     * Converts an object into a string and surrounds it with double quotes if
     * it contains double quotes, the field- or line-separator or a line-break.
     *
     * @param fieldO the object to convert (can be null -> "")
     * @return the field as csv-string
     */
    private String handleField(Object fieldO)
    {
        String field = (fieldO == null) ? "" : fieldO.toString();
        boolean needToEscape = field.contains("\"") || field.contains(
                fieldSeperator) || field.contains(lineSeparator)
                || field.contains("\n");
        if (needToEscape)
        {
            field = "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
