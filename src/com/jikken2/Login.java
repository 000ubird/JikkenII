package com.jikken2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	private String id;		//前のアクティビティから受け取るID情報
	private String pass;	//前のアクティビティから受け取るパスワード情報
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_login);
		
		//ボタンオブジェクト取得(戻る)
		Button button1 = (Button)findViewById(R.id.login_button);
		button1.setOnClickListener(new ButtonClickListener());
		
		//前のアクティビティからID情報を取得しテキストビューに表示
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		TextView loginId = (TextView) findViewById(R.id.login_text_id);
		loginId.setText(id);
		loginId.setTextColor(0xffff0000);
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			EditText editText1 = (EditText)findViewById(R.id.editText1);
			String password = editText1.getText().toString();
			
			if(password.equals(pass)){
				new AlertDialog.Builder(Login.this)
				.setTitle("ログイン成功")
				.setCancelable(false)	//ダイアログ以外の場所のタッチは無効
				.setNegativeButton("確認",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(Login.this,MainMenu.class);
			            i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
			            i.putExtra("PASS", pass);	//パスワードを次のアクティビティに渡す
			            startActivity(i);
					}
				}).show();
			}
			else{
				Toast.makeText(Login.this, "パスワードが正しくありません", Toast.LENGTH_LONG).show();	
			}
		}
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				new AlertDialog.Builder(Login.this)
				.setMessage("NFC読み取り画面に移動しますか？")
				.setCancelable(false)
				.setPositiveButton("はい", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent i = new Intent(Login.this,ReadNfcActivity.class);
						startActivity(i);
					}
				})
				.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				})
				.show();
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
}

