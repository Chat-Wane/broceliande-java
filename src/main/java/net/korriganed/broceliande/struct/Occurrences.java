package net.korriganed.broceliande.struct;

import java.util.HashMap;

import net.korriganed.broceliande.util.InspectionUtils;

public class Occurrences<D, R> {

	// target -> occurrences
	private HashMap<R, Integer> counts = new HashMap<>();
	private Integer total = 0;

	private DataSet<D, R> dataSet;

	public Occurrences(DataSet<D, R> dataSet) {
		this.dataSet = dataSet;
	}

	public void add(D x) {
		R targetX = InspectionUtils.<R> invokeGetter(x, this.dataSet.getTargetGetter());
		if (!this.counts.containsKey(targetX)) {
			this.counts.put(targetX, new Integer(0));
		}
		this.counts.compute(targetX, (t, c) -> c + 1);
		++this.total;
	}

	public void remove(D x) {
		R targetX = InspectionUtils.<R> invokeGetter(x, this.dataSet.getTargetGetter());
		this.counts.compute(targetX, (t, c) -> c - 1);
		--this.total;
	}

	public void reset() {
		this.counts.clear();
		this.total = 0;
	}

	public Integer getTotal() {
		return total;
	}

	public HashMap<R, Integer> getCounts() {
		return counts;
	}

	public DataSet<D, R> getDataSet() {
		return dataSet;
	}
}
