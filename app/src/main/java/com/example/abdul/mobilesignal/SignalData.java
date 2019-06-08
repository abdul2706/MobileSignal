package com.example.abdul.mobilesignal;

public class SignalData {

    private double signalStrength;
    private double longitude;
    private double latitude;

    public SignalData() {
        this(0, 0, 0);
    }

    public SignalData(double signalStrength, double longitude, double latitude) {
        this.signalStrength = signalStrength;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public SignalData(String[] values) {
        this.signalStrength = Double.parseDouble(values[0]);
        this.longitude = Double.parseDouble(values[1]);
        this.latitude = Double.parseDouble(values[2]);
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
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

    public static String convertToQuery(SignalData signalStrength) {
        return signalStrength.toString();
    }

    @Override
    public String toString() {
        return "SignalData{" +
                "signalStrength=" + signalStrength +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
