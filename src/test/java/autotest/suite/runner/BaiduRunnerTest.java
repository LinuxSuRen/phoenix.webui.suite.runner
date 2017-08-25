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

package autotest.suite.runner;

import org.suren.autotest.web.framework.annotation.AutoApplication;

import com.surenpi.autotest.suite.runner.SuiteRunner;
import com.surenpi.autotest.utils.ThreadUtil;

/**
 * @author <a href="http://surenpi.com">suren</a>
 * @since 1.1.0
 */
@AutoApplication
public class BaiduRunnerTest
{
    public static void main(String[] args) throws Exception
    {
        SuiteRunner suiteRunner = new SuiteRunner();
        suiteRunner.setApplicationClazz(BaiduRunnerTest.class);
        
        suiteRunner.runFromClasspathFile("baidu-runner.xls");
        
        ThreadUtil.silentSleep(3300);
    }
}
