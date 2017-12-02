package com.surenpi.autotest.suite.parser;

import org.junit.Test;

import java.io.*;

public class XStreamSuiteParserTest
{
    private String current = "target/test-classes";
    @Test
    public void test()
    {
        try(InputStream in = new FileInputStream(new File(current,"test.xml")))
        {
            new XStreamSuiteParser().parse(in);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}