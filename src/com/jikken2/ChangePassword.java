package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePassword extends Activity implements AsyncTaskCallback{
	private ConnectDB cDB;
	private String id;
	private String pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_change_password);
		
		//前のアクティビティからIDとパスワードを取得
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		
		TextView loginId = (TextView) findViewById(R.id.change_pass_id);
		loginId.setText(id);
		loginId.setTextColor(0xffff0000);
		
		//ボタンオブジェクト取得(戻る)
		Button button1 = (Button)findViewById(R.id.change_pass_button);
		button1.setOnClickListener(new ButtonClickListener());
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			//パスワードを入力欄から2つのパスワードを取得
			EditText editText0 = (EditText)findViewById(R.id.change_pass_current_pass);
			EditText editText1 = (EditText)findViewById(R.id.change_pass_new_1);
			EditText editText2 = (EditText)findViewById(R.id.change_pass_new_2);
			String currentPass = editText0.getText().toString();
			String password1 = editText1.getText().toString();
			String password2 = editText2.getText().toString();
			
			
			//2つのパスワードが正しいかつ一致すればデータベースに登録
			if(isCorrectPass(password1) && isCorrectPass(password2) && password1.equals(password2) && currentPass.equals(pass)){
				pass = password1;
				cDB = new ConnectDB(ChangePassword.this,"UPDATE test SET pass = \""+password1+"\" WHERE id = \""+id+"\";",ChangePassword.this);
				cDB.execute();
			}
			//正しくなければダイアログを表示
			else{
				new AlertDialog.Builder(ChangePassword.this)
				.setMessage("正しいパスワードを入力して下さい。")
				.setCancelable(false)
				.setPositiveButton("確認", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
			}
		}
	}
	
	/**
	 * パスワードが正しいかを判定する
	 * @param password 判定したいパスワード
	 * @return 引数の文字列が半角英数かつ4文字以上32文字以内なら真
	 */
	private static boolean isCorrectPass(String password){
		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher m = p.matcher(password);
		//半角英数かつ4文字以上32文字以内なら真
		if(password.length() >= 4 && password.length() <= 32 && m.find()){
			return true;
		}
		else {
			 return false;
		}
	}
	
	@Override
	public void preExecute() {}

	@Override
	public void postExecute(String result) {
		String r = cDB.getResult();
		if(r==null){
			new AlertDialog.Builder(ChangePassword.this)
			.setTitle("登録が完了しました。メインメニューに戻ります。")
			.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
			.setNegativeButton("確認",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(ChangePassword.this,MainMenu.class);
		            i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
		            i.putExtra("PASS", pass);	//パスワードを次のアクティビティに渡す
		            startActivity(i);
				}
			}).show();
		}
		else {
			new AlertDialog.Builder(ChangePassword.this)
			.setTitle("接続エラーです。NFC読み取り画面に移行します。")
			.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
			.setNegativeButton("はい",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(ChangePassword.this,ReadNfcActivity.class);
		            startActivity(i);
				}
			}).show();
		}
	}

	@Override
	public void progressUpdate(int progress) {}

	@Override
	public void cancel() {}
	
}
