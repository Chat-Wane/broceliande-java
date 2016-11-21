package net.adrouet.broceliande.algo;

import net.adrouet.broceliande.struct.DataSet;
import net.adrouet.broceliande.struct.IData;
import net.adrouet.broceliande.struct.Node;
import net.adrouet.broceliande.struct.SubDataSets;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class DecisionTree<D extends IData<R>, R extends Comparable<R>> {

	private Node<R> root;
	private Splitter splitter;
	private Parameter params;

	public DecisionTree(Splitter splitter, Parameter params) {
		this.splitter = splitter;
		this.params = params;
	}

	public void compute(DataSet<D, R> dataSet) {

		Queue<Pair<Node<R>, DataSet<D, R>>> nodeToCompute = new LinkedList<>();
		this.root = new Node<>();
		nodeToCompute.add(new ImmutablePair<>(this.root, dataSet));

		Pair<Node<R>, DataSet<D, R>> n;
		BestSplit split;
		while (!nodeToCompute.isEmpty()) {
			n = nodeToCompute.poll();
			// Set t as a terminal node if its depth is greater or equal to
			// MaxDepth (d)
			// or if it contains less than MinSamplesSplit samples (c)
			if (n.getKey().getDepth() > this.params.getMaxDepth()
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
			SubDataSets<D, R> subDataSets = dataSet.split(split.getCutPoint());

			// Set t as a terminal node if there is no split such that tL and
			// tR both count a least MinSampleLeaf samples (f)
			if (subDataSets.getLeft().getL_t().size() < this.params.getMinSampleLeaf()
					|| subDataSets.getLeft().getL_t().size() < this.params.getMinSampleLeaf()) {
				toLeaf(n);
				continue;
			}
			Node<R> nLeft = new Node<>(n.getKey().getDepth() + 1);
			nodeToCompute.add(new ImmutablePair<>(nLeft, subDataSets.getLeft()));
			n.getKey().setLeft(nLeft);

			Node<R> nRight = new Node<>(n.getKey().getDepth() + 1);
			nodeToCompute.add(new ImmutablePair<>(nRight, subDataSets.getRight()));
			n.getKey().setRight(nRight);

		}

	}

	private void toLeaf(Pair<Node<R>, DataSet<D, R>> n) {
		n.getKey().setResult(n.getValue().getDominantResult());
	}

	public R predict(D data) {
		Node<R> currentNode = this.root;
		while (!currentNode.isLeaf()) {
			currentNode = currentNode.getChild(data);
		}
		return currentNode.getResult();
	}

}
