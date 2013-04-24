package edu.calpoly.android.lab3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
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

public class AdvancedJokeList extends Activity {


	// Start of lab04 additions
	
	/** Contains the name of the Author for the jokes. */
	protected String authorName;
	
	/** Adapter used to bind an AdapterView to List of Jokes. */
	protected JokeListAdapter jokeAdapter;

	/**
	 * Context-Menu MenuItem ID's
	 * IMPORTANT: You must use these when creating your MenuItems or the tests
	 * used to grade your submission will fail.
	 */
	protected static final int REMOVE_JOKE_MENUITEM = Menu.FIRST;	
	protected static final int UPLOAD_JOKE_MENUITEM = Menu.FIRST + 1;
	
	// End of lab04 additions
	
	
	
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
		Joke[] jokes = null; //resc.getStringArray(R.array.jokeList);
		
		for (Joke joke : jokes) {
			addJoke(joke);
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
	 * Method used to retrieve Jokes from online server. The getJoke script
	 * takes a single optional parameter, which should be encode in "UTF-8".
	 * This parameter allows tells script to only retrieve Jokes whose author
	 * name matches the value in the parameter.
	 * 
	 * param-1) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/getJokes.php?
	 * 
	 */
	protected void getJokesFromServer() {
		// TODO
	}
	
	/**
	 * This method uploads a single Joke to the server. This method should test
	 * the response from the server and display success or failure to the user
	 * via a Toast Notification
	 * 
	 * The addJoke script on the server requires two parameters, both of which
	 * should be encode in "UTF-8":
	 * 
	 * param-1) "joke": The text of the joke.
	 * 
	 * param-2) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/addJoke.php?
	 * 
	 * @param joke
	 *            The Joke to be uploaded to the server.
	 * 
	 */
	protected void uploadJokeToServer(Joke joke) {
		// TODO
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
		
		addJoke(new Joke(jokeText));
		jokeEditText.getText().clear();
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(jokeEditText.getWindowToken(), 0);
		
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to the JokeList, and display it on screen.
	 * @deprecated
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
	
	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to the JokeList, and display it on screen.
	 * 
	 * @param joke The Joke to add.
	 */
	private void addJoke(Joke joke) {

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