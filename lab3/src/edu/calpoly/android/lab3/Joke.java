package edu.calpoly.android.lab3;

/**
 * 
 * This class encapsulates the data pertaining to a Joke.
 */
public class Joke {

	/** The three possible rating values for jokes **/
	public enum Rating {
		Unrated,
		Like,
		Dislike
	}
	
	/** Contains the text of this joke **/
	private String joke;
	
	/** Contains the rating of this joke, should only be one of the constant rating values declared above. **/
	private Rating rating;
	
	/**
	 * Initializes a joke with an empty string and the default rating of 
	 * UNRATED.
	 */
	public Joke() {
		this("", Rating.Unrated);
	}
	
	/**
	 * Initializes a joke with the string passed in and the default rating of UNRATED.
	 * @param joke Joke String used to initialize the text of this joke.
	 */
	public Joke(String joke) {
		this(joke, Rating.Unrated);
	}
	
	/**
	 * Initializes a joke with the string and rating values passed in.
	 * 
	 * @param joke	Joke String used to initialize the text of this joke.
	 * @param rating	Rating value to initialize the rating of this joke.
	 */
	public Joke(String joke, Rating rating) {
		this.joke = joke;
		this.rating = rating;
	}
	
	/**
	 * Get the text of this joke.
	 * @return	A String value containing the text of this joke.
	 */
	public String getJoke() {
		return joke;
	}

	/**
	 * Set the text of this joke.
	 * @param joke The text of this joke.
	 */
	public void setJoke(String joke) {
		this.joke = joke;
	}

	/**
	 * Get the rating of this joke.
	 * @return the ordinal value of the joke's rating.
	 */
	public Rating getRating() {
		
		return rating;
	}
	
	/**
	 * Mutator that changes the rating of this joke.
	 * @param rating The rating ordinal corresponding to the joke's rating.
	 */
	public void setRating(int rating) {
		this.rating = Rating.values()[rating];
	}

	/**
	 * Mutator that changes the rating of this joke.
	 * @param rating The rating ordinal corresponding to the joke's rating.
	 */
	public void setRating(Rating rating) {
		this.rating = rating;
	}

	
	/**
	 * Returns only the text of the joke. This method should mimic getJoke().
	 * @return	A string containing the text of the joke
	 */
	@Override
	public String toString() {
		return joke;
	}
	
	/**
	 * An Object is equal to this Joke, if the Object is a Joke and its text is
	 * the same as this Joke's text. Text equality is defined by 
	 * String.equals(...). The rating is ignored in the determination of 
	 * equality.
	 * 
	 * @return	True if the object passed in is a Joke with the same text as
	 * 			this one; False otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joke) {
			return joke.equalsIgnoreCase(((Joke) obj).getJoke()); 
		}
		return false;
	}
}
