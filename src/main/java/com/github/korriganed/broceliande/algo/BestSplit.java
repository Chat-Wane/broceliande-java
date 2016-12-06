package com.github.korriganed.broceliande.algo;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public class BestSplit<D> {

	/**
	 * getter; X_t
	 */
	private final Method feature;

	/**
	 * mid cut point; v'_k
	 */
	private final Predicate<D> cutPoint;

	/**
	 * maximum impurity decrease on X_t ∆i(s, t)
	 */
	private final Double impurityDecrease;

	public BestSplit(Method feature, Predicate<D> cutPoint, Double impurityDecrease) {
		this.feature = feature;
		this.cutPoint = cutPoint;
		this.impurityDecrease = impurityDecrease;
	}

	public Method getFeature() {
		return feature;
	}

	public Predicate<D> getCutPoint() {
		return cutPoint;
	}

	public Double getImpurityDecrease() {
		return impurityDecrease;
	}

	public boolean isSplit() {
		return this.cutPoint != null;
	}

	@Override
	public String toString() {
		return "BestSplit{" + "feature=" + feature.getName() + ", impurityDecrease=" + impurityDecrease + '}';
	}
}
