package com.github.korriganed.broceliande.struct;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DecisionTreeIterator<D, R> implements Iterator<Node<D, R>> {

	private Queue<Node<D, R>> toExplore;

	public DecisionTreeIterator(Node<D, R> root) {
		this.toExplore = new LinkedList<>();
		this.toExplore.add(root);
	}

	@Override
	public boolean hasNext() {
		return !this.toExplore.isEmpty();
	}

	@Override
	public Node<D, R> next() {
		Node<D, R> result = toExplore.poll();
		if (!result.isLeaf()) {
			toExplore.add(result.getLeft());
			toExplore.add(result.getRight());
		}
		return result;
	}

}
