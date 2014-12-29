package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_sign_up);
		
		//ボタンオブジェクト取得(戻る)
		Button button1 = (Button)findViewById(R.id.sign_up_button);
		button1.setOnClickListener(new ButtonClickListener());
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			//パスワードを入力欄から2つのパスワードを取得
			EditText editText1 = (EditText)findViewById(R.id.sign_up_password);
			EditText editText2 = (EditText)findViewById(R.id.sign_up_password_2);
			String password1 = editText1.getText().toString();
			String password2 = editText2.getText().toString();
				
			//2つのパスワードが正しいかつ一致すればデータベースに登録
			if(isCorrectPass(password1) && isCorrectPass(password2) && password1.equals(password2)){
				Toast.makeText(getApplicationContext(),"正しい", Toast.LENGTH_SHORT).show();
			}
			//正しくなければダイアログを表示
			else{
				new AlertDialog.Builder(SignUp.this)
				.setMessage("パスワードが正しくありません。再入力して下さい。")
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
}
