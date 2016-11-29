package net.korriganed.broceliande.algo;

import java.util.Random;

public class Parameter {

	private int minSamplesSplit;
	private int maxDepth;
	private double minImpurityDecrease;
	private int minSampleLeaf;
	private int k;
	private long seed;
	private int nbTrees;
	private Random random;

	public static class Builder {
		private int minSamplesSplit = 2;
		private int maxDepth = Integer.MAX_VALUE;
		private double minImpurityDecrease = 1e-07;
		private int minSampleLeaf = 1;
		private int maxFeatures = Integer.MAX_VALUE;
		private int nbTrees = 10;
		private Long seed = null;

		public Builder minSamplesSplit(int minSamplesSplit) {
			this.minSamplesSplit = minSamplesSplit;
			return this;
		}

		public Builder maxDepth(int maxDepth) {
			this.maxDepth = maxDepth;
			return this;
		}

		public Builder minImpurityDecrease(double minImpurityDecrease) {
			this.minImpurityDecrease = minImpurityDecrease;
			return this;
		}

		public Builder minSampleLeaf(int minSampleLeaf) {
			this.minSampleLeaf = minSampleLeaf;
			return this;
		}

		public Builder maxFeatures(int maxFeatures) {
			this.maxFeatures = maxFeatures;
			return this;
		}

		public Builder seed(Long seed) {
			this.seed = seed;
			return this;
		}

		public Builder nbTrees(int nbTrees) {
			this.nbTrees = nbTrees;
			return this;
		}

		public Parameter build() {
			return new Parameter(this);
		}

	}

	private Parameter(Builder builder) {
		this.minSamplesSplit = builder.minSamplesSplit;
		this.maxDepth = builder.maxDepth;
		this.minImpurityDecrease = builder.minImpurityDecrease;
		this.minSampleLeaf = builder.minSampleLeaf;
		this.k = builder.maxFeatures;
		this.nbTrees = builder.nbTrees;
		if(builder.seed == null){
			this.random = new Random();
			this.seed = this.random.nextLong();
			this.random.setSeed(this.seed);
		} else {
			this.random = new Random(builder.seed);
			this.seed = builder.seed;
		}
	}

	public int getNbTrees() {
		return nbTrees;
	}

	public long getSeed() {
		return seed;
	}

	public int getK() {
		return k;
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

	public Random getRandom(){
		return this.random;
	}

	@Override
	public String toString() {
		return "Parameter{" +
				"minSamplesSplit=" + minSamplesSplit +
				", maxDepth=" + maxDepth +
				", minImpurityDecrease=" + minImpurityDecrease +
				", minSampleLeaf=" + minSampleLeaf +
				", k=" + k +
				", seed=" + seed +
				", nbTrees=" + nbTrees +
				'}';
	}
}
