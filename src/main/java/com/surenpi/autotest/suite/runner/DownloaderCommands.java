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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;
import org.suren.autotest.webdriver.downloader.DriverDownloader;
import org.suren.autotest.webdriver.downloader.DriverMapping;

/**
 * @author suren
 * @date 2017年5月5日 下午10:14:47
 */
@Component
public class DownloaderCommands implements CommandMarker
{
	@CliCommand(value = { "demo" }, help = "just a demo command")
	public String demo()
	{
		return "demo";
	}
	
	@CliCommand(value = {"download"}, help = "下载驱动")
	public String download(
			@CliOption(key = {"browser"}, mandatory = true, help = "浏览器") final String browser,
			@CliOption(key = {"version"}, mandatory = true, help = "版本") final String ver,
			@CliOption(key = {"os"}, specifiedDefaultValue = "win32", help = "操作系统：win32、linux、mac") final String os,
			@CliOption(key = {"arch"}, specifiedDefaultValue = "32", help = "64或者32位") final String arch)
					throws FileNotFoundException, MalformedURLException, IOException
	{
		String url = url(browser, ver, os, arch);
		System.out.println("找到驱动地址" + url);
		
		return new DriverDownloader().getLocalFilePath(new URL(url));
	}
	
	@CliCommand(value = {"url"}, help = "获取驱动地址")
	public String url(
			@CliOption(key = {"browser"}, mandatory = true, help = "浏览器") final String browser,
			@CliOption(key = {"version"}, mandatory = true, help = "版本") final String ver,
			@CliOption(key = {"os"}, specifiedDefaultValue = "win32", help = "操作系统：win32、linux、mac") final String os,
			@CliOption(key = {"arch"}, specifiedDefaultValue = "32", help = "64或者32位") final String arch)
	{
		DriverMapping driverMapping = new DriverMapping();
		driverMapping.init();
		String url = driverMapping.getUrl(browser, ver, os, arch);
		return url;
	}
}
