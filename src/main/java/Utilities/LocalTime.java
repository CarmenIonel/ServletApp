package Utilities;

import entity.City;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LocalTime {

    public String getLocalTime(City city) throws IOException {

        String line="", time="";

        double latitude = city.getLatitude();
        double longitude = city.getLongitude();

        String urlCity = "http://new.earthtools.org/timezone/" + latitude + "/" + longitude;
        URL url = new URL(urlCity);

        URLConnection urlConnection = url.openConnection();
        urlConnection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.contains("<localtime>")) {
                line = inputLine;
                break;
            }
        }
        in.close();

        ParseXML parseXML=new ParseXML();
        time=parseXML.parse(line);

        return time;
    }
}
