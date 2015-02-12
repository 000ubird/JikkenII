package com.jikken2;


import android.os.AsyncTask;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@SuppressLint("NewApi")
public class ConnectDBDist extends AsyncTask<Void, Void, String>{

    private static String URL = "172.29.139.104/db_group_a";
    private static String USER = "group_a";
    private static String PASS = "m4we6baq";

    private static int TIMEOUT = 5;
    private Activity act = null;
    private String sql = "";
    private ProgressDialog dialog;
    private ResultSet rs = null;
    private String result = "";
    public double totalDistance = 0.0;
    private AsyncTaskCallback callback = null;
    public int loopCounter = 1;
    public int pointBalance = 0;
    public int newPointBalance = 0;
    private static int JAPAN_LOOP = 12000;
    private static int BONUS = 500;
    private String id;


    public interface AsyncTaskCallback{
        void preExecute();
        void postExecute(String result);
        void progressUpdate(int progress);
        void cancel();
    }


    public ConnectDBDist(Activity act){
        this.act = act;
    }

    public ConnectDBDist(Activity act,String sql){
        this.act = act;
        this.sql = sql;
    }

    public ConnectDBDist(Activity act,String sql,AsyncTaskCallback _callback){
        this.act = act;
        this.sql = sql;
        this.callback = _callback;
    }

    public String getResult(){
        return result;
    }

    public double getTotalDistance(){
        return totalDistance;
    }

    public int getLoopCounter(){
        return loopCounter;
    }

    public int getPointBalance(){
        return pointBalance;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        callback.preExecute();

        this.dialog = new ProgressDialog(this.act);
        this.dialog.setMessage("Now Loading...");
        this.dialog.show();
    }

    @Override
    protected String doInBackground(Void... arg0){
       String text = "";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.setLoginTimeout(TIMEOUT);
            Connection con = DriverManager.getConnection("jdbc:mysql://"+URL,USER,PASS);

            Statement stmt = con.createStatement();
//            String[] strAry = sql.split("");

//            switch (strAry[0]) {
//                case "SELECT" :
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        id = rs.getString("id");
                        totalDistance = rs.getDouble("totalDistance");
                        pointBalance = rs.getInt("pointBalance");
                        loopCounter = rs.getInt("loopCounter");
//                    }
//                    break;

//                case "UPDATE":
//                    stmt.executeUpdate(sql);
//                    break;
            }
            rs.close();
            stmt.close();
            con.close();
        }catch (Exception e){
            //text = e.getMessage()
            text = "Connect Error";
            result = "Connect Error";
        }
        return text;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
        callback.postExecute(result);

        if(this.dialog != null && this.dialog.isShowing()){
            this.dialog.dismiss();
        }
    }
}
