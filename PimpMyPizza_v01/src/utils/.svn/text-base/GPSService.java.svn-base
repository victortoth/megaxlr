package utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GPSService implements LocationListener {

	private final Context mContext;
	
	public boolean isGPSEnabled = false;
	
	boolean canGetLocation = false;
	
	Location current_location;
	double latitude;
	double longitude;
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
	
	private static final long MIN_TIME_BW_UPDATES = 1;
	
	protected LocationManager locationManager;
	
	public GPSService (Context context){
		this.mContext = context;
		getLocation();
	}
		
	// FUNCTION TO GET LOCATION
	
	public Location getLocation(){
		try{
			locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			if(!isGPSEnabled){
				Toast.makeText(mContext, "Servico de GPS indisponível", Toast.LENGTH_SHORT).show();
			}else{
				this.canGetLocation = true;
				current_location = null;
				if(current_location == null){
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
							MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					if(locationManager != null){
						current_location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					}
					if(current_location != null){
						latitude = current_location.getLatitude();
						longitude = current_location.getLongitude();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return current_location;
		
	}
	
	public void stopUsingGPS(){
			if(locationManager!=null){
				locationManager.removeUpdates(GPSService.this);
			}
	}
	
	public double getLatitude(){
		if(current_location != null){
			latitude = current_location.getLatitude();
		}
		return latitude;
	}
	
	public double getLongitude(){
		if(current_location != null){
			longitude = current_location.getLongitude();
		}
		return longitude;
	}
	
	public boolean canGetLocation(){
		return this.canGetLocation;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		this.current_location = location;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	
}
