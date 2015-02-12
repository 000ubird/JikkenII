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

public class MainMenu extends Activity {
	private String id;		//前のアクティビティから受け取るID情報
	private String pass;	//前のアクティビティから受け取るパスワード情報
	
	protected void onCreate(Bundle savedInstanceState) {
		//スーパークラスのonCreateメソッド呼び出し
		super.onCreate(savedInstanceState);
		//レイアウト設定ファイルの指定
		setContentView(R.layout.activity_main_menu);
		
		//前のアクティビティからIDとパスワードを取得
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		
		Button button1 = (Button)findViewById(R.id.main_menu_point_Confirm_button);
		button1.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button)findViewById(R.id.main_menu_delay_info_button);
		button2.setOnClickListener(new ButtonClickListener());
		Button button3 = (Button)findViewById(R.id.main_menu_point_add_button);
		button3.setOnClickListener(new ButtonClickListener());
		Button button4 = (Button)findViewById(R.id.main_menu_point_no_sleep_button);
		button4.setOnClickListener(new ButtonClickListener());
		Button button5 = (Button)findViewById(R.id.main_menu_change_pass_button);
		button5.setOnClickListener(new ButtonClickListener());
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				new AlertDialog.Builder(MainMenu.this)
				.setMessage("アプリを終了しますか？")
				.setCancelable(false)
				.setPositiveButton("はい", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						moveTaskToBack(true);
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
	
	//クリックリスナ定義
	class ButtonClickListener implements OnClickListener{
		//ボタンクリック時イベントハイドラ
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_menu_point_Confirm_button:
				Intent i1 = new Intent(MainMenu.this,TotalRideDistance.class);
	            i1.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
	            i1.putExtra("PASS", pass);	//パスワードを次のアクティビティに渡す
				startActivity(i1);
				break;
			case R.id.main_menu_delay_info_button:
				Intent i2 = new Intent(MainMenu.this,DelayInfo.class);
				startActivity(i2);
				break;
			case R.id.main_menu_point_add_button:
				Intent i3 = new Intent(MainMenu.this,Password.class);
	            i3.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
	            i3.putExtra("PASS", pass);	//パスワードを次のアクティビティに渡す
				startActivity(i3);
				break;
			case R.id.main_menu_point_no_sleep_button:
				Intent i4 = new Intent(MainMenu.this,PastPrevention.class);
				startActivity(i4);
				break;
			case R.id.main_menu_change_pass_button:
				Intent i = new Intent(MainMenu.this,ChangePassword.class);
	            i.putExtra("ID",id);	//ID情報を次のアクティビティに渡す
	            i.putExtra("PASS", pass);	//パスワードを次のアクティビティに渡す
				startActivity(i);
			default:
				break;
			}
		}
	}
}
