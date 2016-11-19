package net.adrouet.broceliande.algo;

import net.adrouet.broceliande.struct.IData;

import java.util.List;

public class RandomForest<T> {

	private List<DecisionTree> decisionTrees;

	/**
	 * Init RF with all parameter
	 * @param p params
	 */
	RandomForest(Parameter p){

	}

	/**
	 * Create the model whit the learning set
	 * @param learningSet
	 */
	public void fit(List<IData> learningSet){
		// #1 Bagging
		// #2 Build all decision tree
	}

	/**
	 * Set the predicted result on tesData
	 * @param testData
	 * @return
	 */
	public void predict(IData testData){

	}
}
