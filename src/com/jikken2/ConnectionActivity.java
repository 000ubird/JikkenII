package com.jikken2;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_connection);
		
		//IDm�����g�[�X�g�\��
		Intent i = getIntent();
		Toast.makeText(getApplicationContext(),
			i.getStringExtra("ID"), Toast.LENGTH_SHORT)
			.show();
		ConnectDB cDB = new ConnectDB(ConnectionActivity.this);
		cDB.execute();
	}
}
