package net.adrouet.broceliande.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bagging {

	private Random random;

	public Bagging(Random random) {
		this.random = random;
	}

	public <T> List<List<T>> generateSamples(List<T> data, int nbSample) {
		return this.getStream(data).limit(nbSample).collect(Collectors.toList());
	}

	public <T> Stream<List<T>> getStream(List<T> data) {
		return Stream.generate(() -> generateSample(data));
	}

	private <T> List<T> generateSample(List<T> data) {

		List<T> sample = new ArrayList<>();
		for (int j = 0; j < data.size(); j++) {
			sample.add(data.get(this.random.nextInt(data.size())));
		}

		return sample;
	}

}
