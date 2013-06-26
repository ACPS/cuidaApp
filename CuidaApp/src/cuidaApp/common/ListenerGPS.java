package cuidaApp.common;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.nerdcore.logs.Trace;

public class ListenerGPS {

	private LocationManager locManager;
	private LocationListener locListener;
	private Location currentBestLocation;
	private static final int SECONDS = 1000 * 10;
	public double longitud = 0.0;
	public double latitud = 0.0;
	private static String TAG = "ListenerGPS";
	private static ListenerGPS instance;
	private boolean started;

	public static ListenerGPS getInstance() {
		if (instance == null) {
			instance = new ListenerGPS();
		}
		return instance;
	}

	private ListenerGPS() {
		this.started = false;
	}

	public void obtenerUbicacion(Context context) {

		if (!started) {

			this.started = true;

			Trace.i(TAG, "start listener GPS");

			locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

			locListener = new LocationListener() {
				public void onLocationChanged(Location location) {

					if (isBetterLocation(location, currentBestLocation)) {
						longitud = location.getLongitude();
						latitud = location.getLatitude();
						currentBestLocation = location;
					} else {
						longitud = currentBestLocation.getLongitude();
						latitud = currentBestLocation.getLatitude();
					}

					Trace.i(TAG,
							"-------------------------------------------------");
					Trace.i(TAG, "longitud:" + longitud);
					Trace.i(TAG, "latitud:" + latitud);
					Trace.i(TAG,
							"-------------------------------------------------");
					Trace.i(TAG,
							"Satelites:"
									+ currentBestLocation.getExtras().getInt(
											"satellites"));
					Trace.i(TAG,
							"Precision:" + currentBestLocation.getAccuracy());
					Trace.i(TAG,
							"-------------------------------------------------");

				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onProviderDisabled(String provider) {
				}
			};

			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
					0, 0, locListener);
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
					0, locListener);

		}

	}

	protected boolean isBetterLocation(Location location,
			Location currentBestLocation) {

		if (currentBestLocation == null) {
			// A new location is always better than no location
			return true;
		}

		// Check whether the new location fix is newer or older
		long timeDelta = location.getTime() - currentBestLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > SECONDS;
		boolean isSignificantlyOlder = timeDelta < -SECONDS;
		boolean isNewer = timeDelta > 0;

		// If it's been more than two minutes since the current location, use
		// the new location
		// because the user has likely moved
		if (isSignificantlyNewer) {
			return true;
			// If the new location is more than two minutes older, it must be
			// worse
		} else if (isSignificantlyOlder) {
			return false;
		}

		// Check whether the new location fix is more or less accurate
		int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;

		// Check if the old and new location are from the same provider
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				currentBestLocation.getProvider());

		// Determine location quality using a combination of timeliness and
		// accuracy
		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		return false;
	}

	/** Checks whether two providers are the same */
	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	public void stopListener() {
		this.locManager.removeUpdates(this.locListener);
		Trace.i(TAG, "stop listener GPS");
		this.started = false;
	}

	public boolean checkMinimalRequeriments() {

		if (this.started && currentBestLocation != null) {

			// Trace.i(TAG,
			// currentBestLocation.getAccuracy()+" -"+currentBestLocation.getExtras().getInt("satellites"));

			if ((currentBestLocation.getAccuracy() <= 10)
					&& (currentBestLocation.getExtras().getInt("satellites") >= 10)) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

}
