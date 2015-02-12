package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class UpdateDBDist extends AsyncTask<Void, Void, String> {
	
	private static String URL = "172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	
	private static int TIMEOUT = 5;
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	/**
	 * �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB�ｿｽ�ｿｽ�ｿｽ�ｿｽﾉ趣ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽR�ｿｽ�ｿｽ�ｿｽX�ｿｽg�ｿｽ�ｿｽ�ｿｽN�ｿｽ^
	 * @param act  �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB
	 */
	public UpdateDBDist(Activity act){
		this.act = act;
	}
	
	/**
	 * �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB�ｿｽﾆ趣ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽSQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾉ趣ｿｽ�ｿｽ
	 * @param act  �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB
	 * @param sql  �ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽSQL�ｿｽ�ｿｽ
	 */
	public UpdateDBDist(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	/**
	 * �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB�ｿｽﾆ趣ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽSQL�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾉ趣ｿｽ�ｿｽ
	 * @param act  �ｿｽA�ｿｽN�ｿｽe�ｿｽB�ｿｽr�ｿｽe�ｿｽB
	 * @param sql  �ｿｽ�ｿｽ�ｿｽs�ｿｽ�ｿｽ�ｿｽ�ｿｽSQL�ｿｽ�ｿｽ
	 */
	public UpdateDBDist(Activity act,String sql,AsyncTaskCallback _callback){
		this.act = act;
		this.sql = sql;
	    this.callback = _callback;
	}
	
	public String getResult(){
		return result;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        callback.preExecute();
        
	    // �ｿｽv�ｿｽ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽX�ｿｽ_�ｿｽC�ｿｽA�ｿｽ�ｿｽ�ｿｽO�ｿｽﾌ撰ｿｽ�ｿｽ�ｿｽ
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("�ｿｽﾊ信�ｿｽ�ｿｽ...");  // �ｿｽ�ｿｽ�ｿｽb�ｿｽZ�ｿｽ[�ｿｽW�ｿｽ�ｿｽ�ｿｽZ�ｿｽb�ｿｽg
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBC�ｿｽh�ｿｽ�ｿｽ�ｿｽC�ｿｽo�ｿｽﾌ�ｿｽ�ｿｽ[�ｿｽh
			Class.forName("com.mysql.jdbc.Driver");
			//�ｿｽ^�ｿｽC�ｿｽ�ｿｽ�ｿｽA�ｿｽE�ｿｽg�ｿｽﾝ抵ｿｽ
			DriverManager.setLoginTimeout(TIMEOUT);
			//�ｿｽf�ｿｽ[�ｿｽ^�ｿｽx�ｿｽ[�ｿｽX�ｿｽﾉ接托ｿｽ
			Connection con = DriverManager.getConnection("jdbc:mysql://"+URL,USER,PASS);
			//�ｿｽX�ｿｽe�ｿｽ[�ｿｽg�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽg�ｿｽI�ｿｽu�ｿｽW�ｿｽF�ｿｽN�ｿｽg�ｿｽ�成
			Statement stmt = con.createStatement();
			//SQL�ｿｽ�ｿｽ�ｿｽﾌ趣ｿｽ�ｿｽs(�ｿｽX�ｿｽV)
				stmt.executeUpdate(sql);		
			
			stmt.close();
			con.close();
			
		} catch(Exception e) {
			text = "�ｿｽﾚ托ｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽﾅゑｿｽ";
			result = "�ｿｽﾚ托ｿｽ�ｿｽG�ｿｽ�ｿｽ�ｿｽ[�ｿｽﾅゑｿｽ";
		}
		return text;	
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
        
		// �ｿｽv�ｿｽ�ｿｽ�ｿｽO�ｿｽ�ｿｽ�ｿｽX�ｿｽ_�ｿｽC�ｿｽA�ｿｽ�ｿｽ�ｿｽO�ｿｽ�ｿｽﾂゑｿｽ�ｿｽ�ｿｽ
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}