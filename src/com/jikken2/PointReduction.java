package com.jikken2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class PointReduction extends Activity {
	String pointBalance = "5000";
	String chargeBalance = "15000";
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_point_reduction);
		
		
		TextView text = (TextView)findViewById(R.id.textView3);
		text.setText("あなたのポイント残高："+pointBalance+"ポイント");
		
		
		TextView text1 = (TextView)findViewById(R.id.textView4);
		text1.setText("あなたのチャージ残高："+chargeBalance+"円");
		
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
        
        int pointBalance_i = Integer.parseInt(pointBalance);
        int chargeBalance_i = Integer.parseInt(chargeBalance);
        
        int subPointBalance = pointBalance_i-point_i;        
        int addChargeBalance = chargeBalance_i + point_i;
        
        
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
        alertDlg.setMessage("ポイント残高："+pointBalance+"⇒"+subPointBalance+"\n"+"チャージ残高："+chargeBalance+"⇒"+addChargeBalance);

        alertDlg.setPositiveButton(
            "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // OK ボタンクリック処理
                }
            });
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
}
