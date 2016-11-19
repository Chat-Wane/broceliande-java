package net.adrouet.broceliande.algo;

import net.adrouet.broceliande.struct.IDataSet;
import net.adrouet.broceliande.struct.Node;

public class DecisionTree {

	private Node root;

	public DecisionTree(IDataSet dataSet, Splitter splitter) {
		// #1 create the fitest split on 1 of the feature
		BestSplit split = splitter.findBestSplit(dataSet);

	}

}
