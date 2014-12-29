package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
			EditText et = (EditText)findViewById(R.id.sign_up_password);
			String password = et.toString();
		}
	}
	
	/**
	 * �ｿｽp�ｿｽX�ｿｽ�ｿｽ�ｿｽ[�ｿｽh�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�判定す�ｿｽ�ｿｽ
	 * @param password �ｿｽ�ｿｽ�ｿｽ閧ｵ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽp�ｿｽX�ｿｽ�ｿｽ�ｿｽ[�ｿｽh
	 * @return �ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾌ包ｿｽ�ｿｽ�ｿｽ�ｿｽ�が費ｿｽ�ｿｽp�ｿｽp�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ4�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾈ擾ｿｽ32�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾈ難ｿｽﾈゑｿｽ^
	 */
	private static boolean isCorrectPass(String password){
		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher m = p.matcher(password);
		//�ｿｽ�ｿｽ�ｿｽp�ｿｽp�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽ4�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾈ擾ｿｽ32�ｿｽ�ｿｽ�ｿｽ�ｿｽ�ｿｽﾈ難ｿｽﾈゑｿｽ^
		if(password.length() >= 4 && password.length() <= 32 && m.find()){
			return true;
		}
		else {
			 return false;
		}
	}
}
