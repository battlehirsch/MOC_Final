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

import static dataClasses.Course.getCourses;

public class StudiActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView list;
    private int bookmarkFilter = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studi);
        list = (ListView) findViewById(R.id.courses);
        list.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, getCourses(getApplicationContext())));
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

    private void FilterBookmarks() {
        ArrayList<Course> cList = new ArrayList<>(Course.getCourses(getApplicationContext()));
        System.out.println("Filter == " + bookmarkFilter);

        //Alle Elemente anzeigen
        if(bookmarkFilter == 0 ){
            list.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, cList));
            bookmarkFilter++;

            Toast.makeText(this,"Alle anzeigen", Toast.LENGTH_SHORT).show();
        }
        //Nur Lesezeichen anzeigen
        else if(bookmarkFilter == 1){

            for (int i = 0; i < cList.size(); i++){
                if(!cList.get(i).isFlag()){
                    cList.remove(i--);
                }
            }

            list.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, cList));
            Toast.makeText(this, "Nur Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();

            bookmarkFilter++;

        }
        //Keine Leisezeichen anzeigen
        else{

            for (int i = 0; i < cList.size(); i++){
                if(cList.get(i).isFlag()){
                    cList.remove(i--);
                }
            }

            list.setAdapter(new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_1, cList));
            Toast.makeText(this,"Keine Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();

            bookmarkFilter = 0;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent myIntent = new Intent(this, UniActivity.class);
        Course c = (Course) list.getItemAtPosition(position);
        myIntent.putExtra("course", c.getId());
        startActivity(myIntent);

    }

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
}
