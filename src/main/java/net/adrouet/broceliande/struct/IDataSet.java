package net.adrouet.broceliande.struct;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IDataSet {

	/**
	 * @return set of possible results
	 */
	Set<Comparable> getJ();

	/**
	 * @return set of possible features (getters)
	 */
	Set<Method> getP();

	/**
	 * @return the subset of node samples falling into node t
	 */
	List<IData> getL_t();

	/**
	 * Set the data for the dataset
	 * @param data
	 */
	void setData(List<IData> data);

	/**
	 * split the dataset into two sub data sets depending on the cutting point
	 *
	 * @param cut the cutting point
	 * @return
	 */
	default <T extends IDataSet> SubDataSets split(Predicate<IData> cut, Class<T> cl) {
		List<IData> left = new ArrayList<>();
		List<IData> right = new ArrayList<>();
		getL_t().forEach(d -> {
			if (cut.test(d)) {
				left.add(d);
			} else {
				right.add(d);
			}
		});
		try {
			T leftDataSet = cl.newInstance();
			leftDataSet.setData(left);
			T rightDataSet = cl.newInstance();
			rightDataSet.setData(right);
			return new SubDataSets(leftDataSet, rightDataSet);
		} catch (IllegalAccessException | InstantiationException e) {
			throw new RuntimeException("Unable to call default constructor on " + cl.getName());
		}
	}

	default Comparable getDominantResult(){
		Map<Comparable, Long> count = getL_t().stream()
				.collect(Collectors.groupingBy(d -> d.getResult(), Collectors.counting()));
		return Collections.max(count.entrySet(), Map.Entry.comparingByValue()).getKey();
	}

}
