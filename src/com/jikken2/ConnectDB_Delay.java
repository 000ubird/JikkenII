package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class ConnectDB_Delay extends AsyncTask<Void, Void, String> {
	
	private static String URL = "172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	
	private static int TIMEOUT = 5;		
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String reason = "";
	private String oct = "";
	private String dlt = "";
	private String route = "";
	private AsyncTaskCallback callback = null;;
	
	
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	
	public ConnectDB_Delay(Activity act){
		this.act = act;
	}
	
	public ConnectDB_Delay(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	

	public ConnectDB_Delay(Activity act,String sql,AsyncTaskCallback _callback){
		this.act = act;
		this.sql = sql;
	    this.callback = _callback;
	}
	public String getReason(){
		return reason;
	}
	
	public String getOct(){
		return oct;
	}
	
	public String getDlT(){
		return dlt;
	}
	
	public String getRoute(){
		return route;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        callback.preExecute();
        
	  
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("Connecting...");  
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBC load
			Class.forName("com.mysql.jdbc.Driver");
			//time out 
			DriverManager.setLoginTimeout(TIMEOUT);
			//connect to DB
			Connection con = DriverManager.getConnection("jdbc:mysql://"+URL,USER,PASS);
		
			Statement stmt = con.createStatement();
			//execute query
				rs = stmt.executeQuery(sql);
			
				while(rs.next()){
					reason += rs.getString("reason");
					oct += rs.getString("occerredTime");
					dlt += rs.getString("delayTime");
					route += rs.getString("route");
				}
			
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			text = "螟ｱ謨�";
			reason = "螟ｱ謨�";
		}
		return text;	
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
        
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}