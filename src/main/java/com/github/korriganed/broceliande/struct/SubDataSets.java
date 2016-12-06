package com.github.korriganed.broceliande.struct;

public class SubDataSets<D, R> {

	private final DataSet<D, R> left;
	private final DataSet<D, R> right;

	public SubDataSets(DataSet<D, R> left, DataSet<D, R> right) {
		this.left = left;
		this.right = right;
	}

	public DataSet<D, R> getLeft() {
		return left;
	}

	public DataSet<D, R> getRight() {
		return right;
	}

}
