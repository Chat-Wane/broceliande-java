package net.adrouet.broceliande.struct;

public class SubDataSets {

	private final IDataSet left;
	private final IDataSet right;

	public SubDataSets(IDataSet left, IDataSet right) {
		this.left = left;
		this.right = right;
	}

	public IDataSet getLeft() {
		return left;
	}

	public IDataSet getRight() {
		return right;
	}

}
