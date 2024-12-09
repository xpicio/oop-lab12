package it.unibo.es2;

public interface Logics {
	/**
	 * @return true or false if the element is clicked or not
	 */
	boolean hit(Pair<Integer, Integer> element);

	/**
	 * @return whether it is time to quit
	 */
	boolean toQuit(Pair<Integer, Integer> element);
}
