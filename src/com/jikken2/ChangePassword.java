package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ChangePassword extends Activity {
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
		/*
		//ボタンオブジェクト取得(戻る)
		Button button1 = (Button)findViewById(R.id.sign_up_button2);
		//button1.setOnClickListener(new ButtonClickListener());*/
	}
	
}
