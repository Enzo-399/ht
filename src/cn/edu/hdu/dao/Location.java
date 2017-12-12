package cn.edu.hdu.dao;

public class Location {

    private int userId;
    private String lon;
    private String lat;

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
