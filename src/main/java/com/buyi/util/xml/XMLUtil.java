package com.buyi.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.QNameMap;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.thoughtworks.xstream.io.xml.StaxWriter;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringWriter;

public class XMLUtil {
    /**
     * 实体转换成xml字符串
     *
     * @param obj
     * @return
     * @author buyi
     * @date 2017下午5:05:36
     * @since 1.0.0
     */
    public static String toXML(Object obj) {
        XStream xstream = XStreamFactory.instanceXStremStaxDriver();
        String xml = xstream.toXML(obj);
        // xstream的bug如果有下划线的别名，会变成"__"
        xml = xml.replaceAll("__", "_");
        return xml;
    }

    /**
     * 转换成xml，不带声明串
     *
     * @param obj
     * @return
     * @author buyi
     * @date 2017下午5:27:27
     * @since 1.0.0
     */
    public static String toXMLWithoutDeclare(Object obj) {
        XStream xstream = XStreamFactory.instanceXStremStaxDriver();

        QNameMap qmap = new QNameMap();
        qmap.setDefaultPrefix("");

        StaxWriter sw = null;
        try (StringWriter strWriter = new StringWriter()) {
            sw = new StaxWriter(qmap, new StaxDriver().getOutputFactory().createXMLStreamWriter(strWriter), false, true);
            xstream.marshal(obj, sw);
            sw.flush();
            return strWriter.toString().replaceAll("__", "_");
        } catch (XMLStreamException | IOException e) {
            // throw XStreamIOException.exp("convert xml fail", e);
            throw new RuntimeException(e);
        } finally {
            if (sw != null) {
                sw.close();
            }
        }
    }

    /**
     * 反序列化
     *
     * @param xml
     * @param clazz
     * @return
     * @author buyi
     * @date 2017下午5:27:44
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(String xml, Class<T> clazz) {
        // XStream xstream = new XStream(new DomDriver());
        XStream xstream = XStreamFactory.instanceXStreamDomDriver();
        xstream.processAnnotations(clazz);
        return (T) xstream.fromXML(xml);
    }

}
