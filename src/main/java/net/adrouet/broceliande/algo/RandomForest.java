package net.adrouet.broceliande.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.adrouet.broceliande.struct.IData;

public class RandomForest<T> {

	private List<DecisionTree> decisionTrees;
	private Splitter splitter;
	private Bagging bagging;
	private Parameter p;

	/**
	 * Init RF with all parameter
	 * 
	 * @param p
	 *            params
	 */
	RandomForest(Parameter p) {
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
	public void fit(List<IData> learningSet) {
		// #1 Bagging
		this.bagging.generateSamples(learningSet, p.getNbTrees()).stream().forEach(sample -> {
			// #2 Build all decision tree
			DecisionTree decisionTree = new DecisionTree(splitter, this.p);
			decisionTree.compute(sample, splitter);
			decisionTrees.add(decisionTree);
		});

	}

	/**
	 * Set the predicted result on tesData
	 * 
	 * @param testData
	 * @return
	 */
	public void predict(IData testData) {

	}
}
