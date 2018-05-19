import model.Station;
import model.Type;
import quadtree.Quad;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        StationList stationList = new StationList();
        Quad quad = new Quad(21000, -21000, -21000, 21000);

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
                quad.insert(new Station(name, longitude, latitude, type));
            }

        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        int[] stations = quad.findStationsInARadius(688.665675328263, 6192.220881827567, 15.0);  // Insgesamt 7133 Airports
        quad.findAirportsWithSpecificAmountOfTrainstationsNearBy(15.0, 20);
    }
}
