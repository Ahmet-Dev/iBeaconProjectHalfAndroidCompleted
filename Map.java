package test.tech.prome.odevapp;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.SupportMapFragment;
import com.radiusnetworks.proximity.KitConfig;
import com.radiusnetworks.proximity.ProximityKitGeofenceNotifier;
import com.radiusnetworks.proximity.ProximityKitGeofenceRegion;
import com.radiusnetworks.proximity.ProximityKitManager;
import com.radiusnetworks.proximity.RegionEvent;
import android.app.admin.NetworkEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Map extends Application
        implements ProximityKitGeofenceNotifier {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    protected static ProximityKitManager pkManager = null;
    public static native String stringFromJNI();

    private static final Object pkManagerLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (pkManagerLock) {
            if (pkManager == null) {
                pkManager = ProximityKitManager.getInstance(this, loadConfig());
            }
        }
        pkManager.start();
}       if (servicesConnected()) {

        try {
            pkManager.enableGeofences();

            // No point setting the notifier if we aren't using geofences
            pkManager.setProximityKitGeofenceNotifier(this);
        } catch (GooglePlayServicesException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void didDetermineStateForGeofence(@RegionEvent.RegionState int i, @NonNull ProximityKitGeofenceRegion proximityKitGeofenceRegion) {

    }

    @Override
    public void didEnterGeofence(@NonNull ProximityKitGeofenceRegion proximityKitGeofenceRegion) {

    }

    @Override
    public void didExitGeofence(@NonNull ProximityKitGeofenceRegion proximityKitGeofenceRegion) {

    }

    public class AndroidProximityKitReferenceApplication
            extends     Application
            implements  ProximityKitMonitorNotifier,
            ProximityKitGeofenceNotifier {

        @Override
        public void didEnterGeofence(ProximityKitGeofenceRegion region) {
            String welcomeMessage = region.getAttributes().get("welcomeMessage");
            if (welcomeMessage == null) {
                welcomeMessage = "Hello from the getting started guide!"
            }

            Log.d(TAG, "ENTER geofence region: " + region + " " + welcomeMessage);
        }

        @Override
        public void didExitGeofence(ProximityKitGeofenceRegion region) {
            Log.d(TAG, "EXIT geofence region: " + region);
        }

        @Override
        public void didDetermineStateForGeofence(int state, ProximityKitGeofenceRegion region) {
            Log.d(TAG, "didDeterineStateForGeofence called with region: " + region);

            switch (state) {
                case ProximityKitGeofenceNotifier.INSIDE:
                    Log.d(TAG, "ENTER beacon region: " + region);
                    break;
                case ProximityKitGeofenceNotifier.OUTSIDE:
                    Log.d(TAG, "EXIT beacon region: " + region);
                    break;
                default:
                    Log.d(TAG, "Received unknown state: " + state);
                    break;
            }
        }
    }
}