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
	private static int TIMEOUT = 5;		//単位:秒
	Activity act = null;
	
	/**
	 * アクティビティを引数に取ったコンストラクタ
	 * @param act  アクティビティ
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	@Override
	protected void onPreExecute() {
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText("通信中");
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBCドライバのロード
			Class.forName("com.mysql.jdbc.Driver");
			//タイムアウト設定
			DriverManager.setLoginTimeout(TIMEOUT);
			//データベースに接続
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			ResultSet rs = null;

			//ステートメントオブジェクト作成
			Statement stmt = (Statement)con.createStatement();
			//SQL文の実行とデータの取得
			rs = stmt.executeQuery("SELECT id,pass FROM test");

			while(rs.next()){
				text += "ID : "+rs.getString(1) +" , Pass : "+ rs.getString(2)+"\n";
			}
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			text = "接続エラーです";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
	}
}

