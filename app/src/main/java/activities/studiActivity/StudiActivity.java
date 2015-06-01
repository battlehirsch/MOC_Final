package activities.studiActivity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.norbe_000.moc_final.R;

import java.util.ArrayList;

import activities.uniActivity.UniActivity;
import dataClasses.Course;
import helper.CourseFilter;

import static dataClasses.Course.getCourses;

/**
 * Activity for displaying and interacting with the courses
 */
public class StudiActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    // ListView for displaying the courses
    private ListView list;
    /*
    *   Filter for the bookmarks
    *   0 = display both
    *   1 = display only bookmarks
    *   2 = display no bookmarks
    */
    private int bookmarkFilter = 1;
    private char typeFilter = 'x';
    ArrayList<Course> allCourses;


    //region METHODS: onCreate/onCreateOptionsMenu/onOptionsItemSelected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studi);
        typeFilter = this.getIntent().getCharExtra("type", 'x');


        allCourses = Course.getCourses(getApplicationContext());

        list = (ListView) findViewById(R.id.courses);

        list.setAdapter(new ArrayAdapter<Course>(this,
                android.R.layout.simple_list_item_1, CourseFilter.filterCoursesByType(allCourses,typeFilter)));
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        Intent intent = getIntent();
        System.out.println(intent.getIntExtra("uniId", -1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_studi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case R.id.bookmark_studi:
                System.out.println("Bookmark clicked");
                FilterBookmarks();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    //endregion

    //region METHODS: ItemClick & LongItemClick
    /**
     * If an item is clicked the list redirects to the UniActivity
     * where all Universities are listed. <br />
     *  It automatically sets an extra so only the university(or universities) which
     *  belongs to the course are displayed
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent myIntent = new Intent(this, UniActivity.class);
        Course c = (Course) list.getItemAtPosition(position);
        myIntent.putExtra("course", c.getId());
        startActivity(myIntent);

    }

    /**
     * LongClickMethod for the ListView
     * whenever a listitem is clicked long <br />
     * the bookmark changes it states to marked or not marked <br />
     * depending on the current state <br />
     * @param parent
     * @param view
     * @param position
     * @param id
     * @return
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("Long click");
        Course c = (Course) list.getItemAtPosition(position);

        if(c.isFlag())
        {
            //Nachricht
            c.setFlag(false);
            Toast.makeText(this, "Lesezeichen entfernt", Toast.LENGTH_SHORT).show();
        }

        else
        {
            c.setFlag(true);
            Toast.makeText(this, "Lesezeichen hinzugefuegt", Toast.LENGTH_SHORT).show();
        }
        ((ArrayAdapter<Course>) list.getAdapter()).notifyDataSetChanged();
        return true;
    }
    //endregion

    /**
         * Method for filtering the bookmarks is called whenever the user tapped the star button in the action bar
         */
    private void FilterBookmarks() {
        ArrayList<Course> cList = CourseFilter.filterCoursesByBookmark(allCourses, typeFilter);
        CourseFilter.filterCoursesByBookmark(cList, bookmarkFilter);

        //Display all elements
        if(bookmarkFilter == 0 ){
          Toast.makeText(this,"Alle anzeigen", Toast.LENGTH_SHORT).show();
        }

        //Display bookmarks only
        else if(bookmarkFilter == 1){
            Toast.makeText(this, "Nur Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();
        }
        //Don't display any bookmarks
        else{
            Toast.makeText(this,"Keine Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();
        }

        if(++bookmarkFilter > 1){
            bookmarkFilter = 0;
        }
        list.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, cList));
    }
    //endregion
}
