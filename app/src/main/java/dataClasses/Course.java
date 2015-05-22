package dataClasses;

import java.util.ArrayList;

/**
 * Created by matthias rohrmoser on 22.05.2015.
 */
public class Course {
    private int id;
    private String name;
    private boolean flag;
    private ArrayList<University> universities;
    private static ArrayList<Course> courses;

    public static ArrayList<Course> getCourses() {
        if(courses == null){
            courses = new ArrayList<Course>();
            courses.add(new Course(1,"aa", false));
            courses.add(new Course(2,"ab", false));
            courses.add(new Course(3,"ba", false ));
            courses.add(new Course(4,"bb", false ));
            courses.add(new Course(5,"cb", false ));
            courses.add(new Course(6,"ca", false ));
        }
        return courses;
    }

    public Course() {
    }

    public Course(int id, String name, boolean flag) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return String.format("%02d, %10.10s %s", getId(), getName(), isFlag());
    }
}
