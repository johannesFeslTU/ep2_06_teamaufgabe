import model.Station;
import model.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        StationList stationList = new StationList();

        try(Scanner scanner  = new Scanner(
                new File(System.getProperty("user.dir") +
                "/data/junctions.csv"), "UTF-8")) {
            String line = null;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] columns = line.split(";");
                String name = columns[0];
                double longitude = Double.parseDouble(columns[1]);
                double latitude = Double.parseDouble(columns[2]);
                Type type = columns[3].equals("AIRPORT") ? Type.AIRPORT : Type.TRAINSTATION;
                stationList.add(new Station(name, longitude, latitude, type));
            }

        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        stationList.findAirportsWithSpecificTrainstationsNearBy(15.0, 20);
    }
}
