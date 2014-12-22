package com.jikken2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Password extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
	}

	public void ClickButton(View view){
		EditText edit01 = (EditText)findViewById(R.id.editText1);
		if(edit01.getText().toString().equals("password")){
        Intent i = new Intent(Password.this,PointReduction.class);
        startActivity(i);
		}else{
			Toast.makeText(this,"îFèÿé∏îs",Toast.LENGTH_LONG).show();
		}
	}
	
}
