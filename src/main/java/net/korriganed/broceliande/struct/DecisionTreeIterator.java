package net.korriganed.broceliande.struct;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DecisionTreeIterator<R extends Comparable<R>> implements Iterator<Node<R>> {

	private Queue<Node<R>> toExplore;

	public DecisionTreeIterator(Node<R> root) {
		this.toExplore = new LinkedList<>();
		this.toExplore.add(root);
	}

	@Override
	public boolean hasNext() {
		return !this.toExplore.isEmpty();
	}

	@Override
	public Node<R> next() {
		Node<R> result = toExplore.poll();
		if (!result.isLeaf()) {
			toExplore.add(result.getLeft());
			toExplore.add(result.getRight());
		}
		return result;
	}

}
