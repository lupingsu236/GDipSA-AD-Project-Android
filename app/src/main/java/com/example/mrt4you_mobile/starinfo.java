package com.example.mrt4you_mobile;

public class starinfo {
    private String name;
    private String station;
    private String line;

    public starinfo(String name, String station, String line) {
        this.name = name;
        this.station = station;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
