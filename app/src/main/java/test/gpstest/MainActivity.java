package test.gpstest;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private float x1, x2;
    private DisplayMetrics metrics;
    private int MIN_DISTANCE;


    private TextView coordinates;
    private Button trackButton;
    private GPSTracker gps;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.MIN_DISTANCE = metrics.widthPixels / 3;

        // assigning the button and textview
        this.coordinates = (TextView) findViewById(R.id.coordinates);
        this.trackButton = (Button) findViewById(R.id.trackButton);

        // creating new GPSTracker objects with context.
        this.gps = new GPSTracker(this);
        //set default location to avoid nullchecks
        this.location = new Location("");
        this.location.setLatitude(0.0);
        this.location.setLatitude(0.0);
    }


    //method to control tracking
    // mit getLocation wird geschaut, ob GPS an ist und danach wird die location geholt.
    // Das Problem hier ist, das dies nur einmal passiert, bei jedem Knopfdruck.
    // Die Funktion, kontinuierlich GPS daten zu laden muss in einem AsyncTask geschehen.
    public void controlTrack(View v) {

        if (null != gps.getLocation()) {
            Toast.makeText(this, "fetching ... please wait", Toast.LENGTH_LONG).show();
            this.location = gps.getLocation();
            coordinates.setText("Breitengrad: " + this.location.getLatitude() + "°\n" + "Längengrad: " + this.location.getLongitude() + "°");
        } else {
            Toast.makeText(this, "could not get location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x1 - x2;
                if (deltaX > MIN_DISTANCE) {
                    swipeRight2Left();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * calls the maps activity if a right2left swipe is registered.
     */
    private void swipeRight2Left() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("location", this.location);
        startActivity(intent);
        finish();
    }

}