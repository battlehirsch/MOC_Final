package helper;

import android.content.Context;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import DataBase.DataBaseHandler;
import dataClasses.Course;
import dataClasses.University;

/**
 * Created by matthias rohrmoser on 01.06.2015.
 */
public class CourseXmlParser extends XmlParser {
    private static CourseXmlParser ourInstance;

    private Context context;
    private CourseXmlParser(InputStream xmlInput) {
        super(xmlInput);
    }

    public static CourseXmlParser getInstance(InputStream xml) {
        if(ourInstance == null)
            ourInstance = new CourseXmlParser(xml);

        return ourInstance;
    }

    public ArrayList<Course> parse(){

        ArrayList<Course> courses = new ArrayList<>();

        Element root = document.getDocumentElement();

        NodeList children = root.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if(! child.getNodeName().equals("course"))
                continue;

            NodeList grandChildren = child.getChildNodes();
            Course course = new Course();

            for (int j = 0; j < grandChildren.getLength(); j++) {
                Node grandChild = grandChildren.item(j);


                switch (grandChild.getNodeName()){
                    case "name":
                        course.setName(grandChild.getTextContent());
                        break;

                    case "type":
                        course.setType(grandChild.getTextContent());
                        break;

                    case "universities":
                        NodeList greatGrandChildren = grandChild.getChildNodes();
                        ArrayList<String> uniNames = new ArrayList<>();
                        for (int k = 0; k < greatGrandChildren.getLength(); k++) {
                            Node greatGrandChild = greatGrandChildren.item(k);

                            if(greatGrandChild.getNodeName().equals("University")){
                                uniNames.add(greatGrandChild.getTextContent());
                            }
                        }

                        ArrayList<University> unis = DataBaseHandler.getInstance(context).getUniversitiesByName(uniNames);
                        course.setUniversities(unis);
                        break;
                }


            }
            courses.add(course);
        }
        return courses;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
