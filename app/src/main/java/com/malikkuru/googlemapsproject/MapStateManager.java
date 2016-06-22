package com.malikkuru.googlemapsproject;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mesihmalikkuru on 07/06/16.
 */


public class MapStateManager {  //to save map state when closed or backed

    private static final String LONGITUDE = "longitude", LATITUDE = "latitude", ZOOM = "zoom", BEARING = "bearing", TILT = "tilt";

    private static final String MAPTYPE = "MAPTYPE";
    private static final String PREFS_NAME = "mapCameraState";

    private SharedPreferences mapStatePrefs;

    public MapStateManager(Context context) {
        mapStatePrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveMapState(GoogleMap map) {
        SharedPreferences.Editor editor = mapStatePrefs.edit();
        CameraPosition position = map.getCameraPosition();

        editor.putFloat(LATITUDE, (float) position.target.latitude);
        editor.putFloat(LONGITUDE, (float) position.target.longitude);
        editor.putFloat(ZOOM, position.zoom);
        editor.putFloat(TILT, position.tilt);
        editor.putFloat(BEARING, position.bearing);
        editor.putInt(MAPTYPE, map.getMapType());

        editor.commit();
    }

    public CameraPosition getSavedCameraPosition() {

        double lat = mapStatePrefs.getFloat(LATITUDE, 0);

        if(lat == 0) {     //????
            return null;
        }

        double lng = mapStatePrefs.getFloat(LONGITUDE, 0);

        LatLng target = new LatLng(lat, lng);

        float zoom = mapStatePrefs.getFloat(ZOOM, 0);
        float bearing = mapStatePrefs.getFloat(BEARING, 0);
        float tilt = mapStatePrefs.getFloat(TILT, 0);

        CameraPosition cp = new CameraPosition(target, zoom, bearing, tilt);

        return cp;

    }
    public int getMapType() {
        int mapType = mapStatePrefs.getInt(MAPTYPE, 0);

        return mapType;
    }
}
