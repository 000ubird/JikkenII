package com.jikken2;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionActivity extends Activity implements AsyncTaskCallback{
	//private static String sql = "SELECT id,pass FROM test WHERE ";
	private ConnectDB cDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_connection);

		//IDm情報をトースト表示
		Intent i = getIntent();
		//sql = "INSERT into test(id,pass) values (\""+i.getStringExtra("ID")+"\",\"\");";
		cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+i.getStringExtra("ID")+"';",this);
		//cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+"user3"+"';",this);
		cDB.execute();
	}

	public void preExecute() {
		//だいたいの場合ダイアログの表示などを行う
	}
	public void postExecute(String result) {
		//AsyncTaskの結果を受け取ってなんかする
		String r = cDB.getResult();
		if(r.equals("接続エラーです")){
			new AlertDialog.Builder(ConnectionActivity.this)
			.setTitle("接続エラーです。NFC読み取り画面に移行します。")
			.setNegativeButton(
					"はい", 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which){
							finish();
						}
					})
					.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
					.show();
		}
		else {
			Toast.makeText(getApplicationContext(),
					"通信成功→"+r, Toast.LENGTH_SHORT)
					.show();
            Intent i = new Intent(ConnectionActivity.this,Password.class);
            startActivity(i);
		}
	}
	public void progressUpdate(int progress) {
		//だいたいプログレスダイアログを進める
	}
	public void cancel() {
		//キャンセルされた時になんかする
	}

}
