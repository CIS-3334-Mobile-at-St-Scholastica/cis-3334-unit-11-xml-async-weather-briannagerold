package edu.css.unit11_weather_async;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.xmlpull.v1.XmlPullParser.TYPES;

/**
 * @author Tom Gibbons
 * @version 1.0
 * This class is used to get weather data Asynchronously
 */
public class AsyncDownloadXML extends AsyncTask<MainActivity, String, String> {

    MainActivity mainActivityLink;

    /**
     * Implementation of AsyncTask used to download XML feed
     * This method is run in a separate thread.  Do not do any UI stuff here.
     * Calls onPostExecute when done and passes it the return value or String
     * @param new_actWeather
     * @return
     */
    @Override
    protected String doInBackground(MainActivity... new_actWeather) {
        try {
            Log.v("== CIS 3334 ==","AsyncDownloadXML doInBackground");
            // Save a pointer to the main Weather Activity which is passed in as a parameter
            mainActivityLink = new_actWeather[0];

            // create the XML Pull Parser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            String zip = mainActivityLink.getLocation();

            String  weatherStrURL =  "http://api.openweathermap.org/data/2.5/weather?zip=" + zip +",us&appid=5aa6c40803fbb300fe98c6728bdafce7&mode=xml&units=imperial";
            URL weatherURL =  new URL(weatherStrURL);
            InputStream stream = weatherURL.openStream();
            xpp.setInput(stream, null);
            int eventType = xpp.getEventType();

            String tempStr = "Updating...";			// Temperature Update String
            String windStr = "Updating...";			// Wind Update String
            String visStr = "Updating...";          //Visibility Update String
            publishProgress(tempStr,windStr,visStr);       //calls onProgressUpdate to update the user on the progress

            Log.v("== CIS 3334 ==","AsyncDownloadXML repeat until end of document arrives");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                Log.v("== CIS 3334 ==","AsyncDownloadXML eventType = "+TYPES[eventType]);
                // look for a start tag
                if(eventType == XmlPullParser.START_TAG) {

                    // get the tag name and process it
                    String tag = xpp.getName();
                    Log.v("== CIS 3334 ==","Start tag found with name = "+tag);
                    if (tag.equals("speed")){
                        // XML should look like: <speed value="11.41" name="Strong breeze"/>
                        windStr = xpp.getAttributeValue(null, "value");
                        Log.v("== CIS 3334 ==","Wind =" + windStr);
                        publishProgress(tempStr,windStr, visStr);	// Update the display

                    }
                    if (tag.equals("temperature")){
                        // XML should look like: <temperature value="37.47" min="33.8" max="41" unit="fahrenheit"/>
                        tempStr = xpp.getAttributeValue(null, "value");
                        Log.v("== CIS 3334 ==","Temp =" + tempStr);
                        publishProgress(tempStr,windStr, visStr);	// Update the display
                    }
                    if (tag.equals("visibility")){
                        // XML should look like: <temperature value="37.47" min="33.8" max="41" unit="fahrenheit"/>
                        visStr = xpp.getAttributeValue(null, "value");
                        Log.v("== CIS 3334 ==","Vis =" + visStr);
                        publishProgress(tempStr,windStr,visStr);	// Update the display
                    }
                }
                eventType = xpp.next();
            }
           return "Successfully updated weather"; //returns that is was successful

        } catch (IOException e) {
            Log.v("== CIS 3334 -- ERROR ==","AsyncDownloadXML doInBackground IOException");
            Log.v("== CIS 3334 -- ERROR ==",e.getMessage());
            return(e.getMessage()); //returns error message
        } catch (XmlPullParserException e) {
            Log.v("== CIS 3334 -- ERROR ==","AsyncDownloadXML doInBackground XmlPullParserException");
            Log.v("== CIS 3334 -- ERROR ==",e.getMessage());
            return(e.getMessage()); //returns error message
        }  catch (Exception e) {
            Log.v("== CIS 3334 -- ERROR ==","AsyncDownloadXML doInBackground Exception");
            Log.v("== CIS 3334 -- ERROR ==",e.getMessage());
            return(e.getMessage()); //returns error message
        }
    }

    /**
     * gives an update to the mainActivity
     * so the user knows what is going on.
     * @param update
     */
    @Override
    protected void onProgressUpdate(String... update) {
        //logs the progress
        Log.v("== CIS 3334 ==","in onProgressUpdate");
        //calls setTemp in the MainActivity and sends it the temp string that was sent
        mainActivityLink.setTemp(update[0]);
        //calls setWing in the MainActiviy and sends it the wind string that was passed
        mainActivityLink.setWind(update[1]);
        //calls setVis in the MainActivity and sends it the visibility that was passed
        mainActivityLink.setVis(update[2]);
    }

    /**
     * When the task is over, send the
     * result to the main activity
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        //logs that it is in onPostEsecute
        Log.v("== CIS 3334 ==", "in onPostExecute");
        //calls setStatus in MainActivity and passes the result (success or error)
        mainActivityLink.setStatus(result);
    }

}


