package com.jikken2;

import android.app.Activity;
import android.os.Bundle;

public class MainMenu extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_main_menu);
	}
}
