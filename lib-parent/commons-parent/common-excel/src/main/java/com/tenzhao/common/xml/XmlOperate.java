package com.tenzhao.common.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

public class XmlOperate {

	/**
	 * <pre>
	 *   方法描述:根据ID从xml文件获取某个元素
	 * </pre>
	 * 
	 * @author chenxj
	 * @Date 2014-5-12 下午4:51:24
	 * @param id
	 * @return
	 * @throws FactoryConfigurationError
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Element getElement(String id, String xmlFile) throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException, DocumentException {
		Document doc = getXmlDocument(xmlFile);
		Element root = doc.getRootElement();
		Element e = root.elementByID(id);
		return e;
	}

	public static Document getXmlDocument(String configFileName) throws FactoryConfigurationError, ParserConfigurationException, SAXException, IOException, DocumentException {

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(configFileName));
		return document;
	}

}
