package it.unibo.es3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class LogicsImpl implements Logics {
    private final List<List<Boolean>> matrix;
    private final List<Pair<Integer, Integer>> initElements;
    private int stageIterationCounter;

    LogicsImpl(final int size) {
        this.stageIterationCounter = 1;
        this.initElements = new ArrayList<>();
        this.matrix = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            List<Boolean> row = new ArrayList<>(Collections.nCopies(size, false));
            this.matrix.add(row);
        }
    }

    private void enable(final Pair<Integer, Integer> element) {
        final List<Boolean> row = this.matrix.get(element.getX());
        row.set(element.getY(), true);
    }

    private void enableAdjacent(final Pair<Integer, Integer> element) {
        if ((element.getY() >= 0 && element.getY() < this.matrix.size())
                && (element.getX() >= 0 && element.getX() < this.matrix.size())) {
            this.enable(element);
        }
    }

    private List<Pair<Integer, Integer>> moveToNextEdge(
            final Function<Pair<Integer, Integer>, Pair<Integer, Integer>> moveTo,
            final Pair<Integer, Integer> initialElement,
            final Pair<Integer, Integer> lastElement) {
        final List<Pair<Integer, Integer>> elementsToEnable = new ArrayList<>();
        Pair<Integer, Integer> currentElement = initialElement;

        while (!currentElement.equals(lastElement)) {
            currentElement = moveTo.apply(currentElement);
            elementsToEnable.add(currentElement);
        }
        elementsToEnable.add(lastElement);

        return elementsToEnable;
    }

    private List<Pair<Integer, Integer>> findAdjacentElementsToEnable(final Pair<Integer, Integer> startElement) {
        final List<Pair<Integer, Integer>> elementsToEnable = new ArrayList<>();
        final Pair<Integer, Integer> topElement = new Pair<>(startElement.getX(), startElement.getY() -
                this.stageIterationCounter);
        final Pair<Integer, Integer> topRightElement = new Pair<>(
                startElement.getX() + this.stageIterationCounter, startElement.getY() -
                        this.stageIterationCounter);
        final Pair<Integer, Integer> bottomRightElement = new Pair<>(
                startElement.getX() + this.stageIterationCounter, startElement.getY() +
                        this.stageIterationCounter);
        final Pair<Integer, Integer> bottomLeftElement = new Pair<>(
                startElement.getX() - this.stageIterationCounter, startElement.getY() +
                        this.stageIterationCounter);
        final Pair<Integer, Integer> topLeftElement = new Pair<>(
                startElement.getX() - this.stageIterationCounter, startElement.getY() -
                        this.stageIterationCounter);

        Pair<Integer, Integer> currentElement = topElement;
        elementsToEnable.add(topElement);
        // move right to top right edge
        elementsToEnable.addAll(
                this.moveToNextEdge(x -> new Pair<>(x.getX() + 1, x.getY()), currentElement,
                        topRightElement));
        currentElement = topRightElement;
        // move down to bottom right edge
        elementsToEnable.addAll(
                this.moveToNextEdge(x -> new Pair<>(x.getX(), x.getY() + 1), currentElement,
                        bottomRightElement));
        currentElement = bottomRightElement;
        // move left to bottom left edge
        elementsToEnable.addAll(
                this.moveToNextEdge(x -> new Pair<>(x.getX() - 1, x.getY()), currentElement,
                        bottomLeftElement));
        currentElement = bottomLeftElement;
        // move up to top left edge
        elementsToEnable.addAll(
                this.moveToNextEdge(x -> new Pair<>(x.getX(), x.getY() - 1), currentElement,
                        topLeftElement));
        currentElement = topLeftElement;
        // move right to top element
        elementsToEnable.addAll(
                this.moveToNextEdge(x -> new Pair<>(x.getX() + 1, x.getY()), currentElement,
                        topElement));

        return elementsToEnable;
    }

    @Override
    public void initStage(final int elementToEnable) {
        final Random random = new Random();

        for (int i = 0; i < elementToEnable; i++) {
            final Pair<Integer, Integer> element = new Pair<>(random.nextInt(this.matrix.size()),
                    random.nextInt(this.matrix.size()));
            this.initElements.add(element);
            this.enable(element);
        }
    }

    @Override
    public void nextStage() {
        this.initElements.forEach(x -> {
            final List<Pair<Integer, Integer>> elementsToEnable = this.findAdjacentElementsToEnable(x);
            elementsToEnable.forEach(this::enableAdjacent);
        });

        this.stageIterationCounter++;
    }

    @Override
    public boolean quit() {
        return this.matrix.stream().flatMap(x -> x.stream()).allMatch(x -> x);
    }

    @Override
    public boolean getValue(final Pair<Integer, Integer> element) {
        return this.matrix.get(element.getX()).get(element.getY());
    }
}
