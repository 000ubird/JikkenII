package com.jikken2;

import com.jikken2.InsertDB.AsyncTaskCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity implements AsyncTaskCallback{
	private InsertDB iDB;
	private String id;		//前のアクティビティから受け取るID情報
	private String pass;	//前のアクティビティから受け取るパスワード情報
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);		
		
		//前のアクティビティからIDとパスワードを取得
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
	}

	public void ClickButton(View view){
		
		//パスワード入力
		EditText edit01 = (EditText)findViewById(R.id.editText1);
		String input = edit01.getText().toString();
		
		//passをキーとして、他の利用者情報を取得
		//本番ではpass->password(?)
		iDB = new InsertDB(Password.this,"SELECT password,pointBalance,chargeBalance FROM userinfo WHERE id = '"+id+"';",this);
		iDB.execute();
		
	}

	@Override
	public void preExecute() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void postExecute(String result) {
		// TODO 自動生成されたメソッド・スタブ
		//AsyncTaskの結果を受け取ってなんかする

				String r = iDB.getResult();//データベースから結果(今回の場合はパスワード)を取得
				String p = iDB.getPointBalance();//データベースからポイント残高を取得
				String c = iDB.getChargeBalance();//データベースからチャージ残高
				
				if(r.equals("接続エラーです")){
					Toast.makeText(getApplicationContext(),
							"通信失敗→"+r, Toast.LENGTH_SHORT)
							.show();
				}else if(r.equals("")){
					Toast.makeText(getApplicationContext(),
							"パスワードが違います", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					Toast.makeText(getApplicationContext(),
							"ポイント還元画面に移動します...", Toast.LENGTH_SHORT)
							.show();
								
		            Intent i = new Intent(Password.this,PointReduction.class);
		            
		         // intentへ添え字付で値を保持させる
		            i.putExtra("password", String.valueOf(r));
		            i.putExtra("ID", id);
					i.putExtra("pointBalance", Integer.parseInt(p));
					i.putExtra("chargeBalance", Integer.parseInt(c));					
					// 指定のActivityを開始する
		            startActivity(i);
				}
	}

	@Override
	public void progressUpdate(int progress) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void cancel() {
		// TODO 自動生成されたメソッド・スタブ
		
	}
	
}
