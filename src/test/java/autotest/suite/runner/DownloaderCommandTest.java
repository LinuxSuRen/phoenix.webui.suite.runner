///*
// * Copyright 2002-2007 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package autotest.suite.runner;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.springframework.shell.Bootstrap;
//import org.springframework.shell.core.CommandResult;
//import org.springframework.shell.core.JLineShellComponent;
//
///**
// * @author suren
// * @date 2017年5月5日 下午10:19:53
// */
//public class DownloaderCommandTest
//{
//	@Test
//	public void demoTest()
//	{
//		Bootstrap bootstrap = new Bootstrap();
//		
//		JLineShellComponent shell = bootstrap.getJLineShellComponent();
//		shell.executeCommand("help");
//		
//		CommandResult cmdRes = shell.executeCommand("demo");
//		assertEquals(true, cmdRes.isSuccess());
//		assertEquals("demo", cmdRes.getResult());
//	}
//}
