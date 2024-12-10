package it.unibo.es3;

public interface Logics {
	void initStage(int elementToEnable);

	void nextStage();

	boolean getValue(Pair<Integer, Integer> element);

	boolean quit();
}
