package net.adrouet.broceliande.struct;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

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
}
