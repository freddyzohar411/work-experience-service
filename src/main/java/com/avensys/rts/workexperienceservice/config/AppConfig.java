package com.avensys.rts.workexperienceservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.avensys.rts.workexperienceservice.interceptor.AuditInterceptor;
import com.avensys.rts.workexperienceservice.interceptor.AuthInterceptor;

/**
 * @author Koh He Xiang
 * This class is used to configure the application.
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * This method is used to register the interceptors.
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuditInterceptor());
        registry.addInterceptor(authInterceptor);
    }

    /**
     * This method is used to configure the message source for internationalization.
     * These messages are used to display error messages to the user.
     * These messages are used to send back messages in response
     * @return
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource ();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}