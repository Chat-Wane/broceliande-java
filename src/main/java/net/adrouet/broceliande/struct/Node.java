package net.adrouet.broceliande.struct;

import java.util.function.Predicate;

public class Node<T, U> {

	private String label;

	private Node<T, U> left;

	private Node<T, U> right;

	private Predicate<T> predicate;

	private U result;

	private float impurity;

	public Node() {
		this.predicate = (T t) -> false;
	}

	public Node<T, U> getChild(T data) {
		if (left.getPredicate().test(data)) {
			return left;
		} else if (right.getPredicate().test(data)) {
			return right;
		}
		return null;
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

	public Node<T, U> getLeft() {
		return left;
	}

	public void setLeft(Node<T, U> left) {
		this.left = left;
	}

	public Node<T, U> getRight() {
		return right;
	}

	public void setRight(Node<T, U> right) {
		this.right = right;
	}

	public Predicate<T> getPredicate() {
		return predicate;
	}

	public void setPredicate(Predicate<T> predicate) {
		this.predicate = predicate;
	}

	public U getResult() {
		return result;
	}

	public void setResult(U result) {
		this.result = result;
	}

	public float getImpurity() {
		return impurity;
	}

	public void setImpurity(float impurity) {
		this.impurity = impurity;
	}
}
