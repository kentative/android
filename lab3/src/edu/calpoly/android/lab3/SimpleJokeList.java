package edu.calpoly.android.lab3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user **/
	protected ArrayList<Joke> jokeList;

	/** LinearLayout used for maintaining a list of Views that each display Joke. **/
	protected LinearLayout jokeLayout;

	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.  
	 */
	protected int[] rowColors;

	/**
	 * Button used for creating and adding a new Joke to joke list using the 
	 * text entered in jokeEditText.
	 **/
	protected Button jokeButton;

	/** EditText used for entering text for a new Joke to be added to joke list. **/
	protected EditText jokeEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initLayout();
		initAddJokeListeners();

		// Load jokes from resource file
		jokeList = new ArrayList<Joke>();
		Resources resc = getResources();
		rowColors = new int[2];
		rowColors[0] = resc.getColor(R.color.dark);
		rowColors[1] = resc.getColor(R.color.light);
		String[] jokes = resc.getStringArray(R.array.jokeList);
		
		for (int i = 0; i < jokes.length; i++) {
			addJoke(jokes[i]);
		}
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity. 
	 */
	protected void initLayout() {
		
		// Main Layout
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		
		// Add Joke Layout (button and edit text view)
		LinearLayout addJokeLayout = new LinearLayout(this);
		addJokeLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		// Joke Button
		jokeButton = new Button(this);
		jokeButton.setText("Add Joke");
		addJokeLayout.addView(jokeButton);
		
		// Joke Edit Text
		jokeEditText = new EditText(this);
		jokeEditText.setLayoutParams(new LayoutParams(
			LayoutParams.MATCH_PARENT, 
			LayoutParams.WRAP_CONTENT));
		addJokeLayout.addView(jokeEditText);

		jokeLayout = new LinearLayout(this);
		jokeLayout.setOrientation(LinearLayout.VERTICAL);
		ScrollView scrollView = new ScrollView(this);
		scrollView.addView(jokeLayout);
		
		mainLayout.addView(addJokeLayout);
		mainLayout.addView(scrollView);
		setContentView(mainLayout);
		
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the 
	 * list. 
	 */
	protected void initAddJokeListeners() {

		jokeEditText.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {
			
					case KeyEvent.KEYCODE_ENTER:
					case KeyEvent.KEYCODE_DPAD_CENTER:
						addJokeFromView(jokeEditText);
						return true;
					}
				}
				
				return false;
			}
		});
		
		jokeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addJokeFromView(jokeEditText);
			}
		});

	}
	
	/**
	 * 
	 * @param jokeEditText
	 */
	private void addJokeFromView(EditText jokeEditText) {
		
		String jokeText = jokeEditText.getText().toString();
		if (jokeText == null || jokeText.length() == 0) {
			return;
		}
		
		addJoke(jokeText);
		jokeEditText.getText().clear();
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(jokeEditText.getWindowToken(), 0);
		
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to the JokeList, and display it on screen.
	 * 
	 * @param jokeText A string containing the text of the Joke to add.
	 */
	private void addJoke(String jokeText) {

		Joke joke = new Joke(jokeText);
		if (jokeList.contains(joke)) {
			return;
		}
		
		jokeList.add(joke);
		TextView jokeTextView = new TextView(this);
		jokeTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 16);
		jokeTextView.setBackgroundColor(
			rowColors[jokeList.size()%2]);
		jokeTextView.setText(joke.getJoke());
		jokeLayout.addView(jokeTextView);
		
	}
}