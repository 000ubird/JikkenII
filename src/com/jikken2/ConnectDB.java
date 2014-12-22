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
	private static int TIMEOUT = 5;		//�P��:�b
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
	//Activiy�ւ̃R�[���o�b�N�pinterface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }AsyncTaskCallback
    
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
	
	/**
	 * �A�N�e�B�r�e�B�Ǝ��s����SQL���������Ɏ��
	 * @param act  �A�N�e�B�r�e�B
	 * @param sql  ���s����SQL��
	 */
	public ConnectDB(Activity act,String sql,AsyncTaskCallback _callback){
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
			Connection con = DriverManager.getConnection(URL,USER,PASS);

			//�X�e�[�g�����g�I�u�W�F�N�g�쐬
			Statement stmt = (Statement)con.createStatement();
			//SQL���̎��s�ƃf�[�^�̎擾
			String[] strAry = sql.split(" ");
			
			//SQL���̍ŏ��̖��ߕ��ŏ�������
			switch(strAry[0]){
			case "SELECT" : 
				rs = stmt.executeQuery(sql);
				while(rs.next()){
					result += rs.getString(1)+"\n";
					text += rs.getString(1);
				}
				break;
			case "INSERT" :
				stmt.executeUpdate(sql);
				break;
			case "UPDATE" : 
				stmt.executeUpdate(sql);
				break;
			}
			
			rs.close();
			stmt.close();
			con.close();
		} catch(Exception e) {
			//text = e.getMessage();
			text = "�ڑ��G���[�ł�";
			result = "�ڑ��G���[�ł�";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		super.onPostExecute(result);
        callback.postExecute(result);
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
		// �v���O���X�_�C�A���O�����
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}

