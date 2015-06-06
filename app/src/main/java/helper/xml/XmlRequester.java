package helper.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by matthias on 06.06.2015.
 */
public class XmlRequester {

    public static final String COURSEURL = "http://wi-project.technikum-wien.at/ss15/ss15-bvz4-moc-1/youni/courses.xml";
    public static final String UNIURL = "http://wi-project.technikum-wien.at/ss15/ss15-bvz4-moc-1/youni/university.xml";

    public static String[] getXmlFromUrl(String[] url) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "blackrio", "andr0id".toCharArray());
            }
        });

        int index = 0;
        String[] streams = new String[url.length];
        try {
            for(String s : url){
                URL toXml = new URL(s);
                HttpURLConnection urlConnection = (HttpURLConnection) toXml.openConnection();
                String inputStreamString = new Scanner(urlConnection.getInputStream(),"UTF-8").useDelimiter("\\A").next();
                streams[index++] = inputStreamString;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return streams;
    }
}
