package net.korriganed.broceliande.algo;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.korriganed.broceliande.struct.DataSet;
import net.korriganed.broceliande.struct.DecisionTreeIterator;
import net.korriganed.broceliande.struct.Node;
import net.korriganed.broceliande.struct.SubDataSets;

public class DecisionTree<D, R> implements Iterable<Node<D, R>> {

	private final static Logger LOG = LoggerFactory.getLogger(DecisionTree.class);

	private Node<D, R> root;
	private Splitter<D, R> splitter;
	private Parameter params;

	private int nbLeaf = 0;
	private int maxDepth = 0;

	public DecisionTree(Splitter<D, R> splitter, Parameter params) {
		this.splitter = splitter;
		this.params = params;
	}

	public void compute(DataSet<D, R> dataSet) {
		LOG.info("Computing decision tree");
		long startTime = System.currentTimeMillis();

		Queue<Pair<Node<D, R>, DataSet<D, R>>> nodeToCompute = new LinkedList<>();
		this.root = new Node<>();
		nodeToCompute.add(new ImmutablePair<>(this.root, dataSet));

		Pair<Node<D, R>, DataSet<D, R>> n;
		BestSplit split;
		while (!nodeToCompute.isEmpty()) {
			n = nodeToCompute.poll();
			// Set t as a terminal node if its depth is greater or equal to
			// MaxDepth (d)
			// or if it contains less than MinSamplesSplit samples (c)
			if (n.getKey().getDepth() >= this.params.getMaxDepth()
					|| n.getValue().getL_t().size() < this.params.getMinSamplesSplit()) {
				toLeaf(n);
				continue;
			}
			// Split the dataset
			split = this.splitter.findBestSplit(n.getValue());

			// Splitter is unable to split
			if (!split.isSplit()) {
				toLeaf(n);
				continue;
			}
			// Set t as a terminal node if the total decrease in impurity is
			// less than MinImpurityDecrease (e)
			if (split.getImpurityDecrease() < this.params.getMinImpurityDecrease()) {
				toLeaf(n);
				continue;
			}

			n.getKey().setSplit(split);
			SubDataSets<D, R> subDataSets = n.getValue().split(split.getCutPoint());

			// Set t as a terminal node if there is no split such that tL and
			// tR both count a least MinSampleLeaf samples (f)
			if (subDataSets.getLeft().getL_t().size() < this.params.getMinSampleLeaf()
					|| subDataSets.getRight().getL_t().size() < this.params.getMinSampleLeaf()) {
				toLeaf(n);
				continue;
			}
			Node<D, R> nLeft = new Node<>(n.getKey().getDepth() + 1);
			nodeToCompute.add(new ImmutablePair<>(nLeft, subDataSets.getLeft()));
			n.getKey().setLeft(nLeft);

			Node<D, R> nRight = new Node<>(n.getKey().getDepth() + 1);
			nodeToCompute.add(new ImmutablePair<>(nRight, subDataSets.getRight()));
			n.getKey().setRight(nRight);

			LOG.trace("New Nodes: [{}][{}]", subDataSets.getLeft().getL_t().size(),
					subDataSets.getRight().getL_t().size());

		}

		long endTime = System.currentTimeMillis();
		LOG.debug("Decision tree: end in {} ms with {} leaves and depth {}", endTime - startTime, this.nbLeaf,
				this.maxDepth);
	}

	private void toLeaf(Pair<Node<D, R>, DataSet<D, R>> n) {
		n.getKey().setResult(n.getValue().getDominantResult());
		this.nbLeaf++;
		if (n.getKey().getDepth() > this.maxDepth) {
			this.maxDepth = n.getKey().getDepth();
		}
	}

	public R predict(D data) {
		Node<D, R> currentNode = this.root;
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChild(data);
		}
		return currentNode.getResult();
	}

	@Override
	public Iterator<Node<D, R>> iterator() {
		return new DecisionTreeIterator<>(this.root);
	}

	@Override
	public String toString() {
		return "DecisionTree{" + "nbLeaf=" + nbLeaf + ", maxDepth=" + maxDepth + '}';
	}
}
