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
	private static int TIMEOUT = 5;		//PΚ:b
	private Activity act = null;
	private String sql = "";
	private ProgressDialog dialog;
	private ResultSet rs = null;
	private String result = "";
	private AsyncTaskCallback callback = null;;
	
	//ActiviyοΏ½ΦΜRοΏ½[οΏ½οΏ½οΏ½oοΏ½bοΏ½NοΏ½pinterface
    public interface AsyncTaskCallback {
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }
    
	/**
	 * ANeBreBπψΙζΑ½RXgN^
	 * @param act  ANeBreB
	 */
	public ConnectDB(Activity act){
		this.act = act;
	}
	
	/**
	 * ANeBreBΖΐs·ιSQLΆπψΙζι
	 * @param act  ANeBreB
	 * @param sql  ΐs·ιSQLΆ
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
	    // vOX_CAOΜΆ¬
        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("ΚM...");  // bZ[WπZbg
        this.dialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		String text = "";
		try {
			//JDBChCoΜ[h
			Class.forName("com.mysql.jdbc.Driver");
			//^CAEgέθ
			DriverManager.setLoginTimeout(TIMEOUT);
			//f[^x[XΙΪ±
			Connection con = DriverManager.getConnection(URL,USER,PASS);
			ResultSet rs = null;

			//Xe[ggIuWFNgμ¬
			Statement stmt = (Statement)con.createStatement();
			//SQLΆΜΐsΖf[^ΜζΎ
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
			text = "Ϊ±G[Ε·";
		}
		return text;
	}
	
	protected void onPostExecute(String result){
		TextView tv = (TextView)this.act.findViewById(R.id.textView1);
		tv.setText(result);
		// vOX_CAOπΒΆι
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
	}
}

