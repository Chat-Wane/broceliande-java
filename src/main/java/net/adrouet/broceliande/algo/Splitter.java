package net.adrouet.broceliande.algo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import net.adrouet.broceliande.data.Feature;
import net.adrouet.broceliande.data.FeatureType;
import net.adrouet.broceliande.struct.IData;
import net.adrouet.broceliande.struct.IDataSet;
import net.adrouet.broceliande.struct.Occurrences;

public class Splitter {

	public static BestSplit findBestSplit(IDataSet dataSet) {
		return Splitter.findBestSplit(dataSet, dataSet.getP().size());
	}

	public static BestSplit findBestSplit(IDataSet dataSet, Integer K) {
		BestSplit sstar = new BestSplit(null, null, 0.);
		Double delta = Double.NEGATIVE_INFINITY;
		ArrayList<Method> randomP = new ArrayList<>(dataSet.getP());
		// #1 draw random getters from the features
		while (randomP.size() > K) {
			Collections.shuffle(randomP);
			randomP.remove(randomP.size() - 1);
		}
		for (Method X_j : randomP) {
			// #1 find the best binary split s*_j defined on X_j
			BestSplit splitX_j = Splitter.findBestSplit(dataSet, X_j);
			if (splitX_j.getImpurityDecrease() > delta) {
				delta = splitX_j.getImpurityDecrease();
				sstar = splitX_j;
			}
		}

		return sstar;
	}

	/**
	 * @param dataSet
	 *            the subset of node samples falling into node t (contains L_t)
	 * @param X_j
	 *            the j-th input variable or feature (getter)
	 * @see page 50 of LOUPPE's thesis about random forests
	 */
	public static BestSplit findBestSplit(IDataSet dataSet, Method X_j) {
		BestSplit vstar_j = new BestSplit(X_j, null, 0.);
		Double delta = 0.;

		// #1 initialize the statistics for t_R to i(t)
		// t_R: the right child of node t
		Occurrences occ_R = new Occurrences(dataSet.getJ());
		// #A fill the right node with all data
		for (IData x : dataSet.getL_t()) {
			occ_R.add(x);
		}

		// #2 initialize the statistics for t_L to 0
		// t_L: the left child of node t
		Occurrences occ_L = new Occurrences(dataSet.getJ());

		// #3 initialize i(t)
		// i(t): the impurity of node t
		Double it = impurityG(occ_R.getOccurrences(), dataSet.getL_t().size());

		// #4 sort the sample L_t using the comparator on X_j returned values
		Comparator<IData> comparator = (x1, x2) -> {
            try {
                Comparable x1j = (Comparable) X_j.invoke(x1);
                Comparable x2j = (Comparable) X_j.invoke(x2);
                return x1j.compareTo(x2j);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0;
        };
		Collections.sort(dataSet.getL_t(), comparator);

		// #5 loop over the sample of t
		Integer i = 0; // /!\ starts at 1 in the algorithm of the thesis
		List<IData> L_t = dataSet.getL_t();
		Integer N_t = L_t.size(); // N_t: the number of node samples in node t
		while (i < N_t) {
			while (((i + 1) < N_t) && (comparator.compare(L_t.get(i), L_t.get(i + 1)) == 0)) {
				occ_L.add(L_t.get(i));
				occ_R.remove(L_t.get(i));
				++i;
			}
			occ_L.add(L_t.get(i));
			occ_R.remove(L_t.get(i));
			++i;
			if (i < N_t) {
				try {
					if (X_j.getAnnotation(Feature.class).equals(FeatureType.CATEGORICAL)) {

					} else {
						// v'_k: the mid-cut point between v_k and v_k+1
						Double vPrime_kplus1 = average((Number) X_j.invoke(L_t.get(i)),
								(Number) X_j.invoke(L_t.get(i - 1)));
					}
					// ∆i(s, t): the impurity decrease of split s at node t
					// ∆i(s, t) = i(t) - p_L i(t_L) - p_R i(t_R)
					Double p_L = occ_L.getTotal() / ((double) L_t.size());
					Double p_R = occ_R.getTotal() / ((double) L_t.size());
					Double delta_iOfv_kplus1 = it - p_L * impurityG(occ_L.getOccurrences(), occ_L.getTotal())
							- p_R * impurityG(occ_R.getOccurrences(), occ_R.getTotal());

					if (delta_iOfv_kplus1 > delta) {
						delta = delta_iOfv_kplus1;
						Predicate<IData> p = d -> {
                            try {
                                return ((Number)X_j.invoke(d)).doubleValue() < vPrime_kplus1;
                            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                throw new RuntimeException("Getter on ordered feature not returning Number");
                            }
                        };

						vstar_j = new BestSplit(X_j, p, delta_iOfv_kplus1);
					}

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return vstar_j;
	}

	private static Double average(Number a, Number b) {
		return (a.doubleValue() + b.doubleValue()) / 2;
	}

	private static Double impurityG(List<Integer> N_cts, Integer N_t) {
		Double it = 0.;
		for (Integer N_ct : N_cts) {
			Double pc_kt = N_ct / (N_t.doubleValue());
			it += pc_kt * (1 - pc_kt);
		}
		return it;
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

	/**
	 * (TODO) The impurity function i_H(t) based on the Shannon entropy
	 * 
	 * @return
	 */
	private static Double impurityH() {
		return 0.;
	}
}
