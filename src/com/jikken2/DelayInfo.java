package com.jikken2;

import com.jikken2.ConnectDB_Delay.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DelayInfo extends Activity implements AsyncTaskCallback {
	private ConnectDB_Delay cDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delayinfo);
	}

	public void ClickButton(View view) {

		cDB = new ConnectDB_Delay(DelayInfo.this, "SELECT * FROM delayinfo",
				this);
		cDB.execute();

	}

	@Override
	public void preExecute() {
	}

	@Override
	public void postExecute(String result) {

		String reason = cDB.getReason();
		String oct = cDB.getOct();
		String dlt = cDB.getDlT();
		String route = cDB.getRoute();
		
		if(reason.equals("螟ｱ謨�")){
			AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
	        alertDlg.setTitle("謗･邯壹↓螟ｱ謨励＠縺ｾ縺励◆");
	        alertDlg.setMessage("");
	        alertDlg.setCancelable(false);
	        alertDlg.setPositiveButton(
	            "OK",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                }
	            });
	        alertDlg.create().show();
		 
		}else{
		TextView textView = (TextView) findViewById(R.id.textView9);
		textView.setText(oct + "    " + route + "        " + reason + "       "+ dlt);
	        }
		}

	@Override
	public void progressUpdate(int progress) {
	}

	@Override
	public void cancel() {
	}

}
