package helper;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import dataClasses.Address;
import dataClasses.University;

/**
 * Created by matthias on 31.05.2015.
 */
public class UniXmlParser extends XmlParser {
    private static UniXmlParser ourInstance;

    public static UniXmlParser getInstance(InputStream xml) {
        if(ourInstance == null)
            ourInstance = new UniXmlParser(xml);

        return ourInstance;
    }

    private UniXmlParser(InputStream xml) {
        super(xml);
    }

    public void parse(){
        /*
        <universities version=1.0>
            <name></name>
            <website></website>
            <address>
                <street></street>
                <housenoumber></housenoumber>
                <zip></zip>
                <region></region>
                <country></country>
	        </address>
         */

        //get root element ("UNIVERSITIES")
        Element root = document.getDocumentElement();
        NodeList children = root.getChildNodes();

        //iterate every child of root
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            //if child is not university continue because it's only a linebreak similar node
            if (child.getNodeName().equals("universitiy"))
                continue;

            University uni = new University();

            NodeList grandChildren = child.getChildNodes();
            for (int j = 0; j < grandChildren.getLength(); j++) {
                Node grandChild = grandChildren.item(j);

                switch(grandChild.getNodeName()){
                    case "name":
                        uni.setName(grandChild.getTextContent());
                        break;
                    case "website":
                        uni.setWebsite(grandChild.getTextContent());
                        break;
                    case "address":
                        Address adr = new Address();
                        NodeList greatGrandChildren = grandChild.getChildNodes();
                        for (int k = 0; k < greatGrandChildren.getLength(); k++) {
                            Node greatGrandChild = greatGrandChildren.item(k);

                            switch (greatGrandChild.getNodeName()){
                                case "street":
                                    adr.setStreet(greatGrandChild.getTextContent());
                                    break;
                                case "housenoumber":
                                    adr.setHouseNumber(Integer.parseInt(greatGrandChild.getTextContent()));
                                    break;

                                case "zip":
                                    adr.setZip(Integer.parseInt((greatGrandChild.getTextContent())));
                                    break;

                                case "region":
                                    adr.setRegion(greatGrandChild.getTextContent());
                                    break;

                                case "country":
                                    adr.setCountry(greatGrandChild.getTextContent());
                                    break;
                            }//END SWITCH(greatgrandChild)=ADRESS

                            uni.setAddress(adr);
                        }
                        System.out.println(uni);
                        break;
                }//END SWITCH(grandChild)
            }

        }

    }
}
