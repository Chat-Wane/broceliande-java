package net.adrouet.broceliande.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.adrouet.broceliande.struct.IData;
import net.adrouet.broceliande.struct.IDataSet;

public class Splitter<V> {

	public void finBestSplit(IDataSet dataSet) {
		Double delta = Double.NEGATIVE_INFINITY;
		for (Method p: dataSet.getP()){
			// #1 find the best binary split s*_j defined on X_j
			
		}
	}

	/**
	 * @param dataSet
	 *            the subset of node samples falling into node t (contains L_t)
	 * @param X_j
	 *            the j-th input variable or feature (getter)
	 * @see page 50 of LOUPPE's thesis about random forests
	 */
	public void findBestSplit(IDataSet dataSet, Method X_j) {
		Double delta = 0.;
		Integer k = 0;
		// v'_k: the mid-cut point between v_k and v_k+1
		Double vPrime_k = Double.NEGATIVE_INFINITY;

		// #1 TODO: compute the necessary statistics to process i(t)
		// i(t): the impurity of node t
		Double it = impurityG(dataSet);

		// #2 initialize the statistics for t_L to 0
		// t_L: the left child of node t

		// #3 initialize the statistics for t_R to i(t)
		// t_R: the right child of node t

		// #4 sort the sample L_t using the comparator on X_j returned values
		Comparator<IData> comparator = new Comparator<IData>() {
			@Override
			public int compare(IData x1, IData x2) {
				try {
					Comparable x1j = (Comparable) X_j.invoke(x1);
					Comparable x2j = (Comparable) X_j.invoke(x2);
					return x1j.compareTo(x2j);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		};
		Collections.sort(dataSet.getL_t(), comparator);

		// #5 loop over the sample of t
		Integer i = 0; // /!\ starts at 1 in the algorithm of the thesis
		List<IData> L_t = dataSet.getL_t();
		Integer N_t = L_t.size(); // N_t: the number of node samples in node t
		while (i < N_t) {
			while (((i + 1) < N_t) && (comparator.compare(L_t.get(i), L_t.get(i + 1)) == 0)) {
				++i;
			}
			++i;
			if (i < N_t) {
				// TODO no cast to Double
				try {
					Double vPrime_kplus1 = ((Double) X_j.invoke(L_t.get(i)) + (Double) X_j.invoke(L_t.get(i - 1))) / 2.;

					// #6 (TODO) update the necessary statistics from v'k to
					// v'k+1

					// ∆i(s, t): the impurity decrease of split s at node t
					// ∆i(s, t) = i(t) - p_L i(t_L) - p_R i(t_R)

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * (TODO) The impurity function i_H(t) based on the Shannon entropy
	 * 
	 * @return
	 */
	private static Double impurityH() {
		return 0.;
	}

	/**
	 * (TODO) The impurity function i_G(t) based on the Gini index
	 * 
	 * @param dataSet:
	 *            the data set
	 * @return
	 */
	private Double impurityG(IDataSet dataSet) {
		Double sum = 0.;

		// J: set of results
		for (Comparable result : dataSet.getJ()) {
			Integer N_ct = 0; // N_ct: number of occurrences of this result
			for (IData data : dataSet.getL_t()) {
				if (result.compareTo(data.getResult()) == 0) {
					++N_ct;
				}
			}
			// p(c_k|t): the empirical probability estimate
			// p(Y = c | X in X_t) = N_ct/N_t of class c at node t
			Double pc_kt = N_ct / (new Double(dataSet.getL_t().size()));
			sum = pc_kt * (1 - pc_kt);
		}
		return sum;
	}

	/**
	 * (TODO) The impurity function i_R(t) based on the local resubstitution
	 * estimate defined on the squared error loss
	 * 
	 * @return
	 */
	private static Double impurityR() {
		return 0.;
	}

}
