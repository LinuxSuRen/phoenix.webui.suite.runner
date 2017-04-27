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

package com.surenpi.autotest.suite.runner;

import java.io.ByteArrayInputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author suren
 * @date 2017年4月27日 下午9:20:19
 */
public abstract class SuiteUtil
{
	/**
	 * 判断是否为suite的xml配置
	 * @param content
	 * @return
	 */
	public static boolean isSuiteXml(byte[] content)
	{
		SAXReader reader = new SAXReader();
		try
		{
			Document doc = reader.read(new ByteArrayInputStream(content));
			Element rootEle = doc.getRootElement();
			
			String rootEleName = rootEle.getName();
			
			return "suite".equals(rootEleName);
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
}
