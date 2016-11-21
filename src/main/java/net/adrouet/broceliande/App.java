package net.adrouet.broceliande;

import net.adrouet.broceliande.algo.Parameter;
import net.adrouet.broceliande.algo.RandomForest;
import net.adrouet.broceliande.bean.Passenger;
import net.adrouet.broceliande.util.CsvUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
		Parameter p = new Parameter();
		RandomForest<Passenger, Integer> forest = new RandomForest<>(p);
		// System.out.println("Fit");
		for (int i = 0; i < 100; ++i) {
			forest.fit(train);
			// System.out.println("Predict");
			long count = train.stream().filter(pa -> forest.predict(pa).equals(pa.getResult())).count();
			LOG.info("Success rate: {}%", String.format("%.2f", count * 100 / (double) train.size()));
		}
	}
}
