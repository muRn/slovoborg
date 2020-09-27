package org.slovob.slovoborg.definition;

import org.springframework.core.convert.converter.Converter;

/*
 * The only reason for creating this converter is to avoid using default converter from String to Definition
 * because default converter tries to convert String to Long at some point and may fail
 */
public class StringToDefinitionConverter implements Converter<String, Definition> {
    @Override
    public Definition convert(String source) {
        return new Definition();
    }
}
