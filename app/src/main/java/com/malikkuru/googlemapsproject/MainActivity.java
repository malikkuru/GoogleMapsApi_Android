package com.malikkuru.googlemapsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    GoogleMap mMap;

    private static final double HOME_LAT = 39.990376, HOME_LNG = 32.870631;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleApiClient client;
    private GoogleApiClient mClient; // for reaching location programaticly

    public boolean onCreateOptionsMenu(Menu menu) {   // this method shows menu icon on toolbar
        MenuInflater inflater = getMenuInflater();      // use with menu/menu.xml
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (servicesOK()) {
            setContentView(R.layout.activity_map);

            if (initMap()) {
                Toast.makeText(this, "Map ready", Toast.LENGTH_SHORT).show();

                goToLocation(HOME_LAT, HOME_LNG, 18);

                mClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
                mClient.connect();


            /*    try{
                    mMap.setMyLocationEnabled(true);    // finds your location
                } catch (SecurityException e) {
                    Log.e("PERMISSION_EXCEPTON", "PERMISSION_NOT_GRANTED");
                }
            */

            } else {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
        } else {
            setContentView(R.layout.activity_main);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    //    client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public boolean servicesOK() { //checking service available

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can not connect to map services", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean initMap() {  //map initializing

        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mMap = mapFragment.getMap();
        }
        return (mMap != null);
    }

    public void goToLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom); // without zoom use newLatLng(latLng) method
        mMap.moveCamera(update);
    }

    public void geoLocate(View v) throws IOException {
        hideSoftKeyboard(v);

        EditText et = (EditText) findViewById(R.id.editText1);
        String location = et.getText().toString();

        Geocoder gc = new Geocoder(this);

        List<Address> list = gc.getFromLocationName(location, 1); //1 is maximum result number
        Address add = list.get(0);

        String locality = add.getLocality(); //girilen adresin bulunduğu konum

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

        double lat = add.getLatitude();
        double lng = add.getLongitude();

        goToLocation(lat, lng, 15);
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *
     *
     * public Action getIndexApiAction() {
     Thing object = new Thing.Builder()
     .setName("Main Page") // TODO: Define a title for the content shown.
     // TODO: Make sure this auto-generated URL is correct.
     .setUrl(Uri.parse("http://host/path"))
     .build();
     return new Action.Builder(Action.TYPE_VIEW)
     .setObject(object)
     .setActionStatus(Action.STATUS_TYPE_COMPLETED)
     .build();
     }
     */


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
     /*   client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());*/
    }

    @Override
    public void onStop() {
        super.onStop();

        cameraLastPositionSave();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
      /*  AppIndex.AppIndexApi.end(client, getIndexApiAction()); //autogenerated
        client.disconnect();    */                                //autogenerated




    }

    @Override
    public void onResume() {
        super.onResume();

        cameraLastPositionLoad();

     /*   client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());    *///autogenerated

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // option item handler
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cameraLastPositionSave() {
        MapStateManager mgr = new MapStateManager(this);  //to save position of camera
        mgr.saveMapState(mMap);                           //save camera position
    }
    public void cameraLastPositionLoad() {
        MapStateManager mgr = new MapStateManager(this);
        CameraPosition cp = mgr.getSavedCameraPosition();
        if(cp != null) {
            CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cp);
            mMap.moveCamera(cu);    //load camera position
            mMap.setMapType(mgr.getMapType()); // load map type
        }
    }

    public void gotoCurrentLocation(MenuItem item) {
        try {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(mClient);
            if (currentLocation != null){
                LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15); // zoom 15
                mMap.animateCamera(update);
            } else {
                Toast.makeText(this, "Couldn't Connect!", Toast.LENGTH_SHORT).show();
            }


        } catch (SecurityException s) {
            Toast.makeText(this, "Security Error!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { //implements googleapiclient
        Toast.makeText(this, "Connected to location Services ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) { // implements googleapiclient

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { // implements googleapiclient

    }
}
