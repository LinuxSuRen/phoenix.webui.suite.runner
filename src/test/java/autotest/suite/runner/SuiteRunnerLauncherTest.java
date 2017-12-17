package autotest.suite.runner;

import com.surenpi.autotest.suite.SuiteRunnerLauncher;
import org.junit.Test;

import java.io.IOException;

public class SuiteRunnerLauncherTest
{
    @Test
    public void runners() throws IOException
    {
        SuiteRunnerLauncher.main(new String[]{
                "-runners",
                "target/test-classes/test.xml"});
    }
}