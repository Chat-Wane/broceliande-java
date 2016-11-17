package net.adrouet.broceliande.algo;

import java.lang.reflect.Method;

public class BestSplit {

	private final Method feature; // getter; X_t
	private final Double midCut; // mid cut point; v'_k
	private final Double impurityDecrease; // maximum impurity decrease on X_t
											// ∆i(s, t)

	public BestSplit(Method feature, Double midCut, Double impurityDecrease) {
		this.feature = feature;
		this.midCut = midCut;
		this.impurityDecrease = impurityDecrease;
	}

	public Method getFeature() {
		return feature;
	}

	public Double getMidCut() {
		return midCut;
	}

	public Double getImpurityDecrease() {
		return impurityDecrease;
	}

}
