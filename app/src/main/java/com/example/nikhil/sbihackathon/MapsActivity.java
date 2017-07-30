package com.example.nikhil.sbihackathon;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks
,GoogleApiClient.OnConnectionFailedListener,LocationListener{

    private FloatingActionButton add_button;
    private GoogleMap mMap;
    double latitude=0;
    double longitude=0;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private DatabaseReference mDatabase;
    Button mSendData;
    ArrayList<map_layout> list=new ArrayList<map_layout>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progressDialog=new ProgressDialog(MapsActivity.this);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        add_button=(FloatingActionButton)findViewById(R.id.btnfirebase);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


 /*        mSendData=(Button)findViewById(R.id.btn_trial_firebase);

       mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_array_Post();
            }
        });
*/
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MapsActivity.this,mapsLogin.class);
                startActivity(i);
            }
        });
        get_data();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the map_layout will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the map_layout has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.isTrafficEnabled();
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
/*        list.add(new map_layout(21.7679,78.8718,"SBI preetvihar",1,""));
        list.add(new map_layout(28.7886037,77.1411602,"SBI gagan",-1,""));
        list.add(new map_layout(28.6814284,77.341444,"SBI yojna",0,""));
        list.add(new map_layout(22.352525,76.4235,"SBI kalyanpuri",1,""));
        list.add(new map_layout(23.5453463,76.5525,"SBI nirman vihar",1,""));
        list.add(new map_layout(25.46436,76.886,"SBI mvc",0,""));
        list.add(new map_layout(23.525225,74.3352,"SBI npki",1,""));
        list.add(new map_layout(23.46664,74.3353353,"SBI kln",-1,""));
        list.add(new map_layout(32.5325,76.43335,"SBI jl",-1,""));
        list.add(new map_layout(23.523522,78.5646,"SBI jln",0,""));
*/

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    private void submit_array_Post() {
       ArrayList<map_layout> sampleJournalEntries = getSampleJournalEntries();
        for(int i=0;i<sampleJournalEntries.size();i++) {
            map_layout map_layout=sampleJournalEntries.get(i);
            mDatabase.child(map_layout.getKey()).setValue(map_layout);
        }
    }

    public ArrayList<map_layout> getSampleJournalEntries(){
        ArrayList<map_layout> journalEnrties=new ArrayList<map_layout>();
       for (int i=0;i<list.size();i++){
           String key = mDatabase.push().getKey();
           list.get(i).setKey(key);
       }
        return list;
    }

    public void get_data(){
        final ArrayList<map_layout> mmap_layout = new ArrayList<map_layout>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                    map_layout note=noteSnapshot.getValue(map_layout.class);
                    Log.e("Problem","done");

                            /*.getLatitude(),
                            noteSnapshot.getValue(map_layout.class).getLongitude(),
                            noteSnapshot.getValue(map_layout.class).getTitle(),
                            noteSnapshot.getValue(map_layout.class).getFback(),
                            noteSnapshot.getValue(map_layout.class).getKey());
*/                          mmap_layout.add(note);

                }
                     set_data(mmap_layout);            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

     }

    private void set_data(ArrayList<map_layout> mmap_layout) {
        list=mmap_layout;
        set_data_on_map();
     }

    private void set_data_on_map() {

        for(int i=0;i<list.size();i++) {
            map_layout data=list.get(i);

            LatLng sydney = new LatLng(data.getLatitude(),data.getLongitude());

            MarkerOptions marker = new MarkerOptions().position(new LatLng(data.getLatitude(),data.getLongitude())).title(data.getTitle());
            if(data.getFback()<0){
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));}
            else if(data.getFback()>0){ marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            }else{
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            progressDialog.dismiss();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });

    }


}

