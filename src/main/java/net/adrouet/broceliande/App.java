package net.adrouet.broceliande;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.adrouet.broceliande.algo.Parameter;
import net.adrouet.broceliande.algo.RandomForest;
import net.adrouet.broceliande.bean.Passenger;
import net.adrouet.broceliande.util.CsvUtils;

public class App {

	private static Logger LOG = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		// Do stuff one day

		try {
			readPassager();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void readPassager() throws IOException {
		List<Passenger> train = CsvUtils.csvToPassager("train.csv");
		// List<Passenger> test = CsvUtils.csvToPassager("test.csv");
		Parameter p = new Parameter.Builder().build();
		RandomForest<Passenger, Integer> forest = new RandomForest<>(p);
		// System.out.println("Fit");
		for (int i = 0; i < 10; ++i) {
			forest.fit(train);
			// System.out.println("Predict");
			long count = train.stream().filter(pa -> forest.predict(pa).equals(pa.getResult())).count();
			LOG.info("Success rate: {}%", String.format("%.2f", count * 100 / (double) train.size()));
		}

		List<ImmutablePair<String, Double>> rank = forest.importance();
		for (ImmutablePair<String, Double> e : rank) {
			LOG.info("Importance: feature {} - {}", String.format("%.3f", e.getRight()), e.getLeft());
		}
	}
}
