package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.jikken2.ConnectDBDist.AsyncTaskCallback;

public class TotalRideDistance extends Activity implements AsyncTaskCallback, OnClickListener{

    private ConnectDBDist cDB;
    public double totalDistance = 0.0;
    private String id;
    public int loopCounter = 1;
    private static int JAPAN_LOOP = 12000;
    public int pointBalance = 0;
    public int newPointBalance = 0;
    private static int BONUS = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_ride_distance);


        Bundle extras = getIntent().getExtras();
        id = extras.getString("ID");
//        id = "user5";

        cDB = new ConnectDBDist(TotalRideDistance.this, "SELECT * FROM userinfo WHERE id = '"+id+"';",this);
        cDB.execute();


        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(this);

    }


    public void postExecute(String result){

//        Bundle extras = getIntent().getExtras();
//        String userID = cDB.getResult();
        totalDistance = cDB.getTotalDistance();
        loopCounter = cDB.getLoopCounter();

        //ここから
        if(totalDistance >= loopCounter * JAPAN_LOOP && totalDistance != 0.0) {

            pointBalance = cDB.getPointBalance();
            newPointBalance = pointBalance + BONUS;

            loopCounter += 1;

//            cDB = new ConnectDBDist(TotalRideDistance.this, "UPDATE userinfo SET pointBalance = " + newPointBalance + " ,loopCounter = " + loopCounter + " WHERE id = '" + id + "';",TotalRideDistance.this);
//            cDB.execute();

            loopCounter -= 1;
            TextView text3 = (TextView)findViewById(R.id.textView4);
            text3.setText("日本"+loopCounter+"周ボーナス発生!!!\nポイント残高 "+pointBalance+"P => "+newPointBalance+"P");


        }


        TextView text1 = (TextView)findViewById(R.id.textView2);
        text1.setText("ID : "+id);

        TextView text2 = (TextView)findViewById(R.id.textView3);
        text2.setText("総乗車距離 : "+totalDistance+"km");
    }



    public void preExecute(){

    }

    public void progressUpdate(int progress){

    }

    public void cancel(){

    }

    public void onClick(View v){
        finish();
    }

}
