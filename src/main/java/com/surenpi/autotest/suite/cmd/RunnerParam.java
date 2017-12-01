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

package com.surenpi.autotest.suite.cmd;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试套件运行参数
 * @author suren
 */
public class RunnerParam
{
	@Parameter(names = "-list", description = "列出所有的测试套件")
	public boolean listRunners = false;
	
	@Parameter(names = "-runners", description = "指定运行的测试套件列表")
	public List<String> runners = new ArrayList<>();
	
	@Parameter(names = "-download", description = "下载驱动")
	public boolean download;
	
	@Parameter(names = "-download-driver", description = "指定要下载的浏览器驱动以及版本号，例如：chrome.57")
	public String downloadDriver;
	
	@Parameter(names = "-download-dir", description = "指定下载后的目录")
	public String downloadDir;
	
	@Parameter(names = "-compile", description = "自动编译Page类")
	public boolean compile = true;

	@Parameter(names = "-compile-src", description = "自动编译Page源码目录")
	public String compileSrcDir = "generatedSrc";

    @Parameter(names = "-compile-dst", description = "自动编译Page类目录")
	public String compileDstDir = "generatedDst";
	
	@Parameter(names = "-gui", description = "启动GUI界面")
	public boolean isGuiMode = false;
	
	@Parameter(names = "-inter", description = "使用交换命令")
	public boolean interactive = false;
	
	@Parameter(names = "-help", description = "查看命令参数说明")
	public boolean isHelp = false;
}
