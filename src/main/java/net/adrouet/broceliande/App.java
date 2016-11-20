package net.adrouet.broceliande;

import net.adrouet.broceliande.algo.Parameter;
import net.adrouet.broceliande.algo.RandomForest;
import net.adrouet.broceliande.bean.Passenger;
import net.adrouet.broceliande.util.CsvUtils;

import java.io.IOException;
import java.util.List;

public class App {

	public static void main(String[] args) {
		// Do stuff one day

		try {
			readPassager();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public static void readPassager() throws IOException {
		List<Passenger> passengers = CsvUtils.csvToPassager("train.csv");
		Parameter p = new Parameter();
		RandomForest<Passenger, Integer> forest = new RandomForest<>(p);
		System.out.println("Fit");
		forest.fit(passengers);
		System.out.println("Predict");
		long count = passengers.stream().filter(pa -> forest.predict(pa).equals(pa.getResult())).count();
		System.out.println(count* 100 / passengers.size());

	}
}
