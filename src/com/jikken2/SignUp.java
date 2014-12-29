package com.jikken2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_sign_up);
		
		//�{�^���I�u�W�F�N�g�擾(�߂�)
		Button button1 = (Button)findViewById(R.id.sign_up_button);
		button1.setOnClickListener(new ButtonClickListener());
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
				Toast.makeText(getApplicationContext(),"������", Toast.LENGTH_SHORT).show();
			}
			//�������Ȃ���΃_�C�A���O��\��
			else{
				new AlertDialog.Builder(SignUp.this)
				.setMessage("�p�X���[�h������������܂���B�ē��͂��ĉ������B")
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
}
