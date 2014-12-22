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
	private static int TIMEOUT = 5;		//’PˆÊ:•b
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
	//Activiyï¿½Ö‚ÌƒRï¿½[ï¿½ï¿½ï¿½oï¿½bï¿½Nï¿½pinterface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	/**
	 * ƒAƒNƒeƒBƒrƒeƒB‚ğˆø”‚Éæ‚Á‚½ƒRƒ“ƒXƒgƒ‰ƒNƒ^
	 * @param act  ƒAƒNƒeƒBƒrƒeƒB
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	/**
	 * ƒAƒNƒeƒBƒrƒeƒB‚ÆÀs‚·‚éSQL•¶‚ğˆø”‚Éæ‚é
	 * @param act  ƒAƒNƒeƒBƒrƒeƒB
	 * @param sql  Às‚·‚éSQL•¶
	 */
	public ConnectDB(Activity act,String sql){
		this.act = act;
		this.sql = sql;
	}
	
	public String getResult(){
		return result;
	}
	
	@Override
	protected void onPreExecute() {
	    // ƒvƒƒOƒŒƒXƒ_ƒCƒAƒƒO‚Ì¶¬
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("’ÊM’†...");  // ƒƒbƒZ[ƒW‚ğƒZƒbƒg
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBCƒhƒ‰ƒCƒo‚Ìƒ[ƒh
			Class.forName("com.mysql.jdbc.Driver");
			//ƒ^ƒCƒ€ƒAƒEƒgİ’è
			DriverManager.setLoginTimeout(TIMEOUT);
			//ƒf[ƒ^ƒx[ƒX‚ÉÚ‘±
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			ResultSet rs = null;

			//ƒXƒe[ƒgƒƒ“ƒgƒIƒuƒWƒFƒNƒgì¬
			Statement stmt = (Statement)con.createStatement();
			//SQL•¶‚ÌÀs‚Æƒf[ƒ^‚Ìæ“¾
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
			text = "Ú‘±ƒGƒ‰[‚Å‚·";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
		// ƒvƒƒOƒŒƒXƒ_ƒCƒAƒƒO‚ğ•Â‚¶‚é
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}

