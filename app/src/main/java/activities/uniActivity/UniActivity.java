package activities.uniActivity;

import android.app.AlertDialog;
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
import java.util.List;

import activities.testmaps.MapsActivity;
import dataClasses.Course;
import dataClasses.University;
import helper.database.DataBaseHandler;

public class UniActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView list;
    private int bookmarkFilter = 1;
    private DataBaseHandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uni);

        int courseId = getIntent().getIntExtra("course", -1);
        dbh = DataBaseHandler.getInstance(getApplicationContext());
        List<University> universityList;
        if(courseId != -1){
            Course c = dbh.queryCourseById(courseId);
            universityList = dbh.queryUniversitiesByCourse(c);
        }

        else{
            universityList = University.getUniversities(getApplicationContext());
        }

        list = (ListView)findViewById(R.id.uniList);
        list.setAdapter(new ArrayAdapter<University>(this, android.R.layout.simple_list_item_1, universityList));
        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        Intent intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uni, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.bookmark_uni:
                FilterBookmarks();
                return true;
            case R.id.action_settings:
                new AlertDialog.Builder(this)
                        .setTitle("Bedienungsanleitung")
                        .setMessage("Listenelement gedrückt halten, um Lesezeichen zu verwalten.")
                        .setCancelable(true)
                        .show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void FilterBookmarks() {
        ArrayList<University> uList = new ArrayList<>(University.getUniversities(getApplicationContext()));

        //Alle Elemente anzeigen
        if(bookmarkFilter == 0 ){
            list.setAdapter(new ArrayAdapter<University>(this, android.R.layout.simple_list_item_1, uList));
            bookmarkFilter++;

            Toast.makeText(this,"Alle anzeigen", Toast.LENGTH_SHORT).show();
        }
        //Nur Lesezeichen anzeigen
        else if(bookmarkFilter == 1){

            for (int i = 0; i < uList.size(); i++){
                if(!uList.get(i).isFlag()){
                    uList.remove(i--);
                }
            }

            list.setAdapter(new ArrayAdapter<University>(this, android.R.layout.simple_list_item_1, uList));
            Toast.makeText(this, "Nur Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();

            bookmarkFilter++;

        }
        //Keine Leisezeichen anzeigen
        else{

            for (int i = 0; i < uList.size(); i++){
                if(uList.get(i).isFlag()){
                    uList.remove(i--);
                }
            }

            list.setAdapter(new ArrayAdapter<University>(this, android.R.layout.simple_list_item_1, uList));
            Toast.makeText(this,"Keine Lesezeichen anzeigen", Toast.LENGTH_SHORT).show();

            bookmarkFilter = 0;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("address", ((University) list.getItemAtPosition(position)).getAddress().toString());
        intent.putExtra("name", ((University) list.getItemAtPosition(position)).getName());
        intent.putExtra("website",((University) list.getItemAtPosition(position)).getWebsite());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        University u = (University) list.getItemAtPosition(position);
        if(u.isFlag())
        {
            u.setFlag(false);
            Toast.makeText(this,"Lesezeichen entfernt", Toast.LENGTH_SHORT).show();
        }
        else
        {
            u.setFlag(true);
            Toast.makeText(this,"Lesezeichen hinzugefuegt",Toast.LENGTH_SHORT).show();
        }
        dbh.updateUniBookmark(u);
        ((ArrayAdapter<University>) list.getAdapter()).notifyDataSetChanged();

        return  true;
    }

}
