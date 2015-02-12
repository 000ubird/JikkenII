package com.jikken2;

import com.jikken2.UpdateDB.AsyncTaskCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class PointReduction extends Activity implements AsyncTaskCallback{
	private UpdateDB uDB;
	
	private int pointBalance;
	private int chargeBalance;
	private String password;
	private String id;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point_reduction);
		
		Bundle extras = getIntent().getExtras();
        pointBalance = extras.getInt("pointBalance");
		chargeBalance = extras.getInt("chargeBalance");
		password = extras.getString("password");
        
		TextView text = (TextView)findViewById(R.id.textView3);
		text.setText("あなたのポイント残高："+pointBalance+"ポイント");
		
		
		TextView text1 = (TextView)findViewById(R.id.textView4);
		text1.setText("あなたのチャージ残高："+chargeBalance+"円");
		
		//前のアクティビティからIDとパスワードを取得
		Intent i = getIntent();	
		id = i.getStringExtra("ID");

		initSpinners();
	}
	
	private void initSpinners(){
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		String[] labels = getResources().getStringArray(R.array.list);
		ArrayAdapter<String> adapter
        = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labels);
      spinner1.setAdapter(adapter);
       
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	
	public void ClickButton(View view){
		
		/*選択されたポイント還元額を取得する*/
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        String setPoint = (String)spinner1.getSelectedItem();
        int point_i = Integer.parseInt(setPoint);
        
        final int subPointBalance = pointBalance-point_i;        
        final int addChargeBalance = chargeBalance + point_i;
        
        
if(subPointBalance < 0){
	AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
    alertDlg.setTitle("エラー");
    alertDlg.setMessage("ポイント残高が足りません");

    alertDlg.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	// OK ボタンクリック処理
            }
        });
 
    // 表示
    alertDlg.create().show();

}else if(addChargeBalance > 20000){
	AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
    alertDlg.setTitle("エラー");
    alertDlg.setMessage("チャージ残高が上限を超えています。");

    alertDlg.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OK ボタンクリック処理
            }
        });
    // 表示
    alertDlg.create().show();
	
}else{
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("確認");
        alertDlg.setMessage("ポイント残高："+pointBalance+"⇒"+subPointBalance+"ポイント\n"+"チャージ残高："+chargeBalance+"⇒"+addChargeBalance+"円");

        alertDlg.setPositiveButton(
            "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // OK ボタンクリック処理 	
                	//パスワードをキーとして、ポイント残高とチャージ残高を更新
                	//本番ではpass->password(?)
                			uDB = new UpdateDB(PointReduction.this,
            				"UPDATE userinfo SET pointBalance ="+subPointBalance+",chargeBalance ="+addChargeBalance+
            				" WHERE id = '"+id+"';",PointReduction.this);
            		
            		uDB.execute();            		
                }
            }
            );
        alertDlg.setNegativeButton(
            "Cancel",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Cancel ボタンクリック処理
                }
            });

        // 表示
        alertDlg.create().show();
		
    }
	}

	@Override
	public void preExecute() {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void postExecute(String result) {
		// TODO 自動生成されたメソッド・スタブ
		
		String r = uDB.getResult();
			
		if(r.equals("接続エラーです")){
			Toast.makeText(getApplicationContext(),
					"通信失敗→"+r, Toast.LENGTH_SHORT)
					.show();
		}
		else {
			Toast.makeText(getApplicationContext(),
					"ご利用ありがとうございました", Toast.LENGTH_SHORT)
					.show();
			finish();
			
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
