package it.unibo.es2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicsImpl implements Logics {
    private final List<List<Boolean>> matrix;

    public LogicsImpl(final int size) {
        this.matrix = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Boolean> row = new ArrayList<>(Collections.nCopies(size, false));
            this.matrix.add(row);
        }
    }

    @Override
    public boolean hit(final Pair<Integer, Integer> element) {
        final List<Boolean> rowElements = this.matrix.get(element.getX());
        final boolean value = rowElements.get(element.getY());
        final boolean newValue = !value;

        rowElements.set(element.getY(), newValue);
        this.matrix.set(element.getX(), rowElements);

        return newValue;
    }

    @Override
    public boolean toQuit(final Pair<Integer, Integer> element) {
        final boolean allRowElementsAreClicked = this.matrix.get(element.getX()).stream()
                .allMatch(x -> x);
        final boolean allColumnElementsAreClicked = this.matrix.stream()
                .allMatch(x -> x.get(element.getY()));

        return allRowElementsAreClicked || allColumnElementsAreClicked;
    }
}
