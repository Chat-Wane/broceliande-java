package net.adrouet.broceliande.struct;

import net.adrouet.broceliande.algo.BestSplit;

public class Node<U> {

	private String label;

	private Node<U> left;

	private Node<U> right;

	private U result;

	private BestSplit split;

	public Node() {
	}

	public Node<U> getChild(IData data) {
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

	public Node<U> getLeft() {
		return left;
	}

	public void setLeft(Node<U> left) {
		this.left = left;
	}

	public Node<U> getRight() {
		return right;
	}

	public void setRight(Node<U> right) {
		this.right = right;
	}

	public U getResult() {
		return result;
	}

	public void setResult(U result) {
		this.result = result;
	}

	public void setSplit(BestSplit split) {
		this.split = split;
	}
	
	public BestSplit getSplit() {
		return split;
	}
}
