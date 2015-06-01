package helper;

import android.content.Context;
import android.content.res.Resources;

import com.example.norbe_000.moc_final.R;

import java.util.ArrayList;

import helper.database.DataBaseHandler;
import dataClasses.Course;
import dataClasses.University;
import helper.interfaces.IResourceListener;
import helper.xml.CourseXmlParser;
import helper.xml.UniXmlParser;

/**
 * Created by matthias rohrmoser on 01.06.2015.
 */
public class DataVersionHandler {
    private Context context;
    private IResourceListener listener;


    public DataVersionHandler(IResourceListener listener) {
        this.listener = listener;
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
        CourseXmlParser courseParser = CourseXmlParser.getInstance(resources.openRawResource(R.raw.courses));
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());

        System.out.printf("COURSE== fileVersion:%f @@@@ databaseVersion:%f\n\n", courseParser.getFileVersion(),dbh.queryFileVersionByName("course"));
        return (courseParser.getFileVersion() == dbh.queryFileVersionByName("course"));
    }

    private boolean checkUniversityVersion() {
        Resources resources = listener.getActivityResources();
        UniXmlParser uniParser = UniXmlParser.getInstance(resources.openRawResource(R.raw.university));
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        System.out.printf("UNI== fileVersion:%f @@@@ databaseVersion:%f\n\n", uniParser.getFileVersion(),dbh.queryFileVersionByName("university"));
        return (uniParser.getFileVersion() == dbh.queryFileVersionByName("university"));
    }

    public void updateCourse(){
        Resources resources = listener.getActivityResources();
        CourseXmlParser courseParser = CourseXmlParser.getInstance(resources.openRawResource(R.raw.courses));
        courseParser.setContext(listener.getActivityContext());
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        ArrayList<Course> courses = courseParser.parse();

        for(Course c : courses){
            dbh.createCourse(c);
            c.setId(dbh.queryCourseIdByObject(c));

            for(University u : c.getUniversities()){
                dbh.linkCourseToUni(c.getId(),u.getId());
            }
        }

        dbh.insertFileVersion("course", courseParser.getFileVersion());
    }

    public void updateUniversity(){
        Resources resources = listener.getActivityResources();
        UniXmlParser uniParser = UniXmlParser.getInstance(resources.openRawResource(R.raw.university));
        DataBaseHandler dbh = DataBaseHandler.getInstance(listener.getActivityContext());
        ArrayList<University> unis = uniParser.parse();
        for(University u : unis)
            dbh.createUniversityFromXml(u);

        dbh.insertFileVersion("university", uniParser.getFileVersion());

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
