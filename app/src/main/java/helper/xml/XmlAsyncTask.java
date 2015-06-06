package helper.xml;

import android.os.AsyncTask;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import helper.DataVersionHandler;
import helper.interfaces.IResourceListener;

/**
 * Created by matthias on 06.06.2015.
 */
public class XmlAsyncTask extends AsyncTask<IResourceListener,Void,Void> {


    @Override
    protected Void doInBackground(IResourceListener... params) {
        String[] streams = XmlRequester.getXmlFromUrl(new String[]{XmlRequester.COURSEURL, XmlRequester.UNIURL});
//
        for(String s : streams){
            System.out.printf("--->%100s\n", s);
        }
        InputStream coursestream = new ByteArrayInputStream(streams[0].getBytes());
        InputStream unistream = new ByteArrayInputStream(streams[1].getBytes());



        new DataVersionHandler(params[0], unistream,coursestream).fullProcedure();
        return null;
    }
}
