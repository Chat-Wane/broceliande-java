package net.korriganed.broceliande.struct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.korriganed.broceliande.util.InspectionUtils;

public class DataSet<D, R> {

	private List<D> data;
	private Set<Method> features;
	private Set<R> targets;

	public DataSet(Class<?> cl) {
		this.features = InspectionUtils.findFeatures(cl);
	}

	private DataSet(Set<Method> features, Set<R> targets) {
		this.features = features;
		this.targets = targets;
	}

	/**
	 * @return set of possible results
	 */
	public Set<R> getJ() {
		return this.targets;
	}

	/**
	 * @return set of possible features (getters)
	 */
	public Set<Method> getP() {
		return this.features;
	}

	/**
	 * @return the subset of node samples falling into node t
	 */
	public List<D> getL_t() {
		return data;
	}

	/**
	 * Set the data for the dataset
	 *
	 * @param data
	 */
	public void setData(List<D> data) {
		if (this.targets == null) {
			this.targets = data.stream().map(d -> (R) InspectionUtils.invokeTarget(d)).distinct()
					.collect(Collectors.toSet());
		}
		this.data = data;
	}

	/**
	 * split the dataset into two sub data sets depending on the cutting point
	 *
	 * @param cut
	 *            the cutting point
	 * @return
	 */
	public SubDataSets<D, R> split(Predicate<D> cut) {
		List<D> left = new ArrayList<>();
		List<D> right = new ArrayList<>();
		getL_t().forEach(d -> {
			if (cut.test(d)) {
				left.add(d);
			} else {
				right.add(d);
			}
		});

		DataSet<D, R> leftDataSet = new DataSet<>(this.features, this.targets);
		leftDataSet.setData(left);
		DataSet<D, R> rightDataSet = new DataSet<>(this.features, this.targets);
		rightDataSet.setData(right);
		return new SubDataSets<>(leftDataSet, rightDataSet);

	}

	public R getDominantResult() {
		Map<R, Long> count = data.stream()
				.collect(Collectors.groupingBy(InspectionUtils::invokeTarget, Collectors.counting()));
		return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

}
