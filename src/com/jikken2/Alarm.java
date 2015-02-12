package com.jikken2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import static java.lang.Math.*;
import android.os.Vibrator;

public class Alarm extends Activity implements LocationListener {

	private String sta;	
	private double latitude;
	private double longitude;
	private double currentlati1;
	private double currentlongi1;
	private double currentlat;
	private double currentlng;
	private LocationManager manager = null;
	private double distance;
	private Vibrator vib;
	private long pattern[] = {1000, 200, 700, 200, 400, 200 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		manager = (LocationManager)getSystemService(LOCATION_SERVICE);
		vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);

		Bundle extras = getIntent().getExtras();
		sta = extras.getString("station");
		latitude = extras.getDouble("latitude");
		longitude = extras.getDouble("longitude");
		TextView text = (TextView)findViewById(R.id.textView1);

		text.setText(sta+"   "+latitude+"   "+longitude);
		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				onOperation();
			}
		});
	}
	
	protected void onOperation(){
		Bundle extras = getIntent().getExtras();
		sta = extras.getString("station");
		latitude = extras.getDouble("latitude");
		longitude = extras.getDouble("longitude");
		TextView text = (TextView)findViewById(R.id.textView1);

		text.setText(sta+"   "+latitude+"   "+longitude);

		double r = 6378.137;

		currentlat = latitude - 0.01; 
		currentlng = longitude -0.01;
		
		double lat1 = latitude * PI / 180;
		double lng1 = longitude * PI / 180;

		do{	
			double lat2 = currentlat * PI / 180;
			double lng2 = currentlng * PI / 180;

			distance = r * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lng2 - lng1));
			
			currentlat += 0.002;

		}while(distance >= 1);

		try{
			Thread.sleep(2000);
		}catch(InterruptedException e){}

		if(distance < 1){

			vib.vibrate(pattern, 0);

			AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
			alertDlg.setTitle("退場駅に到着しました");
			alertDlg.setMessage("起きてください。");
			alertDlg.setCancelable(false);
			alertDlg.setPositiveButton(
					"OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							vib.cancel();
							finish();
						}
					});
			alertDlg.create().show();
		}
	}
	
	@Override
	protected void onPause() {
		if(manager != null) {
			manager.removeUpdates(this);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(manager != null) {
			manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.alarm, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {
		currentlati1 = location.getLatitude();
		currentlongi1 = location.getLongitude();

		Bundle extras = getIntent().getExtras();
		sta = extras.getString("station");
		latitude = extras.getDouble("latitude");
		longitude = extras.getDouble("longitude");
		TextView text = (TextView)findViewById(R.id.textView1);

		text.setText(sta+"   "+latitude+"   "+longitude);

		double r = 6378.137;
		
		double lat1 = latitude * PI / 180;
		double lng1 = longitude * PI / 180;
		
		double lat2 = currentlati1 * PI / 180;
		double lng2 = currentlongi1 * PI / 180;

		TextView text7 = (TextView)findViewById(R.id.textView7);
		TextView text8 = (TextView)findViewById(R.id.textView8);

		text7.setText(String.valueOf(currentlati1));
		text8.setText(String.valueOf(currentlongi1));
		TextView text9 = (TextView)findViewById(R.id.textView9);

		double distance = r * acos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(lng2 - lng1));
		text9.setText(String.valueOf(distance));
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
