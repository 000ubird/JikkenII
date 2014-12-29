package com.jikken2;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;

public class ConnectionActivity extends Activity implements AsyncTaskCallback{
	private ConnectDB cDB;
	private String id = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_connection);
		
		//�O�̃A�N�e�B�r�e�B����ID�����擾
		Intent i = getIntent();
		id = i.getStringExtra("ID");
		
		//SQL�������s
		//NFC��ID���ɑΉ�����p�X���[�h���擾����
		cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+id+"';",this);
		//sql = "INSERT into test(id,pass) values (\""+i.getStringExtra("ID")+"\",\"\");";	//�f�o�b�O
		//cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+"user3"+"';",this);	//�f�o�b�O
		cDB.execute();	//�o�b�N�O���E���h�����J�n
	}

	public void preExecute() { }
	//�o�b�N�O���E���h����������̏���
	public void postExecute(String result) {
		String r = cDB.getResult();
		if(r.equals("�ڑ��G���[�ł�")){
			new AlertDialog.Builder(ConnectionActivity.this)
			.setTitle("�ڑ��G���[�ł��BNFC�ǂݎ���ʂɈڍs���܂��B")
			.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
			.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which){
					//NFC�ǂݎ��A�N�e�B�r�e�B�ɑJ��
		            Intent i = new Intent(ConnectionActivity.this,ReadNfcActivity.class);
		            startActivity(i);
				}
			}).show();
		}
		else {
			//�p�X���[�h��񂪖��������ꍇ�̓p�X���[�h�o�^��ʂɑJ��
			if(r.equals("")){
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("�p�X���[�h���o�^����Ă��܂���B�p�X���[�h�o�^��ʂɈڍs���܂��B")
				.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
				.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,SignUp.class);
			            i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
			            startActivity(i);
					}
				}).show();
			}
			//�p�X���[�h��񂪗L�����ꍇ�̓��O�C����ʂɑJ��
			else{
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("���O�C����ʂɈڍs���܂��B")
				.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
				.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,Login.class);
			            i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
			            startActivity(i);
					}
				}).show();
			}
		}
	}
	public void progressUpdate(int progress) { }
	public void cancel() { }
}
