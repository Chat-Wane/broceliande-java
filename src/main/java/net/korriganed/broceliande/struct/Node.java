package net.korriganed.broceliande.struct;

import net.korriganed.broceliande.algo.BestSplit;

public class Node<D, R> {

	private String label;

	private Node<D, R> left;

	private Node<D, R> right;

	private R result;

	private BestSplit<D> split;

	private int depth = 0;

	public Node() {
	}

	public Node(int depth) {
		this.depth = depth;
	}

	public Node<D, R> getChild(D data) {
		if (this.split.getCutPoint().test(data)) {
			return left;
		} else
			return right;
	}

	public boolean isLeaf() {
		return result != null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Node<D, R> getLeft() {
		return left;
	}

	public void setLeft(Node<D, R> left) {
		this.left = left;
	}

	public Node<D, R> getRight() {
		return right;
	}

	public void setRight(Node<D, R> right) {
		this.right = right;
	}

	public R getResult() {
		return result;
	}

	public void setResult(R result) {
		this.result = result;
	}

	public void setSplit(BestSplit split) {
		this.split = split;
	}

	public BestSplit getSplit() {
		return split;
	}

	public int getDepth() {
		return depth;
	}
}
