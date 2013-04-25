package edu.calpoly.android.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.calpoly.android.lab3.Joke.Rating;
import edu.calpoly.android.lab3.Joke.ViewState;

public class JokeView extends RelativeLayout implements OnCheckedChangeListener, OnClickListener, Checkable {

	private Button expandButton;
	private RadioButton likeButton;
	private RadioButton dislikeButton;
	private RadioGroup likeGroup;
	private TextView jokeText;
	private Joke joke;

	public static final String EXPAND = " + ";
	public static final String COLLAPSE = " - ";

	/**
	 * Basic Constructor that takes only takes in an application Context.
	 * 
	 * @param context The application Context in which this view is being added.             
	 * @param joke The Joke this view is responsible for displaying.
	 */
	public JokeView(Context context, Joke joke) {
		 super(context);
		 
		 LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 inflater.inflate(R.layout.jokeview, this, true); 
		 
		 likeButton = (RadioButton) findViewById(R.id.likeButton);
		 dislikeButton = (RadioButton) findViewById(R.id.dislikeButton);
		 jokeText = (TextView) findViewById(R.id.jokeTextView);		 
		 expandButton = (Button) findViewById(R.id.expandButton);
		 expandButton.setOnClickListener(this);		 
		 likeGroup = (RadioGroup) findViewById(R.id.ratingRadioGroup);
		 likeGroup.setOnCheckedChangeListener(this);
		 setJoke(joke);		 
		 
	}

	/**
	 * Mutator method for changing the Joke object this View displays. This View
	 * will be updated to display the correct contents of the new Joke.
	 * @param joke The Joke object which this View will display.
	 */
	public void setJoke(Joke joke) {
		
		this.joke = joke;
		jokeText.setText(joke.getJoke());		
		
		// Set ratings		
		switch (joke.getRating()) {
			case Dislike:
				likeButton.setChecked(false);
				dislikeButton.setChecked(true);				
				break;
				
			case Like:
				likeButton.setChecked(true);
				dislikeButton.setChecked(false);
				break;
				
			case Unrated:
			default:
				likeButton.setChecked(false);
				dislikeButton.setChecked(false);
				break;
		}
		
		collapseJokeView();
	}

	/**
	 * This method encapsulates the logic necessary to update this view so that
	 * it displays itself in its "Expanded" form: 
	 * 	- Shows the complete text of the joke. 
	 *  - Brings the RadioGroup of rating Buttons back into view.
	 */
	private void expandJokeView() {
		joke.setViewState(ViewState.Expanded);
		jokeText.setSingleLine(false);
		likeGroup.setVisibility(VISIBLE);
		expandButton.setText(COLLAPSE);
		requestLayout();
	}

	/**
	 * This method encapsulates the logic necessary to update this view so that
	 * it displays itself in its "Collapsed" form: 
	 * 	- Shows only the first line of text of the joke. 
	 *  - If the joke is longer than one line, it appends an ellipsis to the end. 
	 *  - Removes the RadioGroup of rating Buttons from view.
	 */
	private void collapseJokeView() {		
		joke.setViewState(ViewState.Collapsed);
		jokeText.setSingleLine(true);
		likeGroup.setVisibility(INVISIBLE);
		expandButton.setText(EXPAND);
		requestLayout();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		if (checkedId == likeButton.getId()) {
			joke.setRating(Rating.Like);
		} else if (checkedId == dislikeButton.getId()) {
			joke.setRating(Rating.Dislike);					
		}
		
	}

	@Override
	public void onClick(View v) {
		toggle();
	}

	@Override
	public boolean isChecked() {
		
		return (joke.getViewState() == ViewState.Expanded);
	}

	@Override
	public void setChecked(boolean checked) {
		
		if (checked) {
			expandJokeView();
		} else {
			collapseJokeView();
		}		
	}

	@Override
	public void toggle() {
		
		// Toggle view state
		if (joke.getViewState() == ViewState.Collapsed){
			expandJokeView();
		} else {
			collapseJokeView();
		}	
	}
}
