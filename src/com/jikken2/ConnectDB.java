package com.jikken2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;

@SuppressLint("NewApi")
public class ConnectDB extends AsyncTask<Void, Void, String> {
	private static String URL = "jdbc:mysql://172.29.139.104/db_group_a";
	private static String USER = "group_a";
	private static String PASS = "m4we6baq";
	private static int TIMEOUT = 5;
	Activity act = null;

	/**
	 * �A�N�e�B�r�e�B�������Ɏ�����R���X�g���N�^
	 * @param act  �A�N�e�B�r�e�B
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	@Override
	protected void onPreExecute() {
		//TextView tv = (TextView)this.act.findViewById(R.id.result1);
		//tv.setText("�ʐM��");
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
			rs = stmt.executeQuery("SELECT id,pass FROM test");

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
		//TextView tv = (TextView)this.act.findViewById(R.id.result1);
		//tv.setText(result);
	}
}
