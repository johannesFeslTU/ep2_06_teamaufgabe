package quadtree;

import model.Station;


// TODO change precision level of borders from int to double
public class Quad {

    int topBorder;
    int leftBorder;
    int rightBorder;
    int bottomBorder;

    Station station;

    Quad topLeftTree;
    Quad topRightTree;
    Quad bottomLeftTree;
    Quad bottomRightTree;

    public Quad(int top, int left, int bottom, int right) {
        this.topBorder = top;
        this.bottomBorder = bottom;
        this.leftBorder = left;
        this.rightBorder = right;
    }

    public void insert (Station station) {

        if (station == null) {
            return;
        }

        if (!inBoundary(station.getLongitude(), station.getLatitude())) {
            return;
        }

        if (Math.abs(leftBorder - rightBorder) <= 1 && Math.abs(topBorder - bottomBorder) <= 1) {
            if (this.station == null) {
                this.station = station;
            }
        }

        if ((leftBorder - rightBorder) / 2 >= station.getLongitude()) {
            if ((topBorder - bottomBorder) / 2 >= station.getLatitude()) {
                if (topLeftTree == null) {
                    topLeftTree = new Quad(topBorder, leftBorder, (topBorder + bottomBorder) / 2, (leftBorder + rightBorder) / 2);
                }
                topLeftTree.insert(station);
            }
            else {
                if (bottomLeftTree == null) {
                    bottomLeftTree = new Quad((topBorder + bottomBorder) / 2, leftBorder, bottomBorder, (leftBorder + rightBorder) / 2);
                }
                bottomLeftTree.insert(station);
            }
        }
        else {
            if ((topBorder - bottomBorder) / 2 >= station.getLatitude()) {
                if (topRightTree == null) {
                    topRightTree = new Quad(topBorder, (leftBorder + rightBorder) / 2, (topBorder + bottomBorder) / 2, rightBorder);
                }
                topRightTree.insert(station);
            }
            else {
                if (bottomRightTree == null) {
                    bottomRightTree = new Quad((topBorder + bottomBorder) / 2, (leftBorder + rightBorder / 2), bottomBorder, rightBorder);
                }
                bottomRightTree.insert(station);
            }
        }
    }

    public Station search() {
        return null;
    }

    public boolean inBoundary(double longitude, double latitude) {
        return longitude >= leftBorder && longitude <= rightBorder
                && latitude <= topBorder && latitude >= bottomBorder;
    }
}
