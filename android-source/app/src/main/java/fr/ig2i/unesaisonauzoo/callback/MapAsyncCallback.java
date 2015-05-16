package fr.ig2i.unesaisonauzoo.callback;


import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.ig2i.unesaisonauzoo.R;


public class MapAsyncCallback implements OnMapReadyCallback {
    Activity act;

    public MapAsyncCallback(Activity act) {
        this.act = act;
    }

    private static double lat = 47.675860;
    private static double lng = -0.045758;

    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(lat, lng), 16));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title(act.getResources().getString(R.string.zooName)));
    }
}
