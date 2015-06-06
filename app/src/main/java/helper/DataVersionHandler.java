package helper;

import android.content.Context;
import android.content.res.Resources;

import com.example.norbe_000.moc_final.R;

import java.io.InputStream;
import java.util.ArrayList;

import helper.database.DataBaseHandler;
import dataClasses.Course;
import dataClasses.University;
import helper.interfaces.IResourceListener;
import helper.xml.CourseXmlParser;
import helper.xml.UniXmlParser;
import helper.xml.XmlRequester;

/**
 * Created by matthias rohrmoser on 01.06.2015.
 */
public class DataVersionHandler {
    private Context context;
    private IResourceListener listener;
    private UniXmlParser uniXmlParser;
    private CourseXmlParser courseXmlParser;


    public DataVersionHandler(IResourceListener listener, InputStream uniStream, InputStream courseStream) {
        this.listener = listener;

       uniXmlParser =  this.uniXmlParser.getInstance(uniStream);
       courseXmlParser = this.courseXmlParser.getInstance(courseStream);



    }


    public void fullProcedure(){
        if(! checkUniversityVersion())
            updateUniversity();

        if(! checkCourseVersion())
            updateCourse();
    }


    //region methods
    private boolean checkCourseVersion() {
        Resources resources = listener.getActivityResources();
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());

        System.out.printf("COURSE== fileVersion:%f @@@@ databaseVersion:%f\n\n", courseXmlParser.getFileVersion(),dbh.queryFileVersionByName("course"));
        return (courseXmlParser.getFileVersion() == dbh.queryFileVersionByName("course"));
    }

    private boolean checkUniversityVersion() {
        Resources resources = listener.getActivityResources();
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        return (uniXmlParser.getFileVersion() == dbh.queryFileVersionByName("university"));
    }

    public void updateCourse(){
        Resources resources = listener.getActivityResources();
        courseXmlParser.setContext(listener.getActivityContext());
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        ArrayList<Course> courses = courseXmlParser.parse();

        for(Course c : courses){
            dbh.createCourse(c);
            c.setId(dbh.queryCourseIdByObject(c));

            for(University u : c.getUniversities()){
                dbh.linkCourseToUni(c.getId(),u.getId());
            }
        }

        dbh.insertFileVersion("course", courseXmlParser.getFileVersion());
    }

    public void updateUniversity(){
        Resources resources = listener.getActivityResources();
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        ArrayList<University> unis = uniXmlParser.parse();
        for(University u : unis)
            dbh.createUniversityFromXml(u);

        dbh.insertFileVersion("university", uniXmlParser.getFileVersion());

    }
    //endregion

    //region get&set
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    //endregion



}
