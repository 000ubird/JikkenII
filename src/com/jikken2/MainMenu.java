package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity {
	private String id;		//�O�̃A�N�e�B�r�e�B����󂯎��ID���
	private String pass;	//�O�̃A�N�e�B�r�e�B����󂯎��p�X���[�h���
	
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_main_menu);
		
		//�O�̃A�N�e�B�r�e�B����ID�ƃp�X���[�h���擾
		Intent i = getIntent();	
		id = i.getStringExtra("ID");
		pass = i.getStringExtra("PASS");
		
		Button button1 = (Button)findViewById(R.id.main_menu_point_Confirm_button);
		button1.setOnClickListener(new ButtonClickListener());
		Button button2 = (Button)findViewById(R.id.main_menu_delay_info_button);
		button2.setOnClickListener(new ButtonClickListener());
		Button button3 = (Button)findViewById(R.id.main_menu_point_add_button);
		button3.setOnClickListener(new ButtonClickListener());
		Button button4 = (Button)findViewById(R.id.main_menu_point_no_sleep_button);
		button4.setOnClickListener(new ButtonClickListener());
		Button button5 = (Button)findViewById(R.id.main_menu_change_pass_button);
		button5.setOnClickListener(new ButtonClickListener());
	}
	
	//�N���b�N���X�i��`
	class ButtonClickListener implements OnClickListener{
		//�{�^���N���b�N���C�x���g�n�C�h��
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_menu_point_Confirm_button:
				break;
			case R.id.main_menu_delay_info_button:
				break;
			case R.id.main_menu_point_add_button:
				break;
			case R.id.main_menu_point_no_sleep_button:
				break;
			case R.id.main_menu_change_pass_button:
				Intent i = new Intent(MainMenu.this,ChangePassword.class);
	            i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
	            i.putExtra("PASS", pass);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
				startActivity(i);
			default:
				break;
			}
		}
	}
}