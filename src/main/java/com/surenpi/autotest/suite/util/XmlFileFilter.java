package com.surenpi.autotest.suite.util;

import java.io.File;
import java.io.FilenameFilter;

/**
 * @author suren
 */
public class XmlFileFilter implements FilenameFilter
{
    @Override
    public boolean accept(File file, String subFileName)
    {
        return subFileName.endsWith(".xml");
    }

    public boolean hasRoot(File file, String subFileName, String rootName)
    {
        return XmlUtils.hasRoot(new File(file, subFileName), rootName);
    }
}