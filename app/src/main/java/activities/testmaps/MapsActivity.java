package activities.testmaps;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.norbe_000.moc_final.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


public class MapsActivity extends ActionBarActivity implements GoogleMap.OnInfoWindowClickListener{

    private GoogleMap map;
    private UiSettings mapSettings;
    private  MarkerOptions marker;
    // URL prefix to the geocoder
    private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";
    private float lat;
    private float lng;
    private String website;
    private String name;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        address = intent.getExtras().getString("address");
        name = intent.getExtras().getString("name");
        website = intent.getExtras().getString("website");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        try {
            getCoordinates();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if(map != null)
        {
            mapSettings = map.getUiSettings();
            mapSettings.setZoomControlsEnabled(true);


            LatLng addresslatlong = new LatLng(lat,lng);

            marker = new MarkerOptions();
            marker.position(addresslatlong);
            marker.title(name);
            marker.snippet(website);

            map.moveCamera(CameraUpdateFactory.newLatLngZoom(addresslatlong, 14));
            map.addMarker(marker);
            map.setOnInfoWindowClickListener(this);
        }

    }

    private void getCoordinates() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        // prepare a URL to the geocoder
        URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false");

        // prepare an HTTP connection to the geocoder
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        Document geocoderResultDocument = null;
        try {
            // open the connection and get results as InputSource.
            conn.connect();
            InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());

            // read result and parse into XML Document
            geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
        } finally {
            conn.disconnect();
        }

        // prepare XPath
        XPath xpath = XPathFactory.newInstance().newXPath();

        // extract the result
        NodeList resultNodeList = null;

        resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
        lat = Float.NaN;
        lng = Float.NaN;
        for (int i = 0; i < resultNodeList.getLength(); ++i) {
            Node node = resultNodeList.item(i);
            if ("lat".equals(node.getNodeName())) lat = Float.parseFloat(node.getTextContent());
            if ("lng".equals(node.getNodeName())) lng = Float.parseFloat(node.getTextContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test_maps, menu);
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
    public void onInfoWindowClick(Marker marker) {
        Uri uriUril = Uri.parse(website);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUril);
        startActivity(launchBrowser);
    }
}
