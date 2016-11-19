package net.adrouet.broceliande.data;

import net.adrouet.broceliande.struct.IData;
import net.adrouet.broceliande.struct.IDataSet;
import net.adrouet.broceliande.struct.SubDataSets;
import net.adrouet.broceliande.util.InspectionUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestDataSet implements IDataSet {

	private List<IData> data;
	private Set<Comparable> j;

	public TestDataSet(List<IData> data) {
		this.data = data;
		j = data.stream().map(IData::getResult).distinct().collect(Collectors.toSet());
	}

	@Override
	public Set<Comparable> getJ() {
		return j;
	}

	@Override
	public Set<Method> getP() {
		try {
			return InspectionUtils.findFeatures(TestData.class);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<IData> getL_t() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubDataSets split(Predicate<IData> cut) {
		// TODO Auto-generated method stub
		return null;
	}

}
