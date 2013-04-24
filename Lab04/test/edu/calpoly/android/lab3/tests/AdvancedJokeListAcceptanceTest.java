package edu.calpoly.android.lab3.tests;

import java.util.ArrayList;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.calpoly.android.lab3.AdvancedJokeListStub;
import edu.calpoly.android.lab3.Joke;
import edu.calpoly.android.lab3.JokeListAdapter;
import edu.calpoly.android.lab3.JokeView;
import edu.calpoly.android.lab3.tests.FriendClass.FriendClassException;

public class AdvancedJokeListAcceptanceTest extends ActivityInstrumentationTestCase2<AdvancedJokeListStub> {

	private Instrumentation mInstrument = null;
	
	// AdvancedJokeList member variables
	private AdvancedJokeListStub mActivity = null;
	private Button jokeButton = null;
	private ViewGroup jokeLayout = null;
	private EditText jokeEditText = null;
	private ArrayList<Joke> jokeList = null;
	private JokeListAdapter jokeAdapter;
	
	public AdvancedJokeListAcceptanceTest() {
		super("edu.calpoly.android.lab3", AdvancedJokeListStub.class);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();

		mInstrument = getInstrumentation();
		
		// Setup Activity
//		setActivityInitialTouchMode(false);
		mActivity = getActivity();

		// Gather references to Activity member variables
		try {
			jokeButton = FriendClass.retrieveHiddenMember("jokeButton", jokeButton, mActivity);
			jokeLayout = FriendClass.retrieveHiddenMember("jokeLayout", jokeLayout, mActivity);
			jokeEditText = FriendClass.retrieveHiddenMember("jokeEditText", jokeEditText, mActivity);
			jokeList = FriendClass.retrieveHiddenMember("jokeList", jokeList, getActivity());
			jokeAdapter = FriendClass.retrieveHiddenMember("jokeAdapter", jokeAdapter, getActivity());
		} catch (FriendClassException exc) {fail(exc.getMessage());}
	}

	@SmallTest
	/**
	 * Testing Lab 3
	 * Preconditions to Section 1
	 */
	public void testSec1PreConditions() {
		assertNotNull("jokeEditText should not be null", jokeEditText);
		assertNotNull("jokeButton should not be null", jokeButton);

		assertNotNull("jokeList should not be null", jokeList);
		assertEquals("jokeList should be pre-populated with 3 default jokes", 3, jokeList.size());
		
		assertNotNull("m_vwjokeLayout should not be null", jokeLayout);
		assertEquals("jokeLayout should be pre-populated with 3 default jokes", 3, jokeLayout.getChildCount());
		
		View view = jokeLayout.getChildAt(1);
		if (view instanceof TextView) {
			assertEquals("Checking text size",16.0,((TextView)view).getTextSize(),.001);
		}
		
		for (int ndx = 0; ndx < jokeLayout.getChildCount(); ndx++) {
			if (!(jokeLayout.getChildAt(ndx) instanceof TextView) &&
			    !(jokeLayout.getChildAt(ndx) instanceof JokeView)) {
				fail("jokeLayout should contain only TextView (if you haven't implemented the JokeView class) or JokeView objects and nothing else.");
			}
		}
		assertEquals("Check Button Text","Add Joke",jokeButton.getText());
	}
	
	@SmallTest
	/**
	 * Testing Lab 3
	 * Section 1.3 test jokeButton OnClickListenr
	 */
	public void testAddJokeViaButton() {
		final String jokeText = "Testing addJoke via Add Button...";

		// Add the joke
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText(jokeText);
				jokeButton.performClick();
			}
		});
		mInstrument.waitForIdleSync();
		testForAddedJoke(jokeText);		
	}
	
	@SmallTest
	/**
	 * Testing Lab 3
	 * Section 1.3 test jokeEditText OnKeyListener
	 */
	public void testAddJokeViaReturn() {
		final String jokeText = "Testing addJoke via return key...";

		// Add the joke
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText(jokeText);
			}
		});
		mInstrument.waitForIdleSync();
		sendKeys(KeyEvent.KEYCODE_ENTER);
		mInstrument.waitForIdleSync();
		testForAddedJoke(jokeText);		
	}
	
	@SmallTest
	/**
	 * Testing Lab 3
	 * Section 1.3 test jokeEditText OnKeyListener
	 */
	public void testAddJokeViaDPadTrackBall() {
		final String jokeText = "Testing addJoke via D-Pad Center & Track-Ball...";

		// Add the joke
		mActivity.runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText(jokeText);
			}
		});
		mInstrument.waitForIdleSync();
		sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		mInstrument.waitForIdleSync();
		testForAddedJoke(jokeText);		
	}
	
	private void testForAddedJoke(String jokeText) {
		assertEquals("jokeList should have 4 jokes now", 4, jokeList.size());
		assertEquals("jokeLayout should have 4 jokes now", 4, jokeLayout.getChildCount());
		assertEquals("Checking index of joke, that it was added to the end of jokeList", jokeText, jokeList.get(3).getJoke());
		
		// App under test could be using JokeViews or TextViews at this point
		View view = jokeLayout.getChildAt(3);
		if (view instanceof TextView) {
			TextView tv = (TextView)view;
			assertEquals("Checking index of joke, that it was added to the end of jokeLayout", jokeText, tv.getText().toString());
		}
		else if (view instanceof JokeView) {
			JokeView jokeView = (JokeView)view;
			Joke joke = null;
			try {
				joke = FriendClass.retrieveHiddenMember("m_joke", joke, jokeView);
			} catch (FriendClassException exc) {fail(exc.getMessage());}
			assertEquals("Checking index of joke, that it was added to the end of jokeLayout", jokeText, joke.getJoke());
		}
		else {
			fail("jokeLayout should contain only TextView (if you haven't implemented the JokeView class) or JokeView objects and nothing else.");
		}
	}
}
