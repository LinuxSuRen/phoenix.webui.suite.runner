package com.surenpi.autotest.suite.parser;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class SuitePageConverter implements Converter
{
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
    {

    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
    {
        return null;
    }

    @Override
    public boolean canConvert(Class type)
    {
        return false;
    }
}