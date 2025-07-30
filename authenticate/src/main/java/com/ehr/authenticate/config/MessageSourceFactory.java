package com.ehr.authenticate.config;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class MessageSourceFactory {

    private final MessageSource messageSource;
    private final String language;
    public final Set<String> supportedLanguages = Set.of("en", "hi", "ar", "es", "bn", "or");
    public MessageSourceFactory(HttpServletRequest request) {
        this.language = request.getHeader("lang");
        String languageCode = (this.language != null) ? this.language : "en"; // Default to English
if(!supportedLanguages.contains(languageCode)) {
	languageCode="en";
}
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages_" + languageCode);
        ms.setDefaultEncoding("UTF-8");
        ms.setFallbackToSystemLocale(false);
        ms.setCacheSeconds(5);
        this.messageSource = ms;
    }

    public String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    public String getMessage(String key) {
        return getMessage(key, null);
    }
    public String getLanguage() {
        return this.language;
    }
}
