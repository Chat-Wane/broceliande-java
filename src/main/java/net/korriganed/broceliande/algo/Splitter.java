package net.korriganed.broceliande.algo;

import static net.korriganed.broceliande.util.InspectionUtils.invokeGetter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.korriganed.broceliande.data.FeatureType;
import net.korriganed.broceliande.data.TargetType;
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

	public BestSplit<D> findBestSplit(DataSet<D, R> dataSet) {
		if (this.k == null) {
			this.k = dataSet.getFeatureGetters().size();
		}

		BestSplit<D> bestSplit = new BestSplit<>(null, null, 0.);
		Double delta = Double.NEGATIVE_INFINITY;
		ArrayList<Method> randomFeatureGetters = new ArrayList<>(dataSet.getFeatureGetters());
		// #1 draw random getters from the features
		Collections.shuffle(randomFeatureGetters, this.random);
		while (randomFeatureGetters.size() > this.k) {
			randomFeatureGetters.remove(randomFeatureGetters.size() - 1);
		}
		for (Method featureGetter : randomFeatureGetters) {
			// #1 find the best binary split s*_j defined on X_j
			BestSplit<D> bestSplitOfFeature = this.findBestSplit(dataSet, featureGetter);
			if (bestSplitOfFeature.getImpurityDecrease() > delta) {
				delta = bestSplitOfFeature.getImpurityDecrease();
				bestSplit = bestSplitOfFeature;
			}
		}
		LOG.trace("Best split found: {}", bestSplit);

		return bestSplit;
	}

	/**
	 * See page 50 of LOUPPE's thesis about random forests
	 *
	 * @param dataSet
	 *            the subset of node samples falling into node t (contains L_t)
	 * @param featureGetter
	 *            the j-th input variable or feature (getter)
	 */
	public BestSplit<D> findBestSplit(DataSet<D, R> dataSet, Method featureGetter) {
		BestSplit<D> bestSplitOfFeature = new BestSplit<>(featureGetter, null, 0.);
		Double delta = 0.;

		Occurrences<D, R> occ_R = new Occurrences<>(dataSet);
		dataSet.getSample().forEach(occ_R::add);
		Occurrences<D, R> occ_L = new Occurrences<>(dataSet);

		Double it = impurity(occ_R);

		List<D> sample = dataSet.getSample();
		Integer sampleSize = sample.size();

		if (InspectionUtils.getFeatureType(featureGetter).equals(FeatureType.CATEGORICAL)) {
			// #A CATEGORICAL FEATURE
			Stream<List<D>> sampleGroupByFeatureValue = sample.stream().map(data -> invokeGetter(data, featureGetter))
					.distinct()
					.map(featureValue -> sample.stream()
							.filter(data -> invokeGetter(data, featureGetter).equals(featureValue))
							.collect(Collectors.toList()));

			bestSplitOfFeature = sampleGroupByFeatureValue.map(group -> {
				Occurrences<D, R> right = new Occurrences<>(dataSet);
				dataSet.getSample().forEach(right::add);
				Occurrences<D, R> left = new Occurrences<>(dataSet);
				group.forEach(d -> {
					right.remove(d);
					left.add(d);
				});
				Double p_L = left.getTotal() / ((double) sample.size());
				Double p_R = right.getTotal() / ((double) sample.size());
				Double impurityDecrease = it - p_L * impurity(left) - p_R * impurity(right);
				D d = group.get(0);
				Predicate<D> p = toCheck -> invokeGetter(toCheck, featureGetter).equals(invokeGetter(d, featureGetter));
				BestSplit<D> split = new BestSplit<>(featureGetter, p, impurityDecrease);
				return split;
			}).max(Comparator.comparing(BestSplit::getImpurityDecrease)).orElse(bestSplitOfFeature);
		} else {
			// #B ORDERED FEATURE
			Comparator<D> comparator = (x1, x2) -> InspectionUtils.<Comparable> invokeGetter(x1, featureGetter)
					.compareTo(InspectionUtils.<Comparable> invokeGetter(x2, featureGetter));
			Collections.sort(dataSet.getSample(), comparator);

			// #1 loop over the ordered sample
			int i = 0;
			while (i < sampleSize) {
				while (((i + 1) < sampleSize) && (comparator.compare(sample.get(i), sample.get(i + 1)) == 0)) {
					occ_L.add(sample.get(i));
					occ_R.remove(sample.get(i));
					++i;
				}
				occ_L.add(sample.get(i));
				occ_R.remove(sample.get(i));
				++i;
				if (i < sampleSize) {
					// ∆i(s, t): the impurity decrease of split s at node t
					// ∆i(s, t) = i(t) - p_L i(t_L) - p_R i(t_R)
					Double p_L = occ_L.getTotal() / ((double) sample.size());
					Double p_R = occ_R.getTotal() / ((double) sample.size());
					Double impurityDecrease = it - p_L * impurity(occ_L) - p_R * impurity(occ_R);

					// v'_k: the mid-cut point between the analyzed values
					Double midCutPoint = average(InspectionUtils.<Number> invokeGetter(sample.get(i), featureGetter),
							InspectionUtils.<Number> invokeGetter(sample.get(i - 1), featureGetter));
					Predicate<D> p = data -> (InspectionUtils.<Number> invokeGetter(data, featureGetter))
							.doubleValue() < midCutPoint;

					if (impurityDecrease > delta) {
						delta = impurityDecrease;
						bestSplitOfFeature = new BestSplit<>(featureGetter, p, impurityDecrease);
					}
				}
			}
		}

		return bestSplitOfFeature;
	}

	private static Double average(Number a, Number b) {
		return (a.doubleValue() + b.doubleValue()) / 2;
	}

	private static <D, R> Double impurity(Occurrences<D, R> counts) {
		Double result = 0.;
		if (InspectionUtils.getTargetType(counts.getDataSet().getTargetGetter()).equals(TargetType.CONTINUOUS)) {
			result = impurityR(counts);
		} else {
			result = impurityG(counts);
		}
		return result;
	}

	/**
	 * The impurity function iG(t) based on the Gini index
	 *
	 * @param subSampleSizes
	 * @param totalSampleSize
	 * @return
	 */
	private static <D, R> Double impurityG(Occurrences<D, R> counts) {
		Double impurity = 0.;
		for (Entry<R, Integer> c : counts.getCounts().entrySet()) {
			impurity += c.getValue() / counts.getTotal().doubleValue()
					* (1 - c.getValue() / counts.getTotal().doubleValue());
		}

		return impurity;
	}

	/**
	 * The impurity function i_R(t) based on the local resubstitution estimate
	 * defined on the squared error loss
	 *
	 * @return
	 */
	private static <D, R> Double impurityR(Occurrences<D, R> counts) {
		Double sum = 0.;
		for (Entry<R, Integer> c : counts.getCounts().entrySet()) {
			sum += ((Number) c.getKey()).doubleValue() * c.getValue();
		}
		Double average = sum / counts.getTotal();

		Double impurity = 0.;
		for (Entry<R, Integer> c : counts.getCounts().entrySet()) {
			impurity += c.getValue() * Math.pow(((Number) c.getKey()).doubleValue() - average, 2);
		}
		impurity = impurity / counts.getTotal();

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
