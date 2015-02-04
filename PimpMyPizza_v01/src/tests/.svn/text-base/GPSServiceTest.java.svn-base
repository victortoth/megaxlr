package tests;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import junit.framework.*;
import lugares.Localizar;

public class GPSServiceTest extends AndroidTestCase implements LocationListener{

	private LocationManager locationManager;
	private Location mLocation;
	private final double LATITUDE = 0.0;
	private final double LONGITUDE = 0.0;
	private static final int TIMEOUT = 5000;
	private final String unidade_proxima = "Aricanduva";
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		locationManager = (LocationManager) getContext().
	            getSystemService(Context.LOCATION_SERVICE);
	        assertNotNull(locationManager);
		
			}

	public void testGetLocation(){
		 try{
	            synchronized ( this ){
	                HandlerThread handlerThread = new HandlerThread("testLocationUpdates");
	                handlerThread.start();
	                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this,
	                        handlerThread.getLooper());
	                this.wait(TIMEOUT);
	            }
	        }catch ( InterruptedException ie){
	            ie.printStackTrace();
	            Assert.fail();
	        }
	        assertNotNull(mLocation);
	        assertEquals(new Float(LONGITUDE), new Float(mLocation.getLongitude()));
	        assertEquals(new Float(LATITUDE), new Float(mLocation.getLatitude()));
	        locationManager.removeUpdates(this);
	}
	
	public void testUnidadeMaisProxima(){
		Localizar localizar = new Localizar();
		assertEquals(unidade_proxima, localizar.retornarUnidadeProxima().getNomeUnidade());
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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
