package com.packtpub.deliverydroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ArrayAdapter;

public class SelectRestaurantActivity extends Activity implements OnItemClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       ListView  restaurants = (ListView)findViewById(R.id.restaurant);
       restaurants.setAdapter(new ArrayAdapter<String> (this, R.layout.menu_item,getResources().getStringArray(R.array.restaurants)));
       restaurants.setOnItemClickListener(this);
    }
   @Override
    public void onItemClick (AdapterView<?> parent, View item, int index, long id)
    {
    	switch (index) 
    	{ 
    	case 0:  startActivity(new Intent(this, TheBurgerPlaceActivity.class));
    	break;
    	}
    }
}