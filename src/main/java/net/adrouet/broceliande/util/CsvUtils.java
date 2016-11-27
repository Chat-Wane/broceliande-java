package net.adrouet.broceliande.util;

import com.opencsv.CSVReader;
import net.adrouet.broceliande.bean.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class CsvUtils {

	private static final Logger LOG = LoggerFactory.getLogger(CsvUtils.class);

	public static void writeResult(List<Passenger> passengerList) throws IOException {
		File file = new File("result.csv");
		LOG.info("Writing result in {}", file.getAbsolutePath());
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write("PassengerId,Survived\n");
		for (Passenger p : passengerList) {
			fileWriter.write(p.getPassengerId() + "," + p.getResult() + "\n");
		}
		fileWriter.close();
	}

	public static <T> List<T> csvToBean(String filename, Class<T> clazz) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		String file = classloader.getResource(filename).getFile();
		FileReader fReader = new FileReader(file);
		CSVReader reader = new CSVReader(fReader);
		String[] nextLine;
		List<T> result = new ArrayList<>();
		nextLine = reader.readNext(); // Ignore header
		HashMap<Integer, Method> headers = new HashMap<>();
		Map<String, Method> setters = InspectionUtils.findSetter(Passenger.class);
		for (int i = 0; i < nextLine.length; i++) {
			headers.put(i, setters.get(nextLine[i].toLowerCase()));
		}

		while ((nextLine = reader.readNext()) != null) {
			T t = clazz.newInstance();
			for (int i = 0; i < nextLine.length; i++) {
				Class<?> param = headers.get(i).getParameterTypes()[0];
				if (param.equals(Integer.class)) {
					headers.get(i).invoke(t, getInteger(nextLine[i]));
				} else if (param.equals(Double.class)) {
					headers.get(i).invoke(t, getDouble(nextLine[i]));
				} else {
					headers.get(i).invoke(t, nextLine[i]);
				}
			}
			result.add(t);
		}
		return result;
	}

	private static Integer getInteger(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		try {
			return Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static Double getDouble(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		try {
			return Double.valueOf(s);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
