package it.unibo.es1;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicsImpl implements Logics {
	private final List<Integer> elements;

	public LogicsImpl(final int size) {
		this.elements = new ArrayList<>(Collections.nCopies(size, 0));
	}

	@Override
	public int size() {
		return this.elements.size();
	}

	@Override
	public List<Integer> values() {
		return Collections.unmodifiableList(this.elements);
	}

	@Override
	public List<Boolean> enablings() {
		return this.elements.stream()
				.map(x -> x < elements.size())
				.toList();
	}

	@Override
	public int hit(final int elem) {
		final int itemValue = this.elements.get(elem);
		final int newItemValue = itemValue + 1;

		this.elements.set(elem, newItemValue);

		return newItemValue;
	}

	@Override
	public String result() {
		return this.elements.stream()
				.map(x -> x.toString())
				.collect(joining("|", "<<", ">>"));
	}

	@Override
	public boolean toQuit() {
		return this.elements.stream()
				.allMatch(x -> x == elements.get(0));
	}
}
