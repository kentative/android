package ksl.training.android.lab02;

import java.util.ArrayList;
import java.util.List;

public class PizzaInfo {

	
	private String shape;
	private String cheese;
	private List<String> toppings;

	public PizzaInfo() {
	
		toppings = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param topping
	 */
	public boolean addTopping(String topping) {
		
		if (!toppings.contains(topping)) {
			toppings.add(topping);
			return true;
		}
		
		return false;
	}


	/**
	 * @return the shape
	 */
	public String getShape() {
		return shape;
	}


	/**
	 * @param shape the shape to set
	 */
	public void setShape(String shape) {
		this.shape = shape;
	}


	/**
	 * @return the cheese
	 */
	public String getCheese() {
		return cheese;
	}


	/**
	 * @param cheese the cheese to set
	 */
	public void setCheese(String cheese) {
		this.cheese = cheese;
	}


	/**
	 * @return the toppings as a list of strings
	 */
	public String getToppings() {
		
		if ((toppings.size() == 0)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (String topping : toppings) {
			sb.append(topping + ", ");
		}
		
		return sb.substring(0, sb.length()-2);
	}
}
