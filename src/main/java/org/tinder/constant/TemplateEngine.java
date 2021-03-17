package org.tinder.constant;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class TemplateEngine {
    private final String basePath = "src\\main\\resources\\WEB-INF\\";
    private final Configuration config;


    private TemplateEngine(final String path) throws IOException {
        this.config = new Configuration(Configuration.VERSION_2_3_30) {{
            setDirectoryForTemplateLoading(new File(basePath.concat(path)));
            setDefaultEncoding(String.valueOf(StandardCharsets.UTF_8));
            setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            setWrapUncheckedExceptions(true);
            setLogTemplateExceptions(false);
        }};
    }

    public static TemplateEngine folder(String path) throws IOException {
        return new TemplateEngine(path);
    }

    public void render(String template, HashMap<String, Object> data, HttpServletResponse resp) throws IOException, TemplateException {
        try (PrintWriter pw = resp.getWriter()) {
            this.config.getTemplate(template).process(data, pw);
        }
    }
}
