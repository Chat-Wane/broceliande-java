package net.korriganed.broceliande.algo;

import static net.korriganed.broceliande.util.InspectionUtils.invokeGetter;
import static net.korriganed.broceliande.util.InspectionUtils.invokeGetterForNumber;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.korriganed.broceliande.data.FeatureType;
import net.korriganed.broceliande.struct.DataSet;
import net.korriganed.broceliande.struct.Occurrences;
import net.korriganed.broceliande.util.InspectionUtils;

public class Splitter<D, R> {

	private static final Logger LOG = LoggerFactory.getLogger(Splitter.class);

	private Integer k;
	private Random random;

	public Splitter() {
		this(null, new Random());
	}

	public Splitter(Integer k, Random random) {
		this.k = k;
		this.random = random;
	}

	public BestSplit findBestSplit(DataSet<D, R> dataSet) {
		if (this.k == null) {
			this.k = dataSet.getP().size();
		}

		BestSplit sstar = new BestSplit(null, null, 0.);
		Double delta = Double.NEGATIVE_INFINITY;
		ArrayList<Method> randomP = new ArrayList<>(dataSet.getP());
		// #1 draw random getters from the features
		Collections.shuffle(randomP, this.random);
		while (randomP.size() > this.k) {
			randomP.remove(randomP.size() - 1);
		}
		for (Method X_j : randomP) {
			// #1 find the best binary split s*_j defined on X_j
			BestSplit splitX_j = this.findBestSplit(dataSet, X_j);
			if (splitX_j.getImpurityDecrease() > delta) {
				delta = splitX_j.getImpurityDecrease();
				sstar = splitX_j;
			}
		}
		LOG.trace("Best split found: {}", sstar);

		return sstar;
	}

	/**
	 * See page 50 of LOUPPE's thesis about random forests
	 *
	 * @param dataSet
	 *            the subset of node samples falling into node t (contains L_t)
	 * @param X_j
	 *            the j-th input variable or feature (getter)
	 */
	public BestSplit findBestSplit(DataSet<D, R> dataSet, Method X_j) {
		BestSplit vstar_j = new BestSplit(X_j, null, 0.);
		Double delta = 0.;

		// #1 initialize the statistics for t_R to i(t)
		// t_R: the right child of node t
		Occurrences<D, R> occ_R = new Occurrences<>(dataSet.getJ());
		// #A fill the right node with all data
		dataSet.getL_t().forEach(occ_R::add);

		// #2 initialize the statistics for t_L to 0
		// t_L: the left child of node t
		Occurrences<D, R> occ_L = new Occurrences<>(dataSet.getJ());

		// #3 initialize i(t)
		// i(t): the impurity of node t
		Double it = impurityG(occ_R.getOccurrences(), dataSet.getL_t().size());

		// #4 sort the sample L_t using the comparator on X_j returned values
		Comparator<D> comparator = (x1, x2) -> invokeGetter(x1, X_j).compareTo(invokeGetter(x2, X_j));
		Collections.sort(dataSet.getL_t(), comparator);

		// #5 loop over the sample of t
		int i = 0; // /!\ starts at 1 in the algorithm of the thesis
		List<D> L_t = dataSet.getL_t();
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

				// ∆i(s, t): the impurity decrease of split s at node t
				// ∆i(s, t) = i(t) - p_L i(t_L) - p_R i(t_R)
				Double p_L = occ_L.getTotal() / ((double) L_t.size());
				Double p_R = occ_R.getTotal() / ((double) L_t.size());
				Double delta_iOfv_kplus1 = it - p_L * impurityG(occ_L.getOccurrences(), occ_L.getTotal())
						- p_R * impurityG(occ_R.getOccurrences(), occ_R.getTotal());

				Predicate<D> p;
				if (InspectionUtils.getFeatureType(X_j).equals(FeatureType.CATEGORICAL)) {
					D d = L_t.get(i);
					p = data -> comparator.compare(data, d) == 0;
					occ_R.addFrom(occ_L);
					occ_L.reset();
				} else {
					// v'_k: the mid-cut point between v_k and v_k+1
					Double vPrime_kplus1 = average(invokeGetterForNumber(L_t.get(i), X_j),
							invokeGetterForNumber(L_t.get(i - 1), X_j));
					p = data -> (invokeGetterForNumber(data, X_j)).doubleValue() < vPrime_kplus1;
				}

				if (delta_iOfv_kplus1 > delta) {
					delta = delta_iOfv_kplus1;
					vstar_j = new BestSplit(X_j, p, delta_iOfv_kplus1);
				}
			}
		}

		return vstar_j;
	}

	private static Double average(Number a, Number b) {
		return (a.doubleValue() + b.doubleValue()) / 2;
	}

	/**
	 * The impurity function iG(t) based on the Gini index
	 *
	 * @param N_cts
	 * @param N_t
	 * @return
	 */
	private static Double impurityG(List<Integer> N_cts, Integer N_t) {
		Double it = 0.;
		for (Integer N_ct : N_cts) {
			Double pc_kt = N_ct / (N_t.doubleValue());
			it += pc_kt * (1 - pc_kt);
		}
		return it;
	}

	/**
	 * The impurity function i_R(t) based on the local resubstitution estimate
	 * defined on the squared error loss
	 *
	 * @return
	 */
	private Double impurityR(DataSet<D, R> dataSet) {
		Integer N_t = dataSet.getL_t().size();
		Double sum = 0.;
		Double impurity = 0.;
		for (D x : dataSet.getL_t()) {
			// (XXX) Ugly as hell
			sum = sum + ((Number) InspectionUtils.invokeTarget(x)).doubleValue();
		}
		Double average = sum / N_t.doubleValue();
		for (D x : dataSet.getL_t()) {
			impurity += Math.pow(((Number) InspectionUtils.invokeTarget(x)).doubleValue() - average, 2);
		}
		impurity = impurity / N_t.doubleValue();
		return impurity;
	}

	/**
	 * The impurity function i_H(t) based on the Shannon entropy
	 *
	 * @return
	 */
	private Double impurityH(List<Integer> N_cts, Integer N_t) {
		Double it = 0.;
		for (Integer N_ct : N_cts) {
			Double pc_kt = N_ct / (N_t.doubleValue());
			it -= pc_kt * Math.log(pc_kt) / Math.log(2.);
		}
		return it;
	}
}
