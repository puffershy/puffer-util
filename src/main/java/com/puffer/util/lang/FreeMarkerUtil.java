package com.puffer.util.lang;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;

/**
 * FreeMarkerUtil工具类
 *
 * @author puffer
 * @date 2020年06月30日 14:56:48
 * @since 1.0.0
 */
@Slf4j
public class FreeMarkerUtil {

    private static final Configuration configuration = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    private static final StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();

    static {
        configuration.setDefaultEncoding(CharEncoding.UTF_8);
        configuration.setTemplateLoader(stringTemplateLoader);
    }

    /**
     * 增加内容模板
     *
     * @param templateCode
     * @param templateContent
     * @return
     * @author buyi
     * @date 2020年06月30日 16:35:42
     * @since 7.17.1
     */
    public static void addStringTemplate(String templateCode, String templateContent) throws IOException {
        stringTemplateLoader.putTemplate(templateCode, templateContent);
        configuration.removeTemplateFromCache(templateCode);
    }

    /**
     * 初始化模板
     *
     * @param templateCode
     * @param templateContent
     * @return freemarker.template.Template
     * @author buyi
     * @date 2020年07月01日 16:17:35
     * @since 7.17.1
     */
    public static Template instanceTemplate(String templateCode, String templateContent) throws IOException {
        try {
            configuration.getTemplate(templateCode);
            return configuration.getTemplate(templateCode);
        } catch (TemplateNotFoundException e) {
            log.info("模板不存在，则初始化模板：templateCode:" + templateCode + ",templateContent:" + templateContent);
        }

        return syncInstanceTemplate(templateCode, templateContent);
    }

    /**
     * 同步锁初始化模板
     *
     * @param templateCode
     * @param templateContent
     * @return freemarker.template.Template
     * @author buyi
     * @date 2020年07月01日 16:17:50
     * @since 7.17.1
     */
    public synchronized static Template syncInstanceTemplate(String templateCode, String templateContent) throws IOException {
        //如果模板不出在，则初始化
        addStringTemplate(templateCode, templateContent);
        return configuration.getTemplate(templateCode);
    }

    /**
     * 组装模板内容
     *
     * @param templateCode    模板编号
     * @param templateContent 模板内容
     * @param params          模板参数
     * @return java.lang.String
     * @author buyi
     * @date 2020年06月30日 16:41:04
     * @since 7.17.1
     */
    public static String process(String templateCode, String templateContent, Map<String, String> params) {
        try {
            Template template = instanceTemplate(templateCode, templateContent);
            StringWriter out = new StringWriter();
            template.process(params, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("template exception");
        }
    }

}
