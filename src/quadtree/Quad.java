package quadtree;

import model.Station;
import model.Type;

import java.util.ArrayList;
import java.util.List;


// TODO change precision level of borders from int to double
public class Quad {

    private double topBorder;
    private double leftBorder;
    private double rightBorder;
    private double bottomBorder;

    private Station station;

    private Quad topLeftTree;
    private Quad topRightTree;
    private Quad bottomLeftTree;
    private Quad bottomRightTree;

    public void insert(Station station) {

        if (station == null) {
            return;
        }

        if (!inBoundary(station.getLongitude(), station.getLatitude())) {
            return;
        }

        if (Math.abs(leftBorder - rightBorder) <= 0.0001 && Math.abs(topBorder - bottomBorder) <= 0.0001) {
            if (this.station == null) {
                this.station = station;
            }
            return;
        } else {
            if ((leftBorder + rightBorder) / 2 >= station.getLongitude()) {
                if ((topBorder + bottomBorder) / 2 <= station.getLatitude()) {
                    if (topLeftTree == null) {
                        topLeftTree = new Quad(topBorder, leftBorder, (topBorder + bottomBorder) / 2, (leftBorder + rightBorder) / 2);
                    }
                    topLeftTree.insert(station);
                } else {
                    if (bottomLeftTree == null) {
                        bottomLeftTree = new Quad((topBorder + bottomBorder) / 2, leftBorder, bottomBorder, (leftBorder + rightBorder) / 2);
                    }
                    bottomLeftTree.insert(station);
                }
            } else {
                if ((topBorder + bottomBorder) / 2 <= station.getLatitude()) {
                    if (topRightTree == null) {
                        topRightTree = new Quad(topBorder, (leftBorder + rightBorder) / 2, (topBorder + bottomBorder) / 2, rightBorder);
                    }
                    topRightTree.insert(station);
                } else {
                    if (bottomRightTree == null) {
                        bottomRightTree = new Quad((topBorder + bottomBorder) / 2, (leftBorder + rightBorder) / 2, bottomBorder, rightBorder);
                    }
                    bottomRightTree.insert(station);
                }
            }
        }
    }

    public Quad(double top, double left, double bottom, double right) {
        this.topBorder = top;
        this.bottomBorder = bottom;
        this.leftBorder = left;
        this.rightBorder = right;
    }

    public int[] findStationsInARadius(double longitude, double latitude, double radius) {
        int[] stations = new int[2];
        if (Math.abs(topBorder - bottomBorder) > 0.0001 && Math.abs(leftBorder - rightBorder) > 0.0001) {
            if (topLeftTree != null) {
                if ((longitude + radius < topLeftTree.rightBorder && latitude - radius > topLeftTree.bottomBorder) ||
                        (topLeftTree.rightBorder > longitude - radius && topLeftTree.bottomBorder < latitude + radius)) {
                    int[] tempStations = topLeftTree.findStationsInARadius(longitude, latitude, radius);
                    stations[0] += tempStations[0];
                    stations[1] += tempStations[1];
                }
            }
            if (topRightTree != null) {
                if ((longitude - radius > topRightTree.leftBorder && latitude - radius < topRightTree.bottomBorder) ||
                        (topRightTree.leftBorder < longitude + radius && topRightTree.bottomBorder < latitude + radius)) {
                    int[] tempStations = topRightTree.findStationsInARadius(longitude, latitude, radius);
                    stations[0] += tempStations[0];
                    stations[1] += tempStations[1];
                }
            }
            if (bottomLeftTree != null) {
                if ((longitude + radius < bottomLeftTree.rightBorder && latitude + radius < bottomLeftTree.topBorder) ||
                        (bottomLeftTree.rightBorder > longitude - radius && bottomLeftTree.topBorder > latitude - radius)) {
                    int[] tempStations = bottomLeftTree.findStationsInARadius(longitude, latitude, radius);
                    stations[0] += tempStations[0];
                    stations[1] += tempStations[1];
                }
            }
            if (bottomRightTree != null) {
                if ((longitude - radius > bottomRightTree.leftBorder && latitude + radius < bottomRightTree.topBorder) ||
                        (bottomRightTree.leftBorder < longitude + radius && bottomRightTree.topBorder > latitude - radius)) {
                    int[] tempStations = bottomRightTree.findStationsInARadius(longitude, latitude, radius);
                    stations[0] += tempStations[0];
                    stations[1] += tempStations[1];
                }
            }
        } else {
            if (Math.pow(station.getLongitude() - longitude, 2) + Math.pow(station.getLatitude() - latitude, 2) <= Math.pow(radius, 2)) {
                if (this.station.getType() == Type.AIRPORT) {
                    stations[1]++;
                } else {
                    stations[0]++;
                }
            }
        }
        return stations;
    }

    public void findAirportsWithSpecificAmountOfTrainstationsNearBy(double radius, int n) {
        List<Station> airports = new ArrayList<>();
        findAirports(airports);
        for (Station station : airports) {
            int trainStationCount = findStationsInARadius(station.getLongitude(), station.getLatitude(), radius)[0];
            if (trainStationCount >= n) {
                System.out.println(station.getName() + ": " + trainStationCount + " trainstations nearby.");
            }
        }
    }

    public void findAirports(List<Station> stations) {
        if (Math.abs(topBorder - bottomBorder) > 0.0001 && Math.abs(leftBorder - rightBorder) > 0.0001) {
            if (topLeftTree != null) {
                topLeftTree.findAirports(stations);
            }
            if (topRightTree != null) {
                topRightTree.findAirports(stations);
            }
            if (bottomLeftTree != null) {
                bottomLeftTree.findAirports(stations);
            }
            if (bottomRightTree != null) {
                bottomRightTree.findAirports(stations);
            }
        } else {
            if (this.station.getType() == Type.AIRPORT) {
                stations.add(station);
            }
        }
    }

    public boolean inBoundary(double longitude, double latitude) {
        return longitude >= leftBorder && longitude <= rightBorder
                && latitude <= topBorder && latitude >= bottomBorder;
    }
}
