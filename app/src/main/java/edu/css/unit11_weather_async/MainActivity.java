package edu.css.unit11_weather_async;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Tom Gibbons
 * @author Brianna Gerold
 * @version 2.0
 * This class is the logic behind the screen
 */
public class MainActivity extends AppCompatActivity {
    EditText etLoc;
    EditText etTemp;
    EditText etWind;
    EditText etVis;

    //new AsyncDownloadXML object
    AsyncDownloadXML AsyncWeatherDownloader;

    /**
     * Called when the activity is starated
     * sets up the text fields
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up our edit text variables
        etLoc =  (EditText) findViewById(R.id.textLoc);
        etTemp =  (EditText) findViewById(R.id.textTemp);
        etWind =  (EditText) findViewById(R.id.textWind);
        etVis =  (EditText) findViewById(R.id.textVis);
    }

    /**
     * When the button is clicked this method
     * is called. It starts the
     * AsyncWeatherDownloader task
     * @param v - the view
     * @throws XmlPullParserException
     * @throws URISyntaxException
     * @throws IOException
     */
    public void btnClick (View v) throws XmlPullParserException, URISyntaxException, IOException {
        // Download the weather asynchronously
        AsyncWeatherDownloader = new AsyncDownloadXML();
        AsyncWeatherDownloader.execute(this);
    }

    /**
     * Sets the temp that is passed to
     * the edit text field on the screen
     * @param newTemp
     */
    public void setTemp(String newTemp) {
        etTemp.setText(newTemp);
    }

    /**
     * Sets the wind that is passed to
     * the edit text field on the screen
     * @param newWind
     */
    public void setWind(String newWind) {
        etWind.setText(newWind);
    }

    /**
     * Sets the visibility that is passed
     * to the edit text field
     * @param newVis
     */
    public void setVis(String newVis){
        etVis.setText(newVis);
    }

    /**
     * Returns the location the user
     * has entered as a string
     * @return
     */
    public String getLocation() {
        return etLoc.getText().toString();
    }

    /**
     * Takes in a new status and
     * displays it in a toast
     * @param newStatus
     */
    public void setStatus(String newStatus) {
        //creates a toast with the new status
        Toast toast=Toast.makeText(getApplicationContext(), newStatus,Toast.LENGTH_LONG );
        //displays the toast
        toast.show();
    }

}
