package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

@SuppressLint("NewApi")
public class ConnectDB extends AsyncTask<Void, Void, String> {
	private static String URL = "jdbc:mysql://172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	private static int TIMEOUT = 5;		//�P��:�b
	Activity act = null;
	private String sql = "";
	ProgressDialog dialog;
	
	/**
	 * �A�N�e�B�r�e�B�������Ɏ�����R���X�g���N�^
	 * @param act  �A�N�e�B�r�e�B
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	/**
	 * �A�N�e�B�r�e�B�Ǝ��s����SQL���������Ɏ��
	 * @param act  �A�N�e�B�r�e�B
	 * @param sql  ���s����SQL��
	 */
	public ConnectDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	@Override
	protected void onPreExecute() {
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
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			ResultSet rs = null;

			//�X�e�[�g�����g�I�u�W�F�N�g�쐬
			Statement stmt = (Statement)con.createStatement();
			//SQL���̎��s�ƃf�[�^�̎擾
			//rs = stmt.executeQuery("SELECT id,pass FROM test");
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				text += "ID : "+rs.getString(1) +" , Pass : "+ rs.getString(2)+"\n";
			}
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			text = "�ڑ��G���[�ł�";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
		// �v���O���X�_�C�A���O�����
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}

