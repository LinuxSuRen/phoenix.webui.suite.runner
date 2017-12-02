package com.surenpi.autotest.suite.util;

import java.io.File;

/**
 * @author suren
 */
public class SuiteXmlFilter extends XmlFileFilter
{
    public static final String ROOT_NAME = "suite";

    @Override
    public boolean accept(File file, String subFileName)
    {
        return super.accept(file, subFileName) &&
                hasRoot(file, subFileName, ROOT_NAME);
    }
}