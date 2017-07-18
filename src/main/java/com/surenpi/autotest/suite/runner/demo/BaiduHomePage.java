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

package com.surenpi.autotest.suite.runner.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.suren.autotest.web.framework.selenium.WebPage;

import com.surenpi.autotest.webui.ui.Text;

/**
 * @author suren
 * @date 2017年4月27日 下午10:03:36
 */
@Component
public class BaiduHomePage extends WebPage
{
	@Autowired
	private Text kw;

	public Text getKw()
	{
		return kw;
	}

	public void setKw(Text kw)
	{
		this.kw = kw;
	}
}
