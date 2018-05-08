package model;

public class Station {

    private String name;
    private double longitude;
    private double latitude;
    private Type type;

    public Station(String name, double longitude, double latitude, Type type) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "model.Station{" +
                "name='" + name + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", type=" + type +
                '}';
    }
}
