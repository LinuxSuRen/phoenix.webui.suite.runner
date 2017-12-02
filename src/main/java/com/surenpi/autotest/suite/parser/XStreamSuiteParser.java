package com.surenpi.autotest.suite.parser;

import com.surenpi.autotest.suite.runner.Suite;
import com.surenpi.autotest.suite.runner.SuiteAction;
import com.surenpi.autotest.suite.runner.SuitePage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.security.NoTypePermission;

import java.io.InputStream;

/**
 * @author suren
 */
public class XStreamSuiteParser implements SuiteParser
{
    @Override
    public Suite parse(InputStream suiteInputStream) throws Exception
    {
        XStream xStream = new XStream(new StaxDriver());
        xStream.addPermission(NoTypePermission.NONE);
        xStream.allowTypesByWildcard(new String[]{
                "com.surenpi.autotest.suite.runner.**"
        });

        xStream.aliasSystemAttribute(null, "class");

        xStream.alias("suite", Suite.class);
        xStream.aliasField("pageConfig", Suite.class, "xmlConfPath");
        xStream.useAttributeFor(Suite.class, "xmlConfPath");
        xStream.aliasField("pages", Suite.class, "pageList");
        xStream.useAttributeFor(Suite.class, "pagePackage");

        xStream.alias("page", SuitePage.class);
        xStream.aliasField("class", SuitePage.class, "pageCls");
        xStream.useAttributeFor(SuitePage.class, "pageCls");
        xStream.aliasField("actions", SuitePage.class, "actionList");

        xStream.alias("action", SuiteAction.class);
        xStream.useAttributeFor(SuiteAction.class, "field");
        xStream.useAttributeFor(SuiteAction.class, "name");

        Object obj = xStream.fromXML(suiteInputStream);

        return (Suite) obj;
    }
}