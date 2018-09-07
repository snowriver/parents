
package com.tenzhao.common.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml字符串操作类
 * @author chenxj
 */
public class XMLProperties {

	private static final Logger Log = LoggerFactory.getLogger(XMLProperties.class);

    private Document document;

    public Document getDocument() {
		return document;
	}


	public void setDocument(Document document) {
		this.document = document;
	}

	/**
     * Loads XML properties from a stream.
     *
     * @param in the input stream of XML.
     * @throws IOException if an exception occurs when reading the stream.
     */
    public XMLProperties(InputStream in) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        buildDoc(reader);
        reader.close();
        in.close();
    }

    public Element getRootElement(){
    	return document.getRootElement() ;
    }
    
    /**
     * Returns the value of the specified property.
     *
     * @param name the name of the property to get.
     * @return the value of the specified property.
     */
    public synchronized String getProperty(String name) {
    	return getProperty(name, true);
    }

    /**
     * Returns the value of the specified property.
     *
     * @param name the name of the property to get.
     * @param ignoreEmpty Ignore empty property values (return null)
     * @return the value of the specified property.
     */
    public synchronized String getProperty(String name, boolean ignoreEmpty) {
        String value = null ;
        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML hierarchy.
        Element element = document.getRootElement();
        for (String aPropName : propName) {
            element = element.element(aPropName);
            if (element == null) {
                return null;
            }
        }
        value = element.getTextTrim();
       return value;
    }

    /**
     * Returns the value of the attribute of the given property name or <tt>null</tt>
     * if it doesn't exist.
     *
     * @param name the property name to lookup - ie, "foo.bar"
     * @param attribute the name of the attribute, ie "id"
     * @return the value of the attribute of the given property or <tt>null</tt> if
     *      it doesn't exist.
     */
    public String getAttribute(String name, String attribute) {
        if (name == null || attribute == null) {
            return null;
        }
        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML hierarchy.
        Element element = document.getRootElement();
        for (String child : propName) {
            element = element.element(child);
            if (element == null) {
                // This node doesn't match this part of the property name which
                // indicates this property doesn't exist so return empty array.
                break;
            }
        }
        if (element != null) {
            // Get its attribute values
            return element.attributeValue(attribute);
        }
        return null;
    }

    /**
     * Returns a list of names for all properties found in the XML file.
     *
     * @return Names for all properties in the file
     */
    public List<String> getAllPropertyNames() {
    	List<String> result = new ArrayList<String>();
    	for (String propertyName : getChildPropertyNamesFor(document.getRootElement(), "")) {
    		if (getProperty(propertyName) != null) {
    			result.add(propertyName);
    		}
    	}
    	return result;
    }
    
    private List<String> getChildPropertyNamesFor(Element parent, String parentName) {
    	List<String> result = new ArrayList<String>();
    	for (Element child : (Collection<Element>) parent.elements()) {
    		String childName = new StringBuilder(parentName)
							.append(parentName.isEmpty() ? "" : ".")
							.append(child.getName())
							.toString();
    		if (!result.contains(childName)) {
	    		result.add(childName);
	    		result.addAll(getChildPropertyNamesFor(child, childName));
    		}
    	}
    	return result;
    }

    /**
     * Builds the document XML model up based the given reader of XML data.
     * @param in the input stream used to build the xml document
     * @throws java.io.IOException thrown when an error occurs reading the input stream.
     */
    private void buildDoc(Reader in) throws IOException {
        try {
            SAXReader xmlReader = new SAXReader();
            xmlReader.setEncoding("UTF-8");
            document = xmlReader.read(in);
        }
        catch (Exception e) {
            Log.error("Error reading XML properties", e);
            throw new IOException(e.getMessage());
        }
    }

    /**
     * Returns an array representation of the given Jive property. Jive
     * properties are always in the format "prop.name.is.this" which would be
     * represented as an array of four Strings.
     *
     * @param name the name of the Jive property.
     * @return an array representation of the given Jive property.
     */
    private String[] parsePropertyName(String name) {
        List<String> propName = new ArrayList<String>(5);
        StringTokenizer tokenizer = new StringTokenizer(name, ".");
        while (tokenizer.hasMoreTokens()) {
            propName.add(tokenizer.nextToken());
        }
        return propName.toArray(new String[propName.size()]);
    }
}
