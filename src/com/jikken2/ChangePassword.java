package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ChangePassword extends Activity {
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
		/*
		//�{�^���I�u�W�F�N�g�擾(�߂�)
		Button button1 = (Button)findViewById(R.id.sign_up_button2);
		//button1.setOnClickListener(new ButtonClickListener());*/
	}
	
}
