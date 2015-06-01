package dataClasses;

import android.content.Context;

import java.util.ArrayList;

import helper.database.DataBaseHandler;

/**
 * Created by matthias rohrmoser on 22.05.2015.
 */
public class Course {
    private int id;
    private String name;
    private String type;
    private boolean flag;
    private ArrayList<University> universities;
    private static ArrayList<Course> courses;

    public static ArrayList<Course> getCourses(Context con) {
        DataBaseHandler db = DataBaseHandler.getInstance(con);
        if(courses == null){
            courses = db.queryAllCourses();
        }
        db.closeDB();
        return courses;
    }
    public Course() {
    }

    public Course(int id, String name, boolean flag) {
        this.id = id;
        this.name = name;
        this.flag = flag;
    }

    public Course(int id, String name, String type, boolean flag) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public ArrayList<University> getUniversities() {
        return universities;
    }

    public void setUniversities(ArrayList<University> universities) {
        this.universities = universities;
    }

    @Override
    public String toString() {
        return this.getName();
    }


}
