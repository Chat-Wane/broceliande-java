package net.adrouet.broceliande.algo;

public class Parameter {

	private int minSamplesSplit;
	private int maxDepth;
	private double minImpurityDecrease;
	private int minSampleLeaf;

	public Parameter(int minSamplesSplit, int maxDepth, double minImpurityDecrease, int minSampleLeaf) {
		this.minSamplesSplit = minSamplesSplit;
		this.maxDepth = maxDepth;
		this.minImpurityDecrease = minImpurityDecrease;
		this.minSampleLeaf = minSampleLeaf;
	}

	public int getMinSamplesSplit() {
		return minSamplesSplit;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public double getMinImpurityDecrease() {
		return minImpurityDecrease;
	}

	public int getMinSampleLeaf() {
		return minSampleLeaf;
	}
}
