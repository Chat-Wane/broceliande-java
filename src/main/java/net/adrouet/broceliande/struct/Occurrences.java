package net.adrouet.broceliande.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Occurrences<D extends IData<R>, R extends Comparable<R>> {

	private ArrayList<R> targets = new ArrayList<>();
	private ArrayList<Integer> occurrences = new ArrayList<>();

	private Integer total = 0;

	public Occurrences(Set<R> ts) {
		for (R t : ts) {
			this.targets.add(t);
			this.occurrences.add(0);
		}
	}

	public void add(D x) {
		int i = 0;
		for (R target : this.targets) {
			if (target.compareTo(x.getResult()) == 0) {
				this.occurrences.set(i, this.occurrences.get(i) + 1);
			}
			++i;
		}
		++this.total;
	}

	public void addFrom(Occurrences<D, R> o) {
		for (int i = 0; i < o.getTargets().size(); ++i) {
			for (int j = 0; j < this.getTargets().size(); ++j) {
				if (o.getTargets().get(i).compareTo(this.getTargets().get(j)) == 0) {
					this.occurrences.set(j, this.occurrences.get(j) + o.getOccurrences().get(i));
				}
			}
		}
		this.total += o.getTotal();
	}

	public void remove(D x) {
		int i = 0;
		for (R target : this.targets) {
			if (target.compareTo(x.getResult()) == 0) {
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
