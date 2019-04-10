package com.ayu.growing.utils;


import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author Eric
 * @date 2018/6/13 10:51
 */
public class XmlUtils {

    public static final String DOCTYPE = "<!DOCTYPE";
    public static final String ENTITY  = "<!ENTITY";

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        for (String key : data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");
        try {
            writer.close();
        } catch (Exception ex) {
        }
        return output;
    }

    /**
     * Java对象转Xml字符串（序列化）
     *
     * @param object
     * @param rootName
     * @param rootType
     * @return
     */
    public static String bean2Xml(Object object, String rootName, Class<?> rootType) {
        //创建xStream对象
        XStream xStream = new XStream();
        //给指定类起别名
        xStream.alias(rootName, rootType);
        //暂时忽略掉一些新增的字段
        xStream.ignoreUnknownElements();
        //调用toXML 将对象转成字符串
        return xStream.toXML(object);
    }

    /**
     * Xml字符串转Java对象（反序列化）
     *
     * @param xml
     * @param rootName 根元素名称
     * @param rootType 根元素对应的Java类类型
     * @return
     */
    public static Object xml2Bean(String xml, String rootName, Class<?> rootType) {
        XStream xStream = new XStream();
        xStream.alias(rootName, rootType);
        //暂时忽略掉一些新增的字段
        xStream.ignoreUnknownElements();
        return xStream.fromXML(xml);
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        InputStream stream = null;
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setExpandEntityReferences(false);//禁用外部实体
            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
            stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx=0; idx<nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            return data;
        } catch ( Exception e) {
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return null;
    }

    /**
     * true表示包含 false表示不包含
     * @param strXML
     * @return
     */
    public static boolean hasExpandEntity(String strXML){
        if (StringUtils.isBlank(strXML)) {
            return false;
        }
        if (strXML.indexOf(DOCTYPE) != -1) {
            return true;
        }
        if (strXML.indexOf(ENTITY) != -1) {
            return true;
        }
        return false;
    }


}
