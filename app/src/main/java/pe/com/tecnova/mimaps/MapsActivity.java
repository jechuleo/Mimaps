package pe.com.tecnova.mimaps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
//import android.util.Log;
//import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private CameraUpdate mcamera;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        //Obtenemos una referencia al LocationManager
        // nuevo cambio agregado para probar github
        // otro cambio no figuro el 2do cambio
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        if (null != lastKnownLocation) setUbicacionMapa(lastKnownLocation);
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (null != locationGPS) setUbicacionMapa(locationGPS);

        LocationListener milocListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                setUbicacionMapa(location);
            }
            public void onProviderDisabled(String provider){
            }
            public void onProviderEnabled(String provider){
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
               // Log.i("LocAndroid", "Provider Status: " + status);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, milocListener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(40.070823, -2.137360)).title("Marker")
                        // .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
         //               .icon(BitmapDescriptorFactory.fromResource(R.drawable.micono))
          //              .snippet("Ciudad de la Humanidad")
       // );


        setMarker(new LatLng(40.067589,-2.135677),"Cafeteria","Cafes a buen precio",0.9F,0.1F,0.1F,R.drawable.micono);
        setMarker(new LatLng(40.071180,-2.135145),"Restaurant","comidas tipicas",0.5F,0.5F,0.5F,R.drawable.micono);
        mcamera = CameraUpdateFactory.newLatLngZoom(new LatLng(40.070823,-2.137360),12);
        mMap.animateCamera(mcamera);

    }

    private void setMarker(LatLng posicion, String titulo, String info,float opacidad,float dimension1,float dimension2,int icon){

        mMap.addMarker(new MarkerOptions().position(posicion).title(titulo).snippet(info)
                .alpha(opacidad).anchor(dimension1, dimension2).icon(BitmapDescriptorFactory.fromResource(icon)));
    }

    private void setUbicacionMapa( Location miubicacion)
    {
        mMap.clear();
        //setMarker(new LatLng(miubicacion.getLatitude(), miubicacion.getLongitude()), "Aca Estoy", "Radar", 0.9F, 0.1F, 0.1F, R.drawable.micono);
        //setMarker(new LatLng(40.071180,-2.135145),"Restaurant","comidas tipicas",0.5F,0.5F,0.5F,R.drawable.micono);
        mcamera = CameraUpdateFactory.newLatLngZoom(new LatLng(miubicacion.getLatitude(),miubicacion.getLongitude()),12);
        mMap.animateCamera(mcamera);
    }

//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        switch (item.getItemId()){
//            case R.id.MenuOpcion1:
//                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            return true;
//            case R.id.MenuOpcion2:
//                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//            return true;
//            case R.id.MenuOpcion3:
//                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//            return true;
//            case R.id.MenuOpcion4:
//                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//            return true;
//            default:
//                    return super.onOptionsItemSelected(item);
//        }
//
//    }
}
