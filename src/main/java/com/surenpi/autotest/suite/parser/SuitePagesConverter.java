package com.surenpi.autotest.suite.parser;

import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.mapper.Mapper;

public class SuitePagesConverter extends CollectionConverter
{
    public SuitePagesConverter(Mapper mapper)
    {
        super(mapper);
    }
}