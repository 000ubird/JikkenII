package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
			Toast.makeText(Login.this, pass, Toast.LENGTH_LONG).show();
		}
	}
}

