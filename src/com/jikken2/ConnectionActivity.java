package com.jikken2;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

public class ConnectionActivity extends Activity implements AsyncTaskCallback{
	//private static String sql = "SELECT id,pass FROM test WHERE ";
	private ConnectDB cDB;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//�X�[�p�[�N���X��onCreate���\�b�h�Ăяo��
		super.onCreate(savedInstanceState);
		//���C�A�E�g�ݒ�t�@�C���̎w��
		setContentView(R.layout.activity_connection);

		//IDm�����g�[�X�g�\��
		Intent i = getIntent();
		//sql = "INSERT into test(id,pass) values (\""+i.getStringExtra("ID")+"\",\"\");";
		cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+i.getStringExtra("ID")+"';",this);
		//cDB = new ConnectDB(ConnectionActivity.this,"SELECT pass FROM test WHERE id = '"+"user3"+"';",this);
		cDB.execute();
	}

	public void preExecute() {
		//���������̏ꍇ�_�C�A���O�̕\���Ȃǂ��s��
	}
	public void postExecute(String result) {
		//AsyncTask�̌��ʂ��󂯎���ĂȂ񂩂���
		String r = cDB.getResult();
		if(r.equals("�ڑ��G���[�ł�")){
			new AlertDialog.Builder(ConnectionActivity.this)
			.setTitle("�ڑ��G���[�ł��BNFC�ǂݎ���ʂɈڍs���܂��B")
			.setNegativeButton(
					"�͂�", 
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which){
							finish();
						}
					})
					.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
					.show();
		}
		else {
			//�p�X���[�h��񂪖��������ꍇ�̓p�X���[�h�o�^��ʂɑJ��
			if(r.equals("")){
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("�p�X���[�h���o�^����Ă��܂���B�p�X���[�h�o�^��ʂɈڍs���܂��B")
				.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,SignUp.class);
			            startActivity(i);
					}
				})
				.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
				.show();
			}
			//�p�X���[�h��񂪗L�����ꍇ�̓��O�C����ʂɑJ��
			else{
				new AlertDialog.Builder(ConnectionActivity.this)
				.setTitle("���O�C����ʂɈڍs���܂��B")
				.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which){
			            Intent i = new Intent(ConnectionActivity.this,SignUp.class);
			            startActivity(i);
					}
				})
				.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
				.show();
			}
		}
	}
	public void progressUpdate(int progress) {
		//���������v���O���X�_�C�A���O��i�߂�
	}
	public void cancel() {
		//�L�����Z�����ꂽ���ɂȂ񂩂���
	}

}
