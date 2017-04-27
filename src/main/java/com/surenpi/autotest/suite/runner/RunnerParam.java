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

import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

/**
 * 测试套件运行参数
 * @author suren
 * @date 2017年4月26日 下午5:01:34
 */
public class RunnerParam
{
	@Parameter(names = "-list", description = "列出所有的测试套件")
	public boolean listRunners = false;
	
	@Parameter(names = "-runners", description = "指定运行的测试套件列表")
	public List<String> runners = new ArrayList<String>();
	
	@Parameter(names = "-download-driver", description = "指定要下载的浏览器驱动以及版本号，例如：chrome.57")
	public String downloadDriver;
	
	@Parameter(names = "-download-dir", description = "指定下载后的目录")
	public String downloadDir;
	
	@Parameter(names = "-gui", description = "启动GUI界面")
	public boolean isGuiMode = false;
}
