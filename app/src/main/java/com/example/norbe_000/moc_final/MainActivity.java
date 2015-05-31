package com.example.norbe_000.moc_final;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import activities.studiActivity.StudiActivity;
import activities.testmaps.testMaps;
import activities.uniActivity.UniActivity;
import helper.DialogHelper;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, DialogHelper.DialogListener {

    Button uniButton;
    Button studiButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uniButton = (Button) findViewById(R.id.uni_button);
        uniButton.setOnClickListener(this);

        studiButton = (Button) findViewById(R.id.studi_button);
        studiButton.setOnClickListener(this);
        getSupportActionBar().hide();
        //Testcommit
        //Matze war hier
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        System.out.println("CLICKED:" + v.getId());
        System.out.println("UNI: " + R.id.uni_button);
        System.out.println("STUDI: " + R.id.studi_button);
        if(v.getId() == R.id.uni_button)
        {
            Intent intent = new Intent(this, UniActivity.class);
            startActivity(intent);
        }

        else if(v.getId() == R.id.studi_button)
        {
            showCourseDialog();
        }


    }

    private void showCourseDialog() {
        DialogFragment dialog = new DialogHelper();
        dialog.show(getFragmentManager(),"CourseDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Intent intent = new Intent(this, StudiActivity.class);
        intent.putExtra("type","m");
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Intent intent = new Intent(this, StudiActivity.class);
        intent.putExtra("type","b");
        startActivity(intent);
    }
}
