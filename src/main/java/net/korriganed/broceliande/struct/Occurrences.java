package net.korriganed.broceliande.struct;

import java.util.ArrayList;
import java.util.List;

import net.korriganed.broceliande.util.InspectionUtils;

public class Occurrences<D, R> {

	private ArrayList<R> targets = new ArrayList<>();
	private ArrayList<Integer> occurrences = new ArrayList<>();
	private DataSet<D, R> dataSet;

	private Integer total = 0;

	public Occurrences(DataSet<D, R> dataSet) {
		this.dataSet = dataSet;
		for (R t : dataSet.getJ()) {
			this.targets.add(t);
			this.occurrences.add(0);
		}
	}

	public void add(D x) {
		int i = 0;
		R targetX = (R) InspectionUtils.invokeGetter(x, this.dataSet.getTargetGetter());
		for (R target : this.targets) {
			if (target.equals(targetX)) {
				this.occurrences.set(i, this.occurrences.get(i) + 1);
			}
			++i;
		}
		++this.total;
	}

	public void addFrom(Occurrences<D, R> o) {
		for (int i = 0; i < o.getTargets().size(); ++i) {
			for (int j = 0; j < this.getTargets().size(); ++j) {
				if (o.getTargets().get(i).equals(this.getTargets().get(j))) {
					this.occurrences.set(j, this.occurrences.get(j) + o.getOccurrences().get(i));
				}
			}
		}
		this.total += o.getTotal();
	}

	public void remove(D x) {
		int i = 0;
		R targetX = (R) InspectionUtils.invokeGetter(x, this.dataSet.getTargetGetter());
		for (R target : this.targets) {
			if (target.equals(targetX)) {
				this.occurrences.set(i, this.occurrences.get(i) - 1);
			}
			++i;
		}
		--this.total;
	}

	public void reset() {
		for (int i = 0; i < this.occurrences.size(); ++i) {
			this.occurrences.set(i, 0);
		}
		this.total = 0;
	}

	public ArrayList<Integer> getOccurrences() {
		return occurrences;
	}

	public Integer getTotal() {
		return total;
	}

	public List<R> getTargets() {
		return targets;
	}
}
