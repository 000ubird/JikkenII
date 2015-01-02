package com.jikken2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

@SuppressLint("NewApi")
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
		final String r = cDB.getResult();
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
				//起動済みならログインを省略するかをダイアログで表示
				if(isLaunch("")){
					new AlertDialog.Builder(ConnectionActivity.this)
					.setTitle("自動ログインをしますか？")
					.setPositiveButton("はい",new DialogInterface.OnClickListener() {
						@Override
						//はいがクリックされたらトップ画面に遷移する
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(ConnectionActivity.this,"自動ログインしました", Toast.LENGTH_LONG).show();
							Intent i = new Intent(ConnectionActivity.this,MainMenu.class);
							i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
							i.putExtra("PASS", r);	//パスワードを次のアクティビティに渡す
							startActivity(i);
						}
					})
					.setNegativeButton("いいえ",new DialogInterface.OnClickListener() {
						@Override
						//いいえがクリックされたらログイン画面に遷移する
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent(ConnectionActivity.this,Login.class);
							i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
							i.putExtra("PASS", r);	//パスワードを次のアクティビティに渡す
							startActivity(i);
						}
					}).show();
				}
				else{
					new AlertDialog.Builder(ConnectionActivity.this)
					.setTitle("ログイン画面に移行します。")
					.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
					.setNegativeButton("はい", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which){
							Intent i = new Intent(ConnectionActivity.this,Login.class);
							i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
							i.putExtra("PASS", r);	//パスワードを次のアクティビティに渡す
							startActivity(i);
						}
					}).show();
				}
			}
		}
	}
	public void progressUpdate(int progress) { }
	public void cancel() { }
	
	/**
	 * assetsディレクトリ内のlaunch.txtファイルを読み込み、
	 * 引数のIDに対応するIDが本アプリを起動済みかどうかを返す
	 * @param _id 調べたいID
	 * @return	起動済みならtrue、初回起動ならfalse
	 */
	private boolean isLaunch(String _id){
		InputStream is = null;
		BufferedReader br = null;
		String str;
		String[] strAry = null;
		_id = "user1";	//デバッグ用
		try {
			// assetsフォルダ内の log.txt をオープンする
			is = this.getAssets().open("launch.txt");
			br = new BufferedReader(new InputStreamReader(is));

			while ((str = br.readLine()) != null) {
				strAry = str.split(",");		//読み込む文字列の書式は「ID,起動の有無」
				if(_id.equals(strAry[0])){		//引数のIDと読み込んだログのIDが一致したらループを抜ける
					//Toast.makeText(this, strAry[0]+" -> "+strAry[1], Toast.LENGTH_LONG).show();
					break;
				}
			}
			is.close();
			br.close();
			
			//IDに対応する起動の有無が「true」だったら真を返す
			if(strAry[1].equals("true")) return true;
			else return false;
		} catch (Exception e){
			Toast.makeText(this,"起動ログの読み込みに失敗しました", Toast.LENGTH_LONG).show();
			return false;
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	            return false;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
}
