package ksl.training.android.lab02;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Resources;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private List<CheckBox> toppingCheckBoxes; 
	private static int orderId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 
		toppingCheckBoxes = new ArrayList<CheckBox>();
		toppingCheckBoxes.add((CheckBox) findViewById(R.id.toppingAnchovies));
		toppingCheckBoxes.add((CheckBox) findViewById(R.id.toppingMushroom));
		toppingCheckBoxes.add((CheckBox) findViewById(R.id.toppingPepperoni));
		toppingCheckBoxes.add((CheckBox) findViewById(R.id.toppingVeggies));
		
		// static, put this in application class?
		orderId = 0;
		
		Button btn = (Button) findViewById(R.id.submitBtn);
		btn.setOnClickListener(this);
		
		EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
				 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		CustomerInfo customerInfo = getCustomerInfo();
		PizzaInfo pizzaInfo = getPizzaInfo();
		String toppings = pizzaInfo.getToppings();
		Resources resc = getResources();

		if (customerInfo.getName().length() == 0 || customerInfo.getPhone().length() == 0) {
			Toast.makeText(getApplicationContext(), R.string.error01, Toast.LENGTH_LONG).show();
			return;
		}
		
		if (toppings == null) {
			toppings = resc.getString(R.string.topping_none);
		}
		
		String shape = pizzaInfo.getShape();
		if (shape == null) {
			shape = resc.getString(R.string.shape_round);
		}
		
		String cheese = pizzaInfo.getCheese();
		if (cheese == null) {
			cheese = resc.getString(R.string.base_cheese_none);
		}
		
		/*
		 * Format:
		 * Pizza Order <orderId> 
		 * 
		 * Name: <name> (<phone>)
		 * 
		 * Pizza Details
		 * <round> pizza with <cheese> and <pepperoni,veggies> topping(s).
		 */
		String pizzaOrder = String.format(
			"Pizza Order: %1$d \nName: %2$s (%3$s) \nPizza details \n%4$s with %5$s and %6$s.", 
				
				orderId++,                        // 1
				customerInfo.getName(),           // 2
				customerInfo.getPhone(),          // 3
				shape,                            // 4 
				cheese,                           // 5
				toppings);                        // 6
		
		
		Toast.makeText(getApplicationContext(), 
				pizzaOrder, 
				Toast.LENGTH_LONG).show();
	}
	
	private CustomerInfo getCustomerInfo() {
		
		CustomerInfo customer = new CustomerInfo();
		EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
		customer.setName(nameEditText.getText().toString());
		
		EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
		customer.setPhone(phoneEditText.getText().toString());
		
		return customer;
	}
	
	private PizzaInfo getPizzaInfo() {
		
		PizzaInfo pizza = new PizzaInfo();
		
		String shape = getCheckedValue((RadioGroup) findViewById(R.id.shapeRadioGroup));
		if (shape != null) {
			pizza.setShape(shape);
		}
		
		String cheese = getCheckedValue((RadioGroup) findViewById(R.id.cheeseRadioGroup));
		if (cheese != null) {
			pizza.setCheese(cheese);
		}
		
		for (CheckBox chkBox : toppingCheckBoxes) {
			if (chkBox.isChecked()){ 
				pizza.addTopping(chkBox.getText().toString());
			}
		}
		
		return pizza;
		
	}
	
	/**
	 * 
	 * @param radioGroup
	 * @return
	 */
	private String getCheckedValue(RadioGroup radioGroup) {
		
		String checkedValue = null;
		int checkedRbId = radioGroup.getCheckedRadioButtonId();
		if (checkedRbId != -1) {
			RadioButton checkedRb = (RadioButton) findViewById(checkedRbId);
			checkedValue = checkedRb.getText().toString();
		}
		return checkedValue;
	}

}
