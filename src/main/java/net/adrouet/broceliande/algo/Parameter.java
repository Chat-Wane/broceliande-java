package net.adrouet.broceliande.algo;

public class Parameter {

	private int minSamplesSplit;
	private int maxDepth;
	private double minImpurityDecrease;
	private int minSampleLeaf;
	private int k;
	private long seed;
	private int nbTrees;

	public Parameter(int nbTrees, long seed, int k, int minSamplesSplit, int maxDepth, double minImpurityDecrease,
			int minSampleLeaf) {
		this.minSamplesSplit = minSamplesSplit;
		this.maxDepth = maxDepth;
		this.minImpurityDecrease = minImpurityDecrease;
		this.minSampleLeaf = minSampleLeaf;
		this.k = k;
		this.seed = seed;
		this.nbTrees = nbTrees;
	}

	public int getNbTrees() {
		return nbTrees;
	}

	public void setNbTrees(int nbTrees) {
		this.nbTrees = nbTrees;
	}

	public long getSeed() {
		return seed;
	}

	public void setSeed(long seed) {
		this.seed = seed;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMinSamplesSplit() {
		return minSamplesSplit;
	}

	public void setMinSamplesSplit(int minSamplesSplit) {
		this.minSamplesSplit = minSamplesSplit;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public double getMinImpurityDecrease() {
		return minImpurityDecrease;
	}

	public void setMinImpurityDecrease(double minImpurityDecrease) {
		this.minImpurityDecrease = minImpurityDecrease;
	}

	public int getMinSampleLeaf() {
		return minSampleLeaf;
	}

	public void setMinSampleLeaf(int minSampleLeaf) {
		this.minSampleLeaf = minSampleLeaf;
	}
}
