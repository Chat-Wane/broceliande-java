package net.adrouet.broceliande.struct;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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
	 * split the dataset into two sub data sets depending on the cutting point
	 * 
	 * @param cut
	 *            the cutting point
	 * @return
	 */
	SubDataSets split(Predicate<IData> cut);
}
