package net.adrouet.broceliande.struct;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public interface IDataSet {

	public Set<Comparable> getJ(); // J: set of possible results

	public Set<Method> getP(); // P: set of possible features (getters)

	public List<IData> getL_t(); // L_t: the subset of node samples falling into
									// node t
}
