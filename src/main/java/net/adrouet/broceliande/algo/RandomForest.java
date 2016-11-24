package net.adrouet.broceliande.algo;

import net.adrouet.broceliande.struct.DataSet;
import net.adrouet.broceliande.struct.IData;
import net.adrouet.broceliande.struct.Node;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RandomForest<D extends IData<R>, R extends Comparable<R>> {

	private final static Logger LOG = LoggerFactory.getLogger(RandomForest.class);
	private List<DecisionTree<D, R>> decisionTrees;
	private Splitter<D, R> splitter;
	private Bagging<D> bagging;
	private Parameter p;

	/**
	 * Init RF with all parameter
	 *
	 * @param p params
	 */
	public RandomForest(Parameter p) {
		LOG.debug("Initializing Random Forest : {}", p);
		this.decisionTrees = new ArrayList<>();
		this.splitter = new Splitter<>(p.getK(), p.getRandom());
		this.bagging = new Bagging<>(p.getRandom());
		this.p = p;
	}

	/**
	 * Create the model whit the learning set
	 *
	 * @param learningSet
	 */
	public void fit(List<D> learningSet) {
		LOG.info("Fit: learning set with {} elements, generating {} trees ", learningSet.size(), p.getNbTrees());
		long startTime = System.currentTimeMillis();
		// #1 Bagging
		this.bagging.getStream(learningSet).limit(p.getNbTrees()).forEach(sample -> {
			// #2 Build all decision tree
			DecisionTree<D, R> decisionTree = new DecisionTree<>(splitter, this.p);
			// XXX
			DataSet<D, R> ds = new DataSet<>(sample.get(0).getClass());
			ds.setData(sample);
			decisionTree.compute(ds);
			decisionTrees.add(decisionTree);
		});
		long endTime = System.currentTimeMillis();
		LOG.debug("Fit: end in {} ms", endTime - startTime);
	}

	/**
	 * Set the predicted result on data
	 *
	 * @param data
	 * @return
	 */
	public R predict(D data) {
		HashMap<R, Integer> votes = new HashMap<>();
		// #1 voting
		for (DecisionTree<D, R> tree : this.decisionTrees) {
			R vote = tree.predict(data);
			if (!votes.containsKey(vote)) {
				votes.put(vote, 0);
			}
			votes.put(vote, votes.get(vote) + 1);
		}
		// #2 get the dominant vote
		Entry<R, Integer> dominant = null;
		for (Entry<R, Integer> vote : votes.entrySet()) {
			if (dominant == null || dominant.getValue() < vote.getValue()) {
				dominant = vote;
			}
		}
		LOG.trace("Predict vote result: {}", votes);
		return dominant != null ? dominant.getKey() : null;
	}

	/**
	 * rank the features by their importance
	 *
	 * @return
	 */
	public List<ImmutablePair<String, Double>> importance() {
		HashMap<String, MutablePair<Integer, Double>> sums = new HashMap<>();
		// #1 collect the impurity decreases
		for (DecisionTree<D, R> tree : this.decisionTrees) {
			for (Node<R> current : tree) {
				if (!current.isLeaf()) {
					String name = current.getSplit().getFeature().getName();
					if (!sums.containsKey(name)) {
						sums.put(name, new MutablePair<>(0, 0.));
					}
					MutablePair<Integer, Double> increase = sums.get(name);
					increase.setLeft(increase.getLeft() + 1);
					increase.setRight(increase.getRight() + current.getSplit().getImpurityDecrease());
				}
			}
		}
		// #2 sort the collection
		return sums.entrySet().stream()
				.map(elt -> new ImmutablePair<>(elt.getKey(), elt.getValue().right / elt.getValue().left))
				.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
				.collect(Collectors.toList());
	}

}
