package com.jikken2;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;

public class ConnectionActivity extends Activity implements AsyncTaskCallback{
	private ConnectDB cDB;
	private String id = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_connection);
		
		//前のアクティビティからID情報を取得
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		
		//SQL文を実行
		//NFCのID情報に対応するパスワードを取得する
		cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+id+"';",this);
		//sql = "INSERT into test(id,pass) values (\""+i.getStringExtra("ID")+"\",\"\");";	//デバッグ
		//cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+"user3"+"';",this);	//デバッグ
		cDB.execute();	//バックグラウンド処理開始
	}

	public void preExecute() { }
	//バックグラウンド処理完了後の処理
	public void postExecute(String result) {
		String r = cDB.getResult();
		if(r.equals("接続エラーです")){
			new AlertDialog.Builder(ConnectionActivity.this)
			.setTitle("接続エラーです。NFC読み取り画面に移行します。")
			.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
			.setNegativeButton("はい", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
					//NFC読み取りアクティビティに遷移
		            Intent i = new Intent(ConnectionActivity.this,ReadNfcActivity.class);
		            startActivity(i);
				}
			}).show();
		}
		else {
			//パスワード情報が無かった場合はパスワード登録画面に遷移
			if(r.equals("")){
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("パスワードが登録されていません。パスワード登録画面に移行します。")
				.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
				.setNegativeButton("はい", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,SignUp.class);
			            i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
			            startActivity(i);
					}
				}).show();
			}
			//パスワード情報が有った場合はログイン画面に遷移
			else{
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("ログイン画面に移行します。")
				.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
				.setNegativeButton("はい", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,Login.class);
			            i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
			            startActivity(i);
					}
				}).show();
			}
		}
	}
	public void progressUpdate(int progress) { }
	public void cancel() { }
}
