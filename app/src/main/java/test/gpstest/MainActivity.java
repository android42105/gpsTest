package test.gpstest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private TextView coordinates;
    private Button trackButton;
    private GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigning the button and textview
        this.coordinates = (TextView) findViewById(R.id.coordinates);
        this.trackButton = (Button) findViewById(R.id.trackButton);

        // creating new GPSTracker objects with context.
        this.gps = new GPSTracker(this);

    }


    //method to control tracking
    // mit getLocation wird geschaut, ob GPS an ist und danach wird die location geholt.
    // Das Problem hier ist, das dies nur einmal passiert, bei jedem Knopfdruck.
    // Die Funktion, kontinuierlich GPS daten zu laden muss in einem AsyncTask geschehen.
    public void controlTrack(View v) {

        if(!gps.canGetLocation()){
            Toast.makeText(this, "GPS is not enabled", Toast.LENGTH_SHORT).show();
        }

        gps.getLocation();
        if (gps.canGetLocation()) {
            coordinates.setText("latitide = " + gps.getLatitude() + " longitude = " + gps.getLongitude());
        }

    }


}