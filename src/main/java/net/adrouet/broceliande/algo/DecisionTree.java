package net.adrouet.broceliande.algo;

import java.util.ArrayList;

import net.adrouet.broceliande.struct.IDataSet;
import net.adrouet.broceliande.struct.Node;
import net.adrouet.broceliande.struct.SubDataSets;

public class DecisionTree {

	private Node root;

	public DecisionTree(IDataSet dataSet, Splitter splitter) {
		Integer i = 0;
		Integer p = (int) Math.floor(Math.sqrt((double) dataSet.getP().size()));

		// #1 create the fitest split on 1 of the feature (root)
		BestSplit split = splitter.findBestSplit(dataSet);

		if (split.isSplit()) {
			this.root = new Node<>();
			this.root.setSplit(split);

			// #2 create children of the root node
			SubDataSets subDataSets = dataSet.split(split.getCutPoint());
			ArrayList<Node> todoNodes = new ArrayList<>();
			this.root.setLeft(new Node<>());
			todoNodes.add(this.root.getLeft());
			this.root.setRight(new Node<>());
			todoNodes.add(this.root.getRight());
			ArrayList<IDataSet> todoDataSets = new ArrayList<>();
			todoDataSets.add(subDataSets.getLeft());
			todoDataSets.add(subDataSets.getRight());

			boolean stop = false;
			while (!stop) {

				stop = todoNodes.isEmpty();
			}
		}
	}

}
