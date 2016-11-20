package net.adrouet.broceliande.struct;

public class SubDataSets {

	private final DataSet left;
	private final DataSet right;

	public SubDataSets(DataSet left, DataSet right) {
		this.left = left;
		this.right = right;
	}

	public DataSet getLeft() {
		return left;
	}

	public DataSet getRight() {
		return right;
	}

}
