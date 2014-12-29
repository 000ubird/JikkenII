package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	private String id;		//�O�̃A�N�e�B�r�e�B����󂯎��ID���
	private String pass;	//�O�̃A�N�e�B�r�e�B����󂯎��p�X���[�h���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_login);
		
		//�{�^���I�u�W�F�N�g�擾(�߂�)
		Button button1 = (Button)findViewById(R.id.login_button);
		button1.setOnClickListener(new ButtonClickListener());
		
		//�O�̃A�N�e�B�r�e�B����ID�����擾���e�L�X�g�r���[�ɕ\��
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		TextView loginId = (TextView) findViewById(R.id.login_text_id);
		loginId.setText(id);
		loginId.setTextColor(0xffff0000);
	}
	
	class ButtonClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			Toast.makeText(Login.this, pass, Toast.LENGTH_LONG).show();
		}
	}
}

