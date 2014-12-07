package com.jikken2;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_connection);
		
		//IDm情報をトースト表示
		Intent i = getIntent();
		Toast.makeText(getApplicationContext(),
			i.getStringExtra("ID"), Toast.LENGTH_SHORT)
			.show();
		ConnectDB cDB = new ConnectDB(ConnectionActivity.this);
		cDB.execute();
	}
}
