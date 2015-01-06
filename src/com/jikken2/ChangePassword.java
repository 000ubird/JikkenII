package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePassword extends Activity implements AsyncTaskCallback{
	private ConnectDB cDB;
	private String id;
	private String pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_change_password);
		
		//�O�̃A�N�e�B�r�e�B����ID�ƃp�X���[�h���擾
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		
		TextView loginId = (TextView) findViewById(R.id.change_pass_id);
		loginId.setText(id);
		loginId.setTextColor(0xffff0000);
		
		//�{�^���I�u�W�F�N�g�擾(�߂�)
		Button button1 = (Button)findViewById(R.id.change_pass_button);
		button1.setOnClickListener(new ButtonClickListener());
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			//�p�X���[�h����͗�����2�̃p�X���[�h���擾
			EditText editText0 = (EditText)findViewById(R.id.change_pass_current_pass);
			EditText editText1 = (EditText)findViewById(R.id.change_pass_new_1);
			EditText editText2 = (EditText)findViewById(R.id.change_pass_new_2);
			String currentPass = editText0.getText().toString();
			String password1 = editText1.getText().toString();
			String password2 = editText2.getText().toString();
			
			
			//2�̃p�X���[�h������������v����΃f�[�^�x�[�X�ɓo�^
			if(isCorrectPass(password1) && isCorrectPass(password2) && password1.equals(password2) && currentPass.equals(pass)){
				pass = password1;
				cDB = new ConnectDB(ChangePassword.this,"UPDATE test SET pass = \""+password1+"\" WHERE id = \""+id+"\";",ChangePassword.this);
				cDB.execute();
			}
			//�������Ȃ���΃_�C�A���O��\��
			else{
				new AlertDialog.Builder(ChangePassword.this)
				.setMessage("�������p�X���[�h����͂��ĉ������B")
				.setCancelable(false)
				.setPositiveButton("�m�F", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).show();
			}
		}
	}
	
	/**
	 * �p�X���[�h�����������𔻒肷��
	 * @param password ���肵�����p�X���[�h
	 * @return �����̕����񂪔��p�p������4�����ȏ�32�����ȓ��Ȃ�^
	 */
	private static boolean isCorrectPass(String password){
		Pattern p = Pattern.compile("^[0-9a-zA-Z]+$");
		Matcher m = p.matcher(password);
		//���p�p������4�����ȏ�32�����ȓ��Ȃ�^
		if(password.length() >= 4 && password.length() <= 32 && m.find()){
			return true;
		}
		else {
			 return false;
		}
	}
	
	@Override
	public void preExecute() {}

	@Override
	public void postExecute(String result) {
		String r = cDB.getResult();
		if(r==null){
			new AlertDialog.Builder(ChangePassword.this)
			.setTitle("�o�^���������܂����B���C�����j���[�ɖ߂�܂��B")
			.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
			.setNegativeButton("�m�F",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(ChangePassword.this,MainMenu.class);
		            i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
		            i.putExtra("PASS", pass);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
		            startActivity(i);
				}
			}).show();
		}
		else {
			new AlertDialog.Builder(ChangePassword.this)
			.setTitle("�ڑ��G���[�ł��BNFC�ǂݎ���ʂɈڍs���܂��B")
			.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
			.setNegativeButton("�͂�",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(ChangePassword.this,ReadNfcActivity.class);
		            startActivity(i);
				}
			}).show();
		}
	}

	@Override
	public void progressUpdate(int progress) {}

	@Override
	public void cancel() {}
	
}
