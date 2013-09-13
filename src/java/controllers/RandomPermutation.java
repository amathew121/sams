package controllers;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RandomPermutation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomPermutation r = new RandomPermutation(20);
		for(int i=0;i<20;i++){System.out.println(r.next());}
	}

	    private List<Integer> list;
	    private int index;

		 /**
	     * Create a random permutation the sequence of numbers 0, 1, ... , n - 1.
	     * @param n Range specifier, must be positive
	     */
		
	    public RandomPermutation(int n) {
	        if (n <= 1) {
	            throw new IllegalArgumentException(
	                    "Positive number expected, got: " + n);
	        }
	        list = new ArrayList<Integer>();
	        newList(n);
	    }

	    /**
	     * Creates a new list
	     */
	    public void newList(int n) {
	        index = -1;
	        list.clear();
	        for (int i = 1; i < n+1; i++) {
	            list.add(i);
	        }
	        Collections.shuffle(list);
	    }

	    /**
	     * Retrieve the next integer in sequence. 
	     */
	    public int next() {
	        index = (index + 1) % list.size();
	        return list.get(index);
	    }
	
	
}

