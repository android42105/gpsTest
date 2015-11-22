package test.gpstest;

import android.app.Service;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


/**
 * Class for tracking location via GPS.
 */
public class GPSTracker implements LocationListener {

     // flag for GPS status
    private boolean isGPSEnabled = false;
    //flag for Network status
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;
    private Location location;

    // Declaring a Location Manager
    private LocationManager locationManager;
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = (1000 * 60 * 1) / 6; // 10 sec

    /**
     * Constructor
     *
     * @param context must not be null, should be passed from activity.
     */
    public GPSTracker(Context context) {

        this.locationManager = (LocationManager) context.getSystemService(Service.LOCATION_SERVICE);
    }


    /**
     * Fetches location based on the provider.
     * <p/>
     * prioritieses NETWORK over GPS.
     *
     * @return the current location.
     */
    //TODO getLocation should be async as to continously fetch location.
    public Location getLocation() {

        try {
            // getting network status
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            // getting GPS status
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isNetworkEnabled) {
                locateWith(LocationManager.NETWORK_PROVIDER);
            } else if (isGPSEnabled) {
                locateWith(LocationManager.GPS_PROVIDER);
            } else if (!isNetworkEnabled && !isGPSEnabled) {
                this.canGetLocation = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }


    /**
     * Locates a location with a specific Type of Provider.
     *
     * @param locationType GPS_PROVIDER or NETWORK_PROVIDER
     * @return true if no error occured, false if location could not be fetched.
     */
    private boolean locateWith(String locationType) {
        this.canGetLocation = true;
        try {
            locationManager.requestLocationUpdates(
                    locationType,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            this.location = locationManager.getLastKnownLocation(locationType);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Calling this function will stop using GPS in your app
     * <p/>
     * supressing the 'checkPermission' thingy because i know the manifest includes permissions.
     */
    @SuppressWarnings("ResourceType")
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            return location.getLatitude();
        }
        return 0;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            return location.getLongitude();
        }
        return 0;
    }


    /**
     * Function to check GPS/wifi enabled.
     */
    public boolean canGetLocation() {

        return this.canGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}