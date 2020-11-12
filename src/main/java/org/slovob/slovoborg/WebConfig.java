package org.slovob.slovoborg;

import org.slovob.slovoborg.definition.StringToDefinitionConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
     * The only reason for registering converter here is to avoid using default converter from String to Definition
     * because default converter tries to convert String to Long at some point and may fail
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDefinitionConverter());
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login"); // it would work even without setViewName
        // but explicit > implicit
    }
}
