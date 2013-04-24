package com.packtpub.deliverydroid;

import android.app.ListActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class TheBurgerPlaceActivity extends ListActivity  {
	@Override
	public void onCreate (Bundle icicle)
	{
		super.onCreate(icicle);
		setListAdapter(new BurgerAdapter( new Burger("Plain old Burger"), new Burger("Cheese Burger"), new Burger("Chicken Burger"),
				new Burger("Breakfast Burger"), new Burger("Hawaiian Burger"), new Burger("Fish Burger"), new Burger("Vegatarian Burger"), 
				new Burger("Lamb Burger"), new Burger("Rare Tuna Steak Burger")));
		Log.d("tst","BurgerPlaceonCreate");
}
@Override	
protected void onListItemClick (ListView parent, View item, int index, long id)
{
	BurgerAdapter burgers = (BurgerAdapter)parent.getAdapter();
	Burger burger = (Burger)burgers.getItem(index);
	burger.count++;
	burgers.notifyDataSetInvalidated();
}
@Override
public void onPause() {
	super.onPause();
	Log.v("tst", "BurgerPlaceOnPause");
}
@Override
public void onResume() {
	super.onResume();
	Log.v("tst","BurgerPlaceOnResume");
}
public void onStop() {
	super.onStop();
	Log.v("tst","BurgerPlaceOnStop");
}
public void onStart() {
	super.onStart();
	Log.v("tst","BurgerPlaceOnStart");
}
public void onDestroy() {
	super.onDestroy();
	Log.v("tst", "BurgerPlaceOnDestroy");
}
}
