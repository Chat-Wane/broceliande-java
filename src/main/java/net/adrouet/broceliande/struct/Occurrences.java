package net.adrouet.broceliande.struct;

import java.util.ArrayList;
import java.util.Set;

public class Occurrences {

	private ArrayList<Comparable> targets = new ArrayList<>();
	private ArrayList<Integer> occurrences = new ArrayList<>();

	Integer total = 0;

	public Occurrences(Set<Comparable> ts) {
		for (Comparable t : ts) {
			this.targets.add(t);
			this.occurrences.add(new Integer(0));
		}
	}

	public void add(IData x) {
		int i = 0;
		for (Comparable target : this.targets) {
			if (target.compareTo(x.getResult()) == 0) {
				this.occurrences.set(i, this.occurrences.get(i) + 1);
			}
			++i;
		}
		++this.total;
	}

	public void remove(IData x) {
		int i = 0;
		for (Comparable target : this.targets) {
			if (target.compareTo(x.getResult()) == 0) {
				this.occurrences.set(i, this.occurrences.get(i) - 1);
			}
			++i;
		}
		--this.total;
	}

	public void reset() {
		for (int i = 0; i < this.occurrences.size(); ++i) {
			this.occurrences.set(i, new Integer(0));
		}
		this.total = 0;
	}

	public ArrayList<Integer> getOccurrences() {
		return occurrences;
	}

	public Integer getTotal() {
		return total;
	}
}
