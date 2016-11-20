package net.adrouet.broceliande.struct;

import net.adrouet.broceliande.util.InspectionUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataSet<T extends IData> {

	private Class<T> dataClass;
	private List<T> data;
	private Set<Method> p;


	public DataSet(Class<T> cl) {
		try {
			this.p = InspectionUtils.findFeatures(cl);
			this.dataClass = cl;
		} catch (IntrospectionException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * @return set of possible results
	 */
	public Set<Comparable> getJ() {
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
	public List<T> getL_t() {
		return data;
	}

	/**
	 * Set the data for the dataset
	 *
	 * @param data
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * split the dataset into two sub data sets depending on the cutting point
	 *
	 * @param cut the cutting point
	 * @return
	 */
	public SubDataSets split(Predicate<IData> cut) {
		List<T> left = new ArrayList<>();
		List<T> right = new ArrayList<>();
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

	public Comparable getDominantResult() {
		Map<Comparable, Long> count = getL_t().stream()
				.collect(Collectors.groupingBy(d -> d.getResult(), Collectors.counting()));
		return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

}
