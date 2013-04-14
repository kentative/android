package edu.calpoly.android.lab3;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user **/
	protected ArrayList<Joke> jokeList;

	/** LinearLayout used for maintaining a list of Views that each display Joke. **/
	protected LinearLayout jokeLayout;

	/** EditText used for entering text for a new Joke to be added to joke list. **/
	protected EditText jokeEditText;

	/**
	 * Button used for creating and adding a new Joke to joke list using the 
	 * text entered in jokeEditText.
	 **/
	protected Button jokeButton;
	
	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.  
	 */
	protected int darkColor;
	protected int lightColor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		// Load jokes from resource file
		jokeList = new ArrayList<Joke>();
		Resources resc = getResources();
		String[] jokes = resc.getStringArray(R.array.jokeList);
		for(String joke : jokes) {
			jokeList.add(new Joke(joke));
		}
		
		
		
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity. 
	 */
	protected void initLayout() {
		// TODO
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the 
	 * list. 
	 */
	protected void initAddJokeListeners() {
		// TODO
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param joke A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {
		// TODO
	}
}