package helper;

import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import dataClasses.Course;

/**
 * Created by matthias on 01.06.2015.
 */
public class CourseFilter {

    public static ArrayList<Course> filterCoursesByType(ArrayList<Course> coursesToFilter, char filter){
        ArrayList<Course> coursesReturn = new ArrayList<>();

        for(Course c : coursesToFilter){
            char first = c.getType().charAt(0);

            if(Character.toLowerCase(first) == Character.toLowerCase(filter)){
                coursesReturn.add(c);
            }
        }

        if(coursesReturn.isEmpty())
            return coursesToFilter;

        return coursesReturn;
    }

    public static ArrayList<Course> filterCoursesByBookmark(ArrayList<Course> coursesToFilter, int bookmarkFilter) {


        //Display bookmarks only
        if(bookmarkFilter == 1){

            for (int i = 0; i < coursesToFilter.size(); i++){
                if(!coursesToFilter.get(i).isFlag()){
                    coursesToFilter.remove(i--);
                }
            }
        }
        //Don't display any bookmarks
        else{

            for (int i = 0; i < coursesToFilter.size(); i++){
                if(coursesToFilter.get(i).isFlag()){
                    coursesToFilter.remove(i--);
                }
            }
        }
        return coursesToFilter;
    }
}
