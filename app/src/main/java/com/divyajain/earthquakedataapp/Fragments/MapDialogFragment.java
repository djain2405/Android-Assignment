package com.divyajain.earthquakedataapp.Fragments;




import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.divyajain.earthquakedataapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDialogFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    View rootView;
    Double lat;
    Double lng;
    String magnitude;


    public MapDialogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.mapfragment, container, false);
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            Bundle bundle = getArguments();
            lat = bundle.getDouble("latitude");
            lng = bundle.getDouble("longitude");
            magnitude = bundle.getString("magnitude");
            mapFragment.getMapAsync(this);
        }
       return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng latLng = new LatLng(lat,lng);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,2));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(magnitude);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        map.addMarker(markerOptions);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
