package com.surenpi.autotest.suite.util;

import java.io.File;

/**
 * 检查是否为Page的配置文件
 * @author suren
 */
public class PageXmlFilter extends XmlFileFilter
{
    public static final String ROOT_NAME = "autotest";

    @Override
    public boolean accept(File file, String subFileName)
    {
        return super.accept(file, subFileName) &&
                hasRoot(file, subFileName, ROOT_NAME);
    }
}