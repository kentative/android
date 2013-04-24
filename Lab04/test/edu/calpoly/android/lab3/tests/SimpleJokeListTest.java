package edu.calpoly.android.lab3.tests;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.calpoly.android.lab3.Joke;
import edu.calpoly.android.lab3.AdvancedJokeList;

public class SimpleJokeListTest extends ActivityInstrumentationTestCase2<AdvancedJokeList> {
	
	// Where's the TypeOf method in java?
	private ArrayList<Joke> jokeListType = null;
	private EditText jokeEditTextType  = null;
	private Button jokeButtonType  = null;
	private LinearLayout jokeLayoutType = null;

	public SimpleJokeListTest() {
		super(AdvancedJokeList.class);
	}

	@SmallTest
	public void testAddJokeViaButton() {


		Activity activity = getActivity();
		final ArrayList<Joke> jokeList = this.retrieveHiddenMember("jokeList", jokeListType, activity);
		final EditText jokeEditText = this.retrieveHiddenMember("jokeEditText", jokeEditTextType, activity);
		final Button jokeButton = this.retrieveHiddenMember("jokeButton", jokeButtonType, activity);
		assertEquals("Should be 3 default jokes", 3, jokeList.size());
		activity.runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText("This is a test joke");
				jokeButton.performClick();
			}
		});
		
		// wait for the request to go through
		getInstrumentation().waitForIdleSync();
		assertEquals("Should be 4 jokes now", 4, jokeList.size());
		assertEquals("Ensure the joke we added is really there","This is a test joke", jokeList.get(3).getJoke());	
		LinearLayout jokeLayout = null;
		jokeLayout = this.retrieveHiddenMember("jokeLayout", jokeLayout, getActivity());
		assertEquals("Should be 4 joke views", 4, jokeLayout.getChildCount());
		TextView tv = (TextView) jokeLayout.getChildAt(3);
		assertEquals("Text view should also have the new joke","This is a test joke",tv.getText());
	}
	
	@SmallTest
	public void testAddJokeViaReturn() {
		
		final ArrayList<Joke> jokeList = this.retrieveHiddenMember("jokeList", jokeListType, getActivity());
		final EditText jokeEditText = this.retrieveHiddenMember("jokeEditText", jokeEditTextType, getActivity());
		assertEquals("Should be 3 default jokes", 3, jokeList.size());
		
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText("This is a second test joke");
			}
		});
		sendKeys(KeyEvent.KEYCODE_ENTER);
		assertEquals("Should be 4 jokes now",4,jokeList.size());
		assertEquals("Ensure the joke we added is really there","This is a second test joke",jokeList.get(3).getJoke());	
		final LinearLayout jokeLayout = this.retrieveHiddenMember("jokeLayout", jokeLayoutType, getActivity());
		assertEquals("Should be 4 joke views",4,jokeLayout.getChildCount());
		TextView tv = (TextView)jokeLayout.getChildAt(3);
		assertEquals("Text view should also have the new joke","This is a second test joke",tv.getText());
	}

	@SmallTest
	public void testAddJokeViaDPadTrackBall() {
		
		final ArrayList<Joke> jokeList = this.retrieveHiddenMember("jokeList", jokeListType, getActivity());
		final EditText jokeEditText = this.retrieveHiddenMember("jokeEditText", jokeEditTextType, getActivity());
		assertEquals("Should be 3 default jokes",3,jokeList.size());
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				jokeEditText.setText("This is a third test joke");
			}
		});
		
		sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
		assertEquals("Should be 4 jokes now",4,jokeList.size());
		assertEquals("Ensure the joke we added is really there","This is a third test joke",jokeList.get(3).getJoke());	

		final LinearLayout jokeLayout = this.retrieveHiddenMember("jokeLayout", jokeLayoutType, getActivity());
		assertEquals("Should be 4 joke views",4,jokeLayout.getChildCount());
		TextView tv = (TextView)jokeLayout.getChildAt(3);
		assertEquals("Text view should also have the new joke","This is a third test joke",tv.getText());
	}

	@SmallTest
	public void testAddJokeButtonText() {
		final Button jokeButton = this.retrieveHiddenMember("jokeButton", jokeButtonType, getActivity());
		assertEquals("Check Button Text","Add Joke",jokeButton.getText());
	}

	@SmallTest
	public void testTextViewSize() {
		LinearLayout jokeLayout = null;
		jokeLayout = this.retrieveHiddenMember("jokeLayout",jokeLayout,getActivity());
		TextView tv = (TextView)jokeLayout.getChildAt(1);
		assertEquals("Check text size",16.0,tv.getTextSize(),.001);
	}

	/*************************************/
	/**	Java Friend-Class Helper Method **/
	/*************************************/
	@SuppressWarnings("unchecked")
	public <T> T retrieveHiddenMember(String memberName, T type, Object sourceObj) {
		Field field = null;
		T returnVal = null;
		//Test for proper existence
		try {
			field = sourceObj.getClass().getDeclaredField(memberName);
		} catch (NoSuchFieldException e) {
			fail("The field \"" + memberName + "\" was renamed or removed. Do not rename or remove this member variable.");
		}
		field.setAccessible(true);
		
		//Test for proper type
		try {
			returnVal = (T)field.get(sourceObj);
		} catch (ClassCastException exc) {
			fail("The field \"" + memberName + "\" had its type changed. Do not change the type on this member variable.");
		}  
		
		// Boiler Plate Exception Checking. If any of these Exceptions are 
		// throw it was because this method was called improperly.
		catch (IllegalArgumentException e) {
			fail ("This is an Error caused by the UnitTest!\n Improper user of retrieveHiddenMember(...) -- IllegalArgumentException:\n Passed in the wrong object to Field.get(...)");
		} catch (IllegalAccessException e) {
			fail ("This is an Error caused by the UnitTest!\n Improper user of retrieveHiddenMember(...) -- IllegalAccessException:\n Field.setAccessible(true) should be called.");
		}
		return returnVal; 
	}


}
