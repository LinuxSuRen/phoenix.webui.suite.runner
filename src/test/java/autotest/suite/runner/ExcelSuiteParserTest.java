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

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import com.surenpi.autotest.suite.parser.ExcelSuiteParser;
import com.surenpi.autotest.suite.parser.SuiteParser;
import com.surenpi.autotest.suite.runner.Suite;


/**
 * @author <a href="http://surenpi.com">suren</a>
 * @since 1.1.0
 */
public class ExcelSuiteParserTest
{
    @Test
    public void parse() throws Exception
    {
        try(InputStream input = ExcelSuiteParserTest.class.getClassLoader().getResourceAsStream("baidu-runner.xls"))
        {
            SuiteParser suiteParser = new ExcelSuiteParser();
            
            Suite suite = suiteParser.parse(input);
            
            Assert.assertNotNull(suite);
        }
    }
}
