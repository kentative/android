package com.raytheon.hello;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Lab01Activity extends Activity implements OnClickListener {
	
	private EditText nameEditText;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_getter);
        
        
        Button submitBtn = (Button) this.findViewById(R.id.submitBtn);
        nameEditText = (EditText) this.findViewById(R.id.nameEditText);
        submitBtn.setOnClickListener(this);
    }


	@Override
	public void onClick(View v) {

		String name = nameEditText.getText().toString();
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra(ExtraId.UserName.name(), name);
		startActivity(intent);
		
	}
    

}
