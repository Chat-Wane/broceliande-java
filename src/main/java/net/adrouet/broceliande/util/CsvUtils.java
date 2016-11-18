package net.adrouet.broceliande.util;

import com.opencsv.CSVReader;
import net.adrouet.broceliande.bean.Passenger;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    public static List<Passenger> csvToPassager(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String file = classloader.getResource(filename).getFile();
        FileReader fReader = new FileReader(file);
        CSVReader reader = new CSVReader(fReader);
        String [] nextLine;
        ArrayList<Passenger> result = new ArrayList<>();
        reader.readNext(); // Ignore header
        while ((nextLine = reader.readNext()) != null) {
            Passenger p = new Passenger();
            p.setPassengerId(getInteger(nextLine[0]));
            p.setSurvived(getInteger(nextLine[1]));
            p.setPclass(getInteger(nextLine[2]));
            p.setName(nextLine[3]);
            p.setSex(nextLine[4]);
            p.setAge(getInteger(nextLine[5]));
            p.setSibSp(getInteger(nextLine[6]));
            p.setParch(getInteger(nextLine[7]));
            p.setTicket(nextLine[8]);
            p.setFare(getDouble(nextLine[9]));
            p.setCabin(nextLine[10]);
            p.setEmbarked(nextLine[11]);
            result.add(p);
        }
        return result;
    }

    private static Integer getInteger(String s){
        if(s==null || s.isEmpty()){
            return null;
        }
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static Double getDouble(String s){
        if(s==null || s.isEmpty()){
            return null;
        }
        try {
            return Double.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
