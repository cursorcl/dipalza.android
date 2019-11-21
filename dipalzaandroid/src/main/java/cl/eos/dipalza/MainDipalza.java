package cl.eos.dipalza;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.eos.dipalza.rest.APIService;
import cl.eos.dipalza.rest.APIServiceAdapter;
import cl.eos.dipalza.rest.parameters.PositionData;
import cl.eos.dipalza.rest.responses.User;
import cl.eos.dipalza.rest.responses.Vendedor;
import cl.eos.dipalza.rest.tasks.LoginTask;
import cl.eos.dipalza.rest.tasks.ObtainSellerTask;
import cl.eos.dipalza.rest.tasks.SavePositionTask;

import static android.content.ContentValues.TAG;

public class MainDipalza extends DashboardActivity {
	/**
	 * Code used in requesting runtime permissions.
	 */
	private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

	/**
	 * Constant used in the location settings dialog.
	 */
	private static final int REQUEST_CHECK_SETTINGS = 0x1;

	/**
	 * The desired interval for location updates. Inexact. Updates may be more or less frequent.
	 */
	private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

	/**
	 * The fastest rate for active location updates. Exact. Updates will never be more frequent
	 * than this value.
	 */
	private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
			UPDATE_INTERVAL_IN_MILLISECONDS / 2;

	// Keys for storing activity state in the Bundle.
	private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
	private final static String KEY_LOCATION = "location";
	private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

	private Date mLastUpdateTime;
	/**
	 * Provides access to the Fused Location Provider API.
	 */
	private FusedLocationProviderClient mFusedLocationClient;

	/**
	 * Provides access to the Location Settings API.
	 */
	private SettingsClient mSettingsClient;
	/**
	 * Stores parameters for requests to the FusedLocationProviderApi.
	 */
	private LocationRequest mLocationRequest;

	/**
	 * Stores the types of location services the client is interested in using. Used for checking
	 * settings to determine if the device has optimal location settings.
	 */
	private LocationSettingsRequest mLocationSettingsRequest;

	/**
	 * Callback for Location events.
	 */
	private LocationCallback mLocationCallback;

	/**
	 * Represents a geographical location.
	 */
	private Location mCurrentLocation;
	private boolean mRequestingLocationUpdates;
	/**
	 * Corresponde al servicio REST que provee mecanismo para grabar datos en la nube.
	 */
	private APIService restService = APIServiceAdapter.getApiService();
	private User user;
	private Vendedor vendedor;
	private LoginTask task;
	private ObtainSellerTask sellerTask;
	private SavePositionTask savePositionTask;

	/**
	 * Referencia a controlador de sistema.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerChannels();
		setContentView(R.layout.activity_home);


		task = new LoginTask(restService);
		task.execute();
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
		mSettingsClient = LocationServices.getSettingsClient(this);

		// Kick off the process of building the LocationCallback, LocationRequest, and
		// LocationSettingsRequest objects.
		createLocationCallback();
		createLocationRequest();
		buildLocationSettingsRequest();
		startLocationUpdates();

	}

	private void registerChannels() {
		final List<NotificationChannel> channels = new ArrayList<>();

		NotificationChannel channelInfo;
		channelInfo = new NotificationChannel(getString(R.string.INFO_CHANNEL_ID), getString(R.string.INFO_CHANNEL_NAME), NotificationManager.IMPORTANCE_DEFAULT);
		channelInfo.setDescription(getString(R.string.INFO_CHANNEL_DESC));


		NotificationChannel channelError = new NotificationChannel(getString(R.string.ERROR_CHANNEL_ID), getString(R.string.ERROR_CHANNEL_NAME), NotificationManager.IMPORTANCE_HIGH);
		channelError.setDescription(getString(R.string.ERROR_CHANNEL_DESC));


		channels.add(channelInfo);
		channels.add(channelError);
		final NotificationManager notificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		if (notificationManager != null) {
			notificationManager.createNotificationChannels(channels);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menumaindipalza, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemVentas:
			realizarVentas();
			return true;
		case R.id.itemInicializacion:
			realizarInicializacion();
			return true;
		case R.id.itemResumen:
			visaulizarResumenVentas();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	/**
	 * Metodo que visualiza resumen de ventas.
	 */
	private void visaulizarResumenVentas() {
		Intent intent = new Intent(this, VentasResumen.class);
		this.startActivity(intent);		
	}

	/**
	 * Metodo que solicita al controlador inicializar interfaz de
	 * inicialziacion.
	 */
	private void realizarInicializacion() {
		Intent intent = new Intent(this, ActivityConfiguracion.class);
		this.startActivity(intent);
	}

	/**
	 * Metodo que solicita al controlador inicializar interfaz de
	 * inicializacion.
	 */
	private void realizarVentas() {
		Intent intent = new Intent(this, VentaRegistros.class);		
		this.startActivity(intent);
	}

	/**
	 * Sets up the location request. Android has two location request settings:
	 * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
	 * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
	 * the AndroidManifest.xml.
	 * <p/>
	 * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
	 * interval (5 seconds), the Fused Location Provider API returns location updates that are
	 * accurate to within a few feet.
	 * <p/>
	 * These settings are appropriate for mapping applications that show real-time location
	 * updates.
	 */
	private void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		// Sets the desired interval for active location updates. This interval is
		// inexact. You may not receive updates at all if no location sources are available, or
		// you may receive them slower than requested. You may also receive updates faster than
		// requested if other applications are requesting location at a faster interval.
		mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
		// Sets the fastest rate for active location updates. This interval is exact, and your
		// application will never receive updates faster than this value.
		mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	/**
	 * Creates a callback for receiving location events.
	 */
	private void createLocationCallback() {
		mLocationCallback = new LocationCallback() {
			@Override
			public void onLocationResult(LocationResult locationResult) {
				super.onLocationResult(locationResult);
				if(locationResult == null)
					return;
				if(task.getUser() == null)
					return;
				user = task.getUser();

				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainDipalza.this);
				String code = preferences.getString(ActivityConfiguracion.PREF_VENDEDOR, "");
				if(code.isEmpty())
					return;

				if(sellerTask == null)
				{
					sellerTask = new ObtainSellerTask(restService, user, code);
					sellerTask.execute();
					return;
				}
				if(sellerTask.getSeller() == null )
				{
					return;
				}
				if(vendedor == null) {
					vendedor = sellerTask.getSeller();
				}

				mCurrentLocation = locationResult.getLastLocation();
				double latitud = mCurrentLocation.getLatitude();
				double longitud = mCurrentLocation.getLongitude();
				double bearing = mCurrentLocation.getBearing();
				double speed = mCurrentLocation.getSpeedAccuracyMetersPerSecond();
				mLastUpdateTime = new Date();
				// TODO debo registrar la posición en alguna parte


				if(savePositionTask == null)
					savePositionTask = new SavePositionTask(restService, user, vendedor);

				PositionData pos = new PositionData(vendedor.getId(), latitud, longitud, bearing, speed);
				savePositionTask.execute(pos);

			}
		};
	}

	/**
	 * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
	 * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
	 * if a device has the needed location settings.
	 */
	private void buildLocationSettingsRequest() {
		LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
		builder.addLocationRequest(mLocationRequest);
		mLocationSettingsRequest = builder.build();
	}


	/**
	 * Requests location updates from the FusedLocationApi. Note: we don't call this unless location
	 * runtime permission has been granted.
	 */
	private void startLocationUpdates() {
		// Begin by checking if the device has the necessary location settings.
		mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
				.addOnSuccessListener(this, locationSettingsResponse -> {
					Log.i(TAG, "All location settings are satisfied.");

					//noinspection MissingPermission
					mFusedLocationClient.requestLocationUpdates(mLocationRequest,
							mLocationCallback, Looper.myLooper());

					//updateUI();
				})
				.addOnFailureListener(this, e -> {
					int statusCode = ((ApiException) e).getStatusCode();
					switch (statusCode) {
						case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
							Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
									"location settings ");
							try {
								// Show the dialog by calling startResolutionForResult(), and check the
								// result in onActivityResult().
								ResolvableApiException rae = (ResolvableApiException) e;
								rae.startResolutionForResult(MainDipalza.this, REQUEST_CHECK_SETTINGS);
							} catch (IntentSender.SendIntentException sie) {
								Log.i(TAG, "PendingIntent unable to execute request.");
							}
							break;
						case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
							String errorMessage = "Location settings are inadequate, and cannot be " +
									"fixed here. Fix in Settings.";
							Log.e(TAG, errorMessage);
							Toast.makeText(MainDipalza.this, errorMessage, Toast.LENGTH_LONG).show();
							mRequestingLocationUpdates = false;
					}

					//updateUI();
				});
	}

	/**
	 * Removes location updates from the FusedLocationApi.
	 */
	private void stopLocationUpdates() {
		if (!mRequestingLocationUpdates) {
			Log.d(TAG, "stopLocationUpdates: updates never requested, no-op.");
			return;
		}

		// It is a good practice to remove location requests when the activity is in a paused or
		// stopped state. Doing so helps battery performance and is especially
		// recommended in applications that request frequent location updates.
		mFusedLocationClient.removeLocationUpdates(mLocationCallback)
				.addOnCompleteListener(this, task -> {
					mRequestingLocationUpdates = false;
					//setButtonsEnabledState();
				});
	}

	@Override
	public void onResume() {
		super.onResume();
		// Within {@code onPause()}, we remove location updates. Here, we resume receiving
		// location updates if the user has requested them.
		if (mRequestingLocationUpdates && checkPermissions()) {
			startLocationUpdates();
		} else if (!checkPermissions()) {
			requestPermissions();
		}

		//updateUI();
	}

	@Override
	protected void onPause() {
		super.onPause();

		// Remove location updates to save battery.
		stopLocationUpdates();
	}

	/**
	 * Stores activity data in the Bundle.
	 */
/*	public void onSaveInstanceState(Bundle savedInstanceState) {
		if(mLastUpdateTime != null) {
			savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
			savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
			savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime.toString());
		}
		super.onSaveInstanceState(savedInstanceState);
	}*/

	/**
	 * Return the current state of the permissions needed.
	 */
	private boolean checkPermissions() {
		int permissionState = ActivityCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION);
		return permissionState == PackageManager.PERMISSION_GRANTED;
	}

	private void requestPermissions() {
		boolean shouldProvideRationale =
				ActivityCompat.shouldShowRequestPermissionRationale(this,
						Manifest.permission.ACCESS_FINE_LOCATION);

		// Provide an additional rationale to the user. This would happen if the user denied the
		// request previously, but didn't check the "Don't ask again" checkbox.
		if (shouldProvideRationale) {
			Log.i(TAG, "Displaying permission rationale to provide additional context.");

		} else {
			Log.i(TAG, "Requesting permission");
			// Request permission. It's possible this can be auto answered if device policy
			// sets the permission in a given state or the user denied the permission
			// previously and checked "Never ask again".
			ActivityCompat.requestPermissions(MainDipalza.this,
					new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
					REQUEST_PERMISSIONS_REQUEST_CODE);
		}
	}
}