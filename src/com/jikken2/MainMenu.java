package com.jikken2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				new AlertDialog.Builder(MainMenu.this)
				.setMessage("�A�v�����I�����܂����H")
				.setCancelable(false)
				.setPositiveButton("�͂�", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						moveTaskToBack(true);
					}
				})
				.setNegativeButton("������", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				})
				.show();
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
	
	//�N���b�N���X�i��`
	class ButtonClickListener implements OnClickListener{
		//�{�^���N���b�N���C�x���g�n�C�h��
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_menu_point_Confirm_button:
				Intent i1 = new Intent(MainMenu.this,TotalRideDistance.class);
	            i1.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
	            i1.putExtra("PASS", pass);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
				startActivity(i1);
				break;
			case R.id.main_menu_delay_info_button:
				Intent i2 = new Intent(MainMenu.this,DelayInfo.class);
				startActivity(i2);
				break;
			case R.id.main_menu_point_add_button:
				Intent i3 = new Intent(MainMenu.this,Password.class);
	            i3.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
	            i3.putExtra("PASS", pass);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
				startActivity(i3);
				break;
			case R.id.main_menu_point_no_sleep_button:
				Intent i4 = new Intent(MainMenu.this,PastPrevention.class);
				startActivity(i4);
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
