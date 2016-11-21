package net.adrouet.broceliande.struct;

import net.adrouet.broceliande.algo.BestSplit;

public class Node<R extends Comparable<R>> {

	private String label;

	private Node<R> left;

	private Node<R> right;

	private R result;

	private BestSplit split;

	private int depth = 0;

	public Node() {
	}

	public Node(int depth){
		this.depth = depth;
	}

	public Node<R> getChild(IData data) {
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

	public Node<R> getLeft() {
		return left;
	}

	public void setLeft(Node<R> left) {
		this.left = left;
	}

	public Node<R> getRight() {
		return right;
	}

	public void setRight(Node<R> right) {
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
