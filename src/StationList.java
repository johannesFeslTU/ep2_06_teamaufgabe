import model.Station;
import model.Type;

import java.util.ArrayList;
import java.util.List;
public class StationList {

    private List<Station> stations;

    public StationList() {
        stations = new ArrayList<>();
    }

    public void add(Station station) {
        stations.add(station);
    }

    public int[] findStationsInARadius(double longitude, double latitude, double radius) {
        int[] count = new int[2];
        for (Station station : stations) {
            if (Math.pow(station.getLongitude() - longitude, 2) +
                    Math.pow(station.getLatitude() - latitude, 2) <=
                    Math.pow(radius, 2)) {
                if (station.getType() == Type.AIRPORT) {
                    count[1]++;
                } else {
                    count[0]++;
                }
            }
        }
        return count;
    }

    public void findAirportsWithSpecificTrainstationsNearBy(double r, int n) {
        for (Station station : stations) {
            if (station.getType() == Type.AIRPORT) {
                int trainStationCount = findStationsInARadius(station.getLongitude(), station.getLatitude(), r)[0];
                if (trainStationCount    >= n) {
                    System.out.println(station.getName() + ": " + trainStationCount + " trainstations nearby.");
                }
            }
        }
    }

    public void print() {
        for (Station station : stations) {
            System.out.println(station.toString());
        }
    }
}
