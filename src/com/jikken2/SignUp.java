package com.jikken2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
			EditText et = (EditText)findViewById(R.id.sign_up_password);
			String password = et.toString();
		}
	}
	
}
