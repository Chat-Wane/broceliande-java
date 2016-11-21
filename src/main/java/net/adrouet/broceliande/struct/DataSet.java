package net.adrouet.broceliande.struct;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.adrouet.broceliande.util.InspectionUtils;

public class DataSet<D extends IData<R>, R extends Comparable<R>> {

	private Class<D> dataClass;
	private List<D> data;
	private Set<Method> p;

	public DataSet(Class<D> cl) {
		this.p = InspectionUtils.findFeatures(cl);
		this.dataClass = cl;
	}

	/**
	 * @return set of possible results
	 */
	public Set<R> getJ() {
		return data.stream().map(IData::getResult).distinct().collect(Collectors.toSet());
	}

	/**
	 * @return set of possible features (getters)
	 */
	public Set<Method> getP() {
		return p;
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
		this.data = data;
	}

	/**
	 * split the dataset into two sub data sets depending on the cutting point
	 *
	 * @param cut
	 *            the cutting point
	 * @return
	 */
	public SubDataSets split(Predicate<IData> cut) {
		List<D> left = new ArrayList<>();
		List<D> right = new ArrayList<>();
		getL_t().forEach(d -> {
			if (cut.test(d)) {
				left.add(d);
			} else {
				right.add(d);
			}
		});

		DataSet leftDataSet = new DataSet<>(dataClass);
		leftDataSet.setData(left);
		DataSet rightDataSet = new DataSet<>(dataClass);
		rightDataSet.setData(right);
		return new SubDataSets(leftDataSet, rightDataSet);

	}

	public R getDominantResult() {
		Map<R, Long> count = data.stream().collect(Collectors.groupingBy(d -> d.getResult(), Collectors.counting()));
		return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

}
