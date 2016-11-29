package net.korriganed.broceliande.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.korriganed.broceliande.struct.IData;

public class Bagging<D extends IData<?>> {

	private Random random;

	public Bagging(Random random) {
		this.random = random;
	}

	public List<List<D>> generateSamples(List<D> data, int nbSample) {
		return this.getStream(data).limit(nbSample).collect(Collectors.toList());
	}

	public Stream<List<D>> getStream(List<D> data) {
		return Stream.generate(() -> generateSample(data));
	}

	private List<D> generateSample(List<D> data) {

		List<D> sample = new ArrayList<>();
		for (int j = 0; j < data.size(); j++) {
			sample.add(data.get(this.random.nextInt(data.size())));
		}

		return sample;
	}

}
