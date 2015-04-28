package darkvador.nurseproject;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by zumatec on 28/04/15.
 */
public class geocode {
    private GoogleMap googleMap;
    private Context mContext;
    private LocationManager locationManager;
    private String provider, adressePatient;
    private LatLng positionPatient, positionAgent;
    private boolean reussiGeolocalisationAgent = false;

    public boolean isReussiGeolocalisationPatient() {
        return reussiGeolocalisationPatient;
    }

    public boolean isReussiGeolocalisationAgent() {
        return reussiGeolocalisationAgent;
    }

    private boolean reussiGeolocalisationPatient = false;

    public LatLngBounds.Builder getBuilder() {
        return builder;
    }

    private LatLngBounds.Builder builder = new LatLngBounds.Builder();
    public geocode(String a, Context c){
        adressePatient=a;
        mContext=c;
        setPositionPatient();
        locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        //locationManager.requestLocationUpdates(provider, 20000, 0, (android.location.LocationListener) mContext);
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            positionAgent = new LatLng(location.getLatitude(),
                    location.getLongitude());
            reussiGeolocalisationAgent = true;
        }
        else {
            //"Erreur dans la géolocalisation"
        }
    }
    public LatLng getPositionAgent() {
        return positionAgent;
    }

    public LatLng getPositionPatient() {
        return positionPatient;
    }
    public void setPositionPatient(){
        Geocoder fwdGeocoder = new Geocoder(mContext, Locale.FRANCE);
        List<Address> locations = null;
        try {
            locations = fwdGeocoder.getFromLocationName(adressePatient, 10);
        } catch (IOException e) {
            //"Pbs geocoder adresse Patient
        }
        if ((locations == null) || (locations.isEmpty())) {
            //"Adresse Patient inconnu !"
        } else {
            positionPatient = new LatLng(locations.get(0).getLatitude(),
                    locations.get(0).getLongitude());
            reussiGeolocalisationPatient = true;
        }
    }
}
