package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity implements AsyncTaskCallback{
	private ConnectDB cDB;
	private String id;
	private String pass;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_sign_up);
		
		//�{�^���I�u�W�F�N�g�擾(�߂�)
		Button button1 = (Button)findViewById(R.id.sign_up_button);
		button1.setOnClickListener(new ButtonClickListener());
		
		//�O�̃A�N�e�B�r�e�B����ID�����擾
		Intent i = getIntent();
		id = i.getStringExtra("ID");
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			//�p�X���[�h����͗�����2�̃p�X���[�h���擾
			EditText editText1 = (EditText)findViewById(R.id.sign_up_password);
			EditText editText2 = (EditText)findViewById(R.id.sign_up_password_2);
			String password1 = editText1.getText().toString();
			String password2 = editText2.getText().toString();
				
			//2�̃p�X���[�h������������v����΃f�[�^�x�[�X�ɓo�^
			if(isCorrectPass(password1) && isCorrectPass(password2) && password1.equals(password2)){
				pass = password1;
				cDB = new ConnectDB(SignUp.this,"UPDATE test SET pass = \""+password1+"\" WHERE id = \""+id+"\";",SignUp.this);
				cDB.execute();
			}
			//�������Ȃ���΃_�C�A���O��\��
			else{
				new AlertDialog.Builder(SignUp.this)
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
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
	            return false;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
	
	@Override
	public void preExecute() {}

	@Override
	public void postExecute(String result) {
		String r = cDB.getResult();
		if(r==null){
			new AlertDialog.Builder(SignUp.this)
			.setTitle("�o�^���������܂����B���O�C����ʂɈڍs���܂��B")
			.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
			.setNegativeButton("�m�F",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(SignUp.this,Login.class);
		            i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
		            i.putExtra("PASS", pass);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
		            startActivity(i);
				}
			}).show();
		}
		else {
			new AlertDialog.Builder(SignUp.this)
			.setTitle("�ڑ��G���[�ł��BNFC�ǂݎ���ʂɈڍs���܂��B")
			.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
			.setNegativeButton("�͂�",new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
		            Intent i = new Intent(SignUp.this,ReadNfcActivity.class);
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
