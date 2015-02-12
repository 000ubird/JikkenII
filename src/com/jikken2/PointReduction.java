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
		text.setText("���Ȃ��̃|�C���g�c���F"+pointBalance+"�|�C���g");
		
		
		TextView text1 = (TextView)findViewById(R.id.textView4);
		text1.setText("���Ȃ��̃`���[�W�c���F"+chargeBalance+"�~");
		
		//�O�̃A�N�e�B�r�e�B����ID�ƃp�X���[�h���擾
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
		
		/*�I�����ꂽ�|�C���g�Ҍ��z���擾����*/
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
        String setPoint = (String)spinner1.getSelectedItem();
        int point_i = Integer.parseInt(setPoint);
        
        final int subPointBalance = pointBalance-point_i;        
        final int addChargeBalance = chargeBalance + point_i;
        
        
if(subPointBalance < 0){
	AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
    alertDlg.setTitle("�G���[");
    alertDlg.setMessage("�|�C���g�c��������܂���");

    alertDlg.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	// OK �{�^���N���b�N����
            }
        });
 
    // �\��
    alertDlg.create().show();

}else if(addChargeBalance > 20000){
	AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
    alertDlg.setTitle("�G���[");
    alertDlg.setMessage("�`���[�W�c��������𒴂��Ă��܂��B");

    alertDlg.setPositiveButton(
        "OK",
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // OK �{�^���N���b�N����
            }
        });
    // �\��
    alertDlg.create().show();
	
}else{
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
        alertDlg.setTitle("�m�F");
        alertDlg.setMessage("�|�C���g�c���F"+pointBalance+"��"+subPointBalance+"�|�C���g\n"+"�`���[�W�c���F"+chargeBalance+"��"+addChargeBalance+"�~");

        alertDlg.setPositiveButton(
            "OK",
            new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // OK �{�^���N���b�N���� 	
                	//�p�X���[�h���L�[�Ƃ��āA�|�C���g�c���ƃ`���[�W�c�����X�V
                	//�{�Ԃł�pass->password(?)
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
                    // Cancel �{�^���N���b�N����
                }
            });

        // �\��
        alertDlg.create().show();
		
    }
	}

	@Override
	public void preExecute() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void postExecute(String result) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
		String r = uDB.getResult();
			
		if(r.equals("�ڑ��G���[�ł�")){
			Toast.makeText(getApplicationContext(),
					"�ʐM���s��"+r, Toast.LENGTH_SHORT)
					.show();
		}
		else {
			Toast.makeText(getApplicationContext(),
					"�����p���肪�Ƃ��������܂���", Toast.LENGTH_SHORT)
					.show();
			finish();
			
		}
		
	}

	@Override
	public void progressUpdate(int progress) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void cancel() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}
}
