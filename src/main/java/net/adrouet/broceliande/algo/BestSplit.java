package net.adrouet.broceliande.algo;

import net.adrouet.broceliande.struct.IData;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class BestSplit {

	private final Method feature; // getter; X_t
	private final Predicate<IData> cutPoint; // mid cut point; v'_k
	private final Double impurityDecrease; // maximum impurity decrease on X_t
											// ∆i(s, t)

	public BestSplit(Method feature, Predicate<IData> cutPoint, Double impurityDecrease) {
		this.feature = feature;
		this.cutPoint = cutPoint;
		this.impurityDecrease = impurityDecrease;
	}

	public Method getFeature() {
		return feature;
	}

	public Predicate<IData> getCutPoint() {
		return cutPoint;
	}

	public Double getImpurityDecrease() {
		return impurityDecrease;
	}

}
