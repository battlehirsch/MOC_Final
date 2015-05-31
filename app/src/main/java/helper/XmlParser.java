package helper;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by matthias on 31.05.2015.
 */
public class XmlParser
{
    protected InputStream xmlInput;
    protected Document document;

    public XmlParser(InputStream xmlInput) {
        this.xmlInput = xmlInput;
        DocumentBuilderFactory docBuildFac = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuild = docBuildFac.newDocumentBuilder();
            document = docBuild.parse(xmlInput);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
