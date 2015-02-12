package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

@SuppressLint("NewApi")
public class InsertDB extends AsyncTask<Void, Void, String> {
	
	private static String URL = "172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	
	private static int TIMEOUT = 5;		//単位:秒
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String result = "";
	private String pointBalance = "";
	private String chargeBalance = "";
	private AsyncTaskCallback callback = null;;
	
	//Activityへのコールバック用interface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	/**
	 * アクティビティを引数に取ったコンストラクタ
	 * @param act  アクティビティ
	 */
	public InsertDB(Activity act){
		this.act = act;
	}
	
	/**
	 * アクティビティと実行するSQL文を引数に取る
	 * @param act  アクティビティ
	 * @param sql  実行するSQL文
	 */
	public InsertDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	/**
	 * アクティビティと実行するSQL文を引数に取る
	 * @param act  アクティビティ
	 * @param sql  実行するSQL文
	 */
	public InsertDB(Activity act,String sql,AsyncTaskCallback _callback){
		this.act = act;
		this.sql = sql;
	    this.callback = _callback;
	}
	public String getResult(){
		return result;
	}
	
	public String getPointBalance(){
		return pointBalance;
	}
	
	public String getChargeBalance(){
		return chargeBalance;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        callback.preExecute();
        
	    // プログレスダイアログの生成
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("通信中...");  // メッセージをセット
        this.dialog.show();
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
			Connection con = DriverManager.getConnection("jdbc:mysql://"+URL,USER,PASS);

			//ステートメントオブジェクト作成
			Statement stmt = con.createStatement();
			//SQL文の実行とデータの取得
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					result += rs.getString("password");
					pointBalance += rs.getString("pointBalance");
					chargeBalance += rs.getString("chargeBalance");
				}
			
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			Toast.makeText(act,
					e.getMessage(), Toast.LENGTH_SHORT)
					.show();
			text = "接続エラーです";
			result = "接続エラーです";
		}
		return text;	
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
        
		// プログレスダイアログを閉じる
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}