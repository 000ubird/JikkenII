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
	private String id;		//�O�̃A�N�e�B�r�e�B����󂯎��ID���
	private String pass;	//�O�̃A�N�e�B�r�e�B����󂯎��p�X���[�h���
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);		
		
		//�O�̃A�N�e�B�r�e�B����ID�ƃp�X���[�h���擾
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
	}

	public void ClickButton(View view){
		
		//�p�X���[�h����
		EditText edit01 = (EditText)findViewById(R.id.editText1);
		String input = edit01.getText().toString();
		
		//pass���L�[�Ƃ��āA���̗��p�ҏ����擾
		//�{�Ԃł�pass->password(?)
		iDB = new InsertDB(Password.this,"SELECT password,pointBalance,chargeBalance FROM userinfo WHERE id = '"+id+"';",this);
		iDB.execute();
		
	}

	@Override
	public void preExecute() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	@Override
	public void postExecute(String result) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		//AsyncTask�̌��ʂ��󂯎���ĂȂ񂩂���

				String r = iDB.getResult();//�f�[�^�x�[�X���猋��(����̏ꍇ�̓p�X���[�h)���擾
				String p = iDB.getPointBalance();//�f�[�^�x�[�X����|�C���g�c�����擾
				String c = iDB.getChargeBalance();//�f�[�^�x�[�X����`���[�W�c��
				
				if(r.equals("�ڑ��G���[�ł�")){
					Toast.makeText(getApplicationContext(),
							"�ʐM���s��"+r, Toast.LENGTH_SHORT)
							.show();
				}else if(r.equals("")){
					Toast.makeText(getApplicationContext(),
							"�p�X���[�h���Ⴂ�܂�", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					Toast.makeText(getApplicationContext(),
							"�|�C���g�Ҍ���ʂɈړ����܂�...", Toast.LENGTH_SHORT)
							.show();
								
		            Intent i = new Intent(Password.this,PointReduction.class);
		            
		         // intent�֓Y�����t�Œl��ێ�������
		            i.putExtra("password", String.valueOf(r));
		            i.putExtra("ID", id);
					i.putExtra("pointBalance", Integer.parseInt(p));
					i.putExtra("chargeBalance", Integer.parseInt(c));					
					// �w���Activity���J�n����
		            startActivity(i);
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
