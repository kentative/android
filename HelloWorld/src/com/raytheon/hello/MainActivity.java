package com.raytheon.hello;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button byeBtn = (Button) findViewById(R.id.byeBtn);
        byeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, Lab01Activity.class));
			}
			
		});
        
        // Greets user
        Bundle extras = this.getIntent().getExtras();
        Object username = extras.get(ExtraId.UserName.name());
        
        Resources resc = getResources();
        if (username == null || username.toString().length() == 0) {
        	username = resc.getString(R.string.unknown_user);
        }
        
        TextView greetingsTextView = (TextView) this.findViewById(R.id.greetingsTextView);
        greetingsTextView.setText(
    		resc.getString(R.string.greetings) + " " + username);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        return true;
    }
}
