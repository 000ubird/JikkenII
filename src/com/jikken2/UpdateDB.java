package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class UpdateDB extends AsyncTask<Void, Void, String> {
	
	private static String URL = "172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	
	private static int TIMEOUT = 5;		//�P��:�b
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
	//Activity�ւ̃R�[���o�b�N�pinterface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	/**
	 * �A�N�e�B�r�e�B�������Ɏ�����R���X�g���N�^
	 * @param act  �A�N�e�B�r�e�B
	 */
	public UpdateDB(Activity act){
		this.act = act;
	}
	
	/**
	 * �A�N�e�B�r�e�B�Ǝ��s����SQL���������Ɏ��
	 * @param act  �A�N�e�B�r�e�B
	 * @param sql  ���s����SQL��
	 */
	public UpdateDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	/**
	 * �A�N�e�B�r�e�B�Ǝ��s����SQL���������Ɏ��
	 * @param act  �A�N�e�B�r�e�B
	 * @param sql  ���s����SQL��
	 */
	public UpdateDB(Activity act,String sql,AsyncTaskCallback _callback){
		this.act = act;
		this.sql = sql;
	    this.callback = _callback;
	}
	
	public String getResult(){
		return result;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
        callback.preExecute();
        
	    // �v���O���X�_�C�A���O�̐���
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("�ʐM��...");  // ���b�Z�[�W���Z�b�g
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBC�h���C�o�̃��[�h
			Class.forName("com.mysql.jdbc.Driver");
			//�^�C���A�E�g�ݒ�
			DriverManager.setLoginTimeout(TIMEOUT);
			//�f�[�^�x�[�X�ɐڑ�
			Connection con = DriverManager.getConnection("jdbc:mysql://"+URL,USER,PASS);
			//�X�e�[�g�����g�I�u�W�F�N�g�쐬
			Statement stmt = con.createStatement();
			//SQL���̎��s(�X�V)
				stmt.executeUpdate(sql);		
			
			stmt.close();
			con.close();
			
		} catch(Exception e) {
			text = "�ڑ��G���[�ł�";
			result = "�ڑ��G���[�ł�";
		}
		return text;	
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
        
		// �v���O���X�_�C�A���O�����
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}