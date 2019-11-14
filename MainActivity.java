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
import com.radiusnetworks.proximity.KitConfig;
import com.radiusnetworks.proximity.ProximityKitGeofenceNotifier;
import com.radiusnetworks.proximity.ProximityKitGeofenceRegion;
import com.radiusnetworks.proximity.ProximityKitManager;
import com.radiusnetworks.proximity.RegionEvent;
import android.app.admin.NetworkEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class MainActivity1 extends Application
        implements ProximityKitGeofenceNotifier {
    /**
     * Singleton storage for an instance of the manager
     */
    protected static ProximityKitManager pkManager = null;
    public static native String stringFromJNI();
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


// Used to load the 'native-lib' library on application startup.
public class MainActivity2 extends AppCompatActivity {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       Map<String, String> settings = new HashMap<>();
       settings.put(
               KitConfig.CONFIG_API_URL,
               "https://proximitykit.radiusnetworks.com/api/kits/MY_KIT_ID"
       );
       settings.put(
               KitConfig.CONFIG_API_TOKEN,
               "MY_KIT_SDK_TOKEN"
       );
       Properties settings1 = new Properties();
       settings1.setProperty(
               KitConfig.CONFIG_API_URL,
               "https://proximitykit.radiusnetworks.com/api/kits/MY_KIT_ID"
       );
       settings1.setProperty(
               KitConfig.CONFIG_API_TOKEN,
               "MY_KIT_SDK_TOKEN"
       );
    // Example of a call to a native method
    TextView tv = (TextView) findViewById(R.id.instant_message);
    tv.setText(MainActivity.stringFromJNI());
       static {
           System.loadLibrary("native-lib");
           MainActivity.pkManager = ProximityKitManager.getInstance(getApplicationContext(),
                   new KitConfig(settings1)
           );
       }

       Button bttn_name;
       bttn_name = (Button)findViewById(R.id.joinButton);
       bttn_name.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View arg0) {
               startActivity(new Intent("test.tech.prome.odevapp.app.src.main.res.layout.map"));
           }
       });
   }
    private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(TAG, "Google Play services available");
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getSupportFragmentManager(), TAG);
            }
            return false;
        }
    }
}
