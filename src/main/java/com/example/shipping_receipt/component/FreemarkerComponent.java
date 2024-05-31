package com.example.shipping_receipt.component;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class FreemarkerComponent {

    private final Configuration freemarkerConfig;

    public FreemarkerComponent(Configuration freemarkerConfig) {
        this.freemarkerConfig = freemarkerConfig;
    }

    public String processTemplateIntoString(String templateName, Object model) throws ProcessTemplateException {
        try {
            final Template template = freemarkerConfig.getTemplate(templateName);  
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException e) {
            throw new ProcessTemplateException(templateName + " not found");
        } catch (TemplateException e) {
            throw new ProcessTemplateException("Error processing template");
        }
    }

    public class ProcessTemplateException extends RuntimeException {
        protected ProcessTemplateException(String message) {
            super(message);
        }
    }

}
