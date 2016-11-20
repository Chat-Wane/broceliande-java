package net.adrouet.broceliande;

import net.adrouet.broceliande.algo.Parameter;
import net.adrouet.broceliande.algo.RandomForest;
import net.adrouet.broceliande.bean.Passenger;
import net.adrouet.broceliande.struct.IData;
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
		List<IData> passengers = CsvUtils.csvToPassager("train.csv");
		Parameter p = new Parameter();
		RandomForest<Passenger> forest = new RandomForest<>(p);
		forest.fit(passengers);
		System.out.println(passengers.size());
	}
}
