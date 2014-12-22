package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ConnectDB extends AsyncTask<Void, Void, String> {
	private static int TIMEOUT = 5;		//単位:秒
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
	//Activiyへのコールバック用interface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }AsyncTaskCallback
    
	/**
	 * アクティビティを引数に取ったコンストラクタ
	 * @param act  アクティビティ
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	/**
	 * アクティビティと実行するSQL文を引数に取る
	 * @param act  アクティビティ
	 * @param sql  実行するSQL文
	 */
	public ConnectDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	/**
	 * アクティビティと実行するSQL文を引数に取る
	 * @param act  アクティビティ
	 * @param sql  実行するSQL文
	 */
	public ConnectDB(Activity act,String sql,AsyncTaskCallback _callback){
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
			Connection con = DriverManager.getConnection(URL,USER,PASS);

			//ステートメントオブジェクト作成
			Statement stmt = (Statement)con.createStatement();
			//SQL文の実行とデータの取得
			String[] strAry = sql.split(" ");
			
			//SQL文の最初の命令文で条件分岐
			switch(strAry[0]){
			case "SELECT" : 
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					result += rs.getString(1)+"\n";
					text += rs.getString(1);
				}
				break;
			case "INSERT" :
				stmt.executeUpdate(sql);
				break;
			case "UPDATE" : 
				stmt.executeUpdate(sql);
				break;
			}
			
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			text = "接続エラーです";
			result = "接続エラーです";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
		// プログレスダイアログを閉じる
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}

