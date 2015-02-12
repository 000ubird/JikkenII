package com.jikken2;

import com.jikken2.ConnectDB_PP.AsyncTaskCallback;

import android.view.View;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;




 public class PastPrevention extends Activity implements AsyncTaskCallback{
	 private ConnectDB_PP cDB;
	 private String item;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_past_prevention);
	     
	     @SuppressWarnings("rawtypes")
		ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.us_state, android.R.layout.simple_spinner_item);
	             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	             final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
	             spinner.setAdapter(adapter);
	             
	             spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
	                 @Override
	                 public void onItemSelected(AdapterView<?> v, View view,int position, long id){
	                      item = (String)spinner.getSelectedItem();
	                 }
	                 @Override
	                 public void onNothingSelected(AdapterView<?> arg0){
	                 }
	             });	       
 }
	
	public void ClickButton(View view){
		cDB = new ConnectDB_PP(PastPrevention.this,"SELECT * FROM stationposition WHERE station ='"+item+"';",this);
		cDB.execute();		
	}

	@Override
	public void preExecute() {
	}

	@Override
	public void postExecute(String result) {
		String sta = cDB.getSta();	
		double latitude = cDB.getLati();
		double longitude = cDB.getLongi();
		if(sta.equals("螟ｱ謨�") || !sta.equals(item)){
			System.out.println(item);
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
	        alertDlg.setTitle("謗･邯壹↓螟ｱ謨励＠縺ｾ縺励◆");
	        alertDlg.setMessage("謗･邯壹ｒ遒ｺ隱阪＠縺ｦ縺上□縺輔＞");
	        alertDlg.setCancelable(false);
	        alertDlg.setPositiveButton(
	            "OK",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                }
	            });

	        alertDlg.create().show();
		 
		
		}else{
		Intent i = new Intent(this,Alarm.class);
		i.putExtra("station", sta);
		i.putExtra("latitude", latitude);
		i.putExtra("longitude",longitude);
		startActivity(i);
	}
	}

	@Override
	public void progressUpdate(int progress) {
	}

	@Override
	public void cancel() {
	}
	}

