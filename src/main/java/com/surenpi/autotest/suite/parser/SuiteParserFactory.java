/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.surenpi.autotest.suite.parser;

/**
 * @author <a href="http://surenpi.com">suren</a>
 * @since 1.1.0
 */
public abstract class SuiteParserFactory
{
    public static SuiteParser getInstance(String clsName)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException
    {
        Class<?> clazz = Class.forName(clsName);
        return (SuiteParser) clazz.newInstance();
    }

    public static SuiteParser newInstance(String fileName)
    {
        SuiteParser suiteParser = null;
        
        if(fileName.endsWith(".xml"))
        {
            suiteParser = new XmlSuiteParser();
        }
        else if(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))
        {
            suiteParser = new ExcelSuiteParser();
        }
        
        return suiteParser;
    }
}
