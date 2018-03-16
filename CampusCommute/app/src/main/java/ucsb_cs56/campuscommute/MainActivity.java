package ucsb_cs56.campuscommute;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener{

    private static final String TAG = "MainActivity";

    private static final int ERROR_DIALOG_REQUEST = 9011;

    private GoogleMap mGoogleMap;

    private Marker mMarker = null;

    private Marker mMarker2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isServicesOK()) init();
        Spinner dropdown = findViewById(R.id.LocationMenu);
        dropdown.setTag(0);
        Spinner second = findViewById(R.id.LocationMenu2);
        second.setTag(1);

        dropdown.setOnItemSelectedListener(this);
        second.setOnItemSelectedListener(this);

        Button alertButton = findViewById(R.id.button_id);
        alertButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Time: 3 Minutes");
                builder.setNegativeButton("Ok", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void init() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            // user doesn't need to update version and can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Log.d(TAG, "isServicesOK: Fixable error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void onMapReady(GoogleMap googleMap) {
        LatLng ucsb = new LatLng(34.413963, -119.848946);
        this.mMarker = googleMap.addMarker(new MarkerOptions().position(ucsb)
                .title("Marker in UCSB"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucsb, 15));
        this.mMarker2 = googleMap.addMarker(new MarkerOptions().position(ucsb)
                .title("2nd Marker in UCSB"));
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        LatLng latlng;
        switch (i) {
            case 0:
                latlng = new LatLng(34.415017, -119.841571);
                break;
            case 1:
                latlng = new LatLng(34.41623, -119.845262);
                break;
            case 2:
                latlng = new LatLng(34.415372, -119.84274);
                break;
            case 3:
                latlng = new LatLng(34.415323, -119.843974);
                break;
            case 4:
                latlng = new LatLng(34.411539, -119.847772);
                break;
            case 5:
                latlng = new LatLng(34.416385, -119.84435);
                break;
            default:
                latlng = new LatLng(34.413963, -119.848946);
                break;
        }
        if((Integer)adapterView.getTag() == 1){
            mMarker2.setPosition(latlng);
        }else{
            mMarker.setPosition(latlng);
        }
    }


    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}





