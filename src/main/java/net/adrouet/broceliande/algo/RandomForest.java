package net.adrouet.broceliande.algo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.adrouet.broceliande.struct.DataSet;
import net.adrouet.broceliande.struct.IData;

public class RandomForest<D extends IData<R>, R extends Comparable<R>> {

	private final static Logger LOG = LoggerFactory.getLogger(RandomForest.class);
	private List<DecisionTree<D, R>> decisionTrees;
	private Splitter<D, R> splitter;
	private Bagging bagging;
	private Parameter p;

	/**
	 * Init RF with all parameter
	 *
	 * @param p
	 *            params
	 */
	public RandomForest(Parameter p) {
		LOG.debug("Initializing Random Forest : {}", p);
		this.decisionTrees = new ArrayList<>();
		this.splitter = new Splitter(p.getK());
		this.bagging = new Bagging(p.getSeed());
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
			DecisionTree decisionTree = new DecisionTree(splitter, this.p);
			// XXX
			DataSet ds = new DataSet<>(sample.get(0).getClass());
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
				votes.put(vote, new Integer(0));
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
		return dominant.getKey();
	}

	public List<ImmutablePair<Method, Double>> importance() {
		for (DecisionTree<D, R> tree : this.decisionTrees) {
			// (TODO)
		}
		return null;
	}

}
