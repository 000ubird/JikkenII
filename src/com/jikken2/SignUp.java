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
		//ƒX[ƒp[ƒNƒ‰ƒX‚ÌonCreateƒƒ\ƒbƒhŒÄ‚Ño‚µ
		super.onCreate(savedInstanceState);
		//ƒŒƒCƒAƒEƒgÝ’èƒtƒ@ƒCƒ‹‚ÌŽw’è
		setContentView(R.layout.activity_sign_up);
		
		//ƒ{ƒ^ƒ“ƒIƒuƒWƒFƒNƒgŽæ“¾(–ß‚é)
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
	 * ï¿½pï¿½Xï¿½ï¿½ï¿½[ï¿½hï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ð”»’è‚·ï¿½ï¿½
	 * @param password ï¿½ï¿½ï¿½è‚µï¿½ï¿½ï¿½ï¿½ï¿½pï¿½Xï¿½ï¿½ï¿½[ï¿½h
	 * @return ï¿½ï¿½ï¿½ï¿½ï¿½Ì•ï¿½ï¿½ï¿½ï¿½ñ‚ª”ï¿½ï¿½pï¿½pï¿½ï¿½ï¿½ï¿½ï¿½ï¿½4ï¿½ï¿½ï¿½ï¿½ï¿½Èï¿½32ï¿½ï¿½ï¿½ï¿½ï¿½È“ï¿½È‚ï¿½^
	 */
	private static boolean isCorrectPass(String password){
		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher m = p.matcher(password);
		//ï¿½ï¿½ï¿½pï¿½pï¿½ï¿½ï¿½ï¿½ï¿½ï¿½4ï¿½ï¿½ï¿½ï¿½ï¿½Èï¿½32ï¿½ï¿½ï¿½ï¿½ï¿½È“ï¿½È‚ï¿½^
		if(password.length() >= 4 && password.length() <= 32 && m.find()){
			return true;
		}
		else {
			 return false;
		}
	}
}
