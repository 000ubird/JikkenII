package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ConnectDB extends AsyncTask<Void, Void, String> {
	private static String URL = "jdbc:mysql://172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	private static int TIMEOUT = 5;		//’PˆÊ:•b
	Activity act = null;
	private String sql = "";
	
	/**
	 * ƒAƒNƒeƒBƒrƒeƒB‚ğˆø”‚Éæ‚Á‚½ƒRƒ“ƒXƒgƒ‰ƒNƒ^
	 * @param act  ƒAƒNƒeƒBƒrƒeƒB
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	/**
	 * ï¿½Aï¿½Nï¿½eï¿½Bï¿½rï¿½eï¿½Bï¿½Æï¿½ï¿½sï¿½ï¿½ï¿½ï¿½SQLï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Éï¿½ï¿½
	 * @param act  ï¿½Aï¿½Nï¿½eï¿½Bï¿½rï¿½eï¿½B
	 * @param sql  ï¿½ï¿½ï¿½sï¿½ï¿½ï¿½ï¿½SQLï¿½ï¿½
	 */
	public ConnectDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	@Override
	protected void onPreExecute() {
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText("’ÊM’†");
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBCƒhƒ‰ƒCƒo‚Ìƒ[ƒh
			Class.forName("com.mysql.jdbc.Driver");
			//ƒ^ƒCƒ€ƒAƒEƒgİ’è
			DriverManager.setLoginTimeout(TIMEOUT);
			//ƒf[ƒ^ƒx[ƒX‚ÉÚ‘±
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			ResultSet rs = null;

			//ƒXƒe[ƒgƒƒ“ƒgƒIƒuƒWƒFƒNƒgì¬
			Statement stmt = (Statement)con.createStatement();
			//SQL•¶‚ÌÀs‚Æƒf[ƒ^‚Ìæ“¾
			rs = stmt.executeQuery("SELECT id,pass FROM test");

			while(rs.next()){
				text += "ID : "+rs.getString(1) +" , Pass : "+ rs.getString(2)+"\n";
			}
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			text = "Ú‘±ƒGƒ‰[‚Å‚·";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
	}
}

