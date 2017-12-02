package com.surenpi.autotest.suite.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author suren
 */
public abstract class XmlUtils
{
    public static boolean hasRoot(File file, String name)
    {
        SAXReader reader = new SAXReader();
        try(InputStream in = new FileInputStream(file))
        {
            Document doc = reader.read(in);
            Element rootEle = doc.getRootElement();
            String rootEleName = rootEle.getName();

            return rootEleName.equals(name);
        }
        catch (Exception e)
        {}

        return false;
    }
}