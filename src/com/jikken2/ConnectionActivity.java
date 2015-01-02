package com.jikken2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jikken2.ConnectDB.AsyncTaskCallback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;

@SuppressLint("NewApi")
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
		final String r = cDB.getResult();
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
				//�N���ς݂Ȃ烍�O�C�����ȗ����邩���_�C�A���O�ŕ\��
				if(isLaunch("")){
					new AlertDialog.Builder(ConnectionActivity.this)
					.setTitle("�������O�C�������܂����H")
					.setPositiveButton("�͂�",new DialogInterface.OnClickListener() {
						@Override
						//�͂����N���b�N���ꂽ��g�b�v��ʂɑJ�ڂ���
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(ConnectionActivity.this,"�������O�C�����܂���", Toast.LENGTH_LONG).show();
							Intent i = new Intent(ConnectionActivity.this,MainMenu.class);
							i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
							i.putExtra("PASS", r);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
							startActivity(i);
						}
					})
					.setNegativeButton("������",new DialogInterface.OnClickListener() {
						@Override
						//���������N���b�N���ꂽ�烍�O�C����ʂɑJ�ڂ���
						public void onClick(DialogInterface dialog, int which) {
							Intent i = new Intent(ConnectionActivity.this,Login.class);
							i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
							i.putExtra("PASS", r);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
							startActivity(i);
						}
					}).show();
				}
				else{
					new AlertDialog.Builder(ConnectionActivity.this)
					.setTitle("���O�C����ʂɈڍs���܂��B")
					.setCancelable(false)	//�_�C�A���O�ȊO�̏ꏊ�̃^�b�`�͖���
					.setNegativeButton("�͂�", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which){
							Intent i = new Intent(ConnectionActivity.this,Login.class);
							i.putExtra("ID",id);	//ID�������̃A�N�e�B�r�e�B�ɓn��
							i.putExtra("PASS", r);	//�p�X���[�h�����̃A�N�e�B�r�e�B�ɓn��
							startActivity(i);
						}
					}).show();
				}
			}
		}
	}
	public void progressUpdate(int progress) { }
	public void cancel() { }
	
	/**
	 * assets�f�B���N�g������launch.txt�t�@�C����ǂݍ��݁A
	 * ������ID�ɑΉ�����ID���{�A�v�����N���ς݂��ǂ�����Ԃ�
	 * @param _id ���ׂ���ID
	 * @return	�N���ς݂Ȃ�true�A����N���Ȃ�false
	 */
	private boolean isLaunch(String _id){
		InputStream is = null;
		BufferedReader br = null;
		String str;
		String[] strAry = null;
		_id = "user1";	//�f�o�b�O�p
		try {
			// assets�t�H���_���� log.txt ���I�[�v������
			is = this.getAssets().open("launch.txt");
			br = new BufferedReader(new InputStreamReader(is));

			while ((str = br.readLine()) != null) {
				strAry = str.split(",");		//�ǂݍ��ޕ�����̏����́uID,�N���̗L���v
				if(_id.equals(strAry[0])){		//������ID�Ɠǂݍ��񂾃��O��ID����v�����烋�[�v�𔲂���
					//Toast.makeText(this, strAry[0]+" -> "+strAry[1], Toast.LENGTH_LONG).show();
					break;
				}
			}
			is.close();
			br.close();
			
			//ID�ɑΉ�����N���̗L�����utrue�v��������^��Ԃ�
			if(strAry[1].equals("true")) return true;
			else return false;
		} catch (Exception e){
			Toast.makeText(this,"�N�����O�̓ǂݍ��݂Ɏ��s���܂���", Toast.LENGTH_LONG).show();
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
}
