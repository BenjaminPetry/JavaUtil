/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.util.gui;

/**
 * Describes the potential type of a URL
 *
 * @author Benjamin Petry
 */
public enum URLType
{

    URL("URL"), // normal URL
    PDF("PDF file"), // pdf
    DOC("Word file"), // doc
    XLS("Excel file") // excel
    ;
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    private String s;

    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------
    URLType(String s)
    {
        this.s = s;
    }

    //-------------------------------------------------------------------------
    //////////////////////  Parent Methods Implementation /////////////////////
    //-------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return s;
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    public static URLType getType(String url)
    {
        String tmpUrl = url.toLowerCase();
        if (tmpUrl.endsWith(".pdf") || tmpUrl.contains("type=pdf"))
        {
            return URLType.PDF;
        }
        else if (tmpUrl.endsWith(".doc") || tmpUrl.endsWith(".docx"))
        {
            return URLType.DOC;
        }
        else if (tmpUrl.endsWith(".xsl") || tmpUrl.endsWith(".xlsx"))
        {
            return URLType.XLS;
        }
        return URLType.URL;
    }
}
