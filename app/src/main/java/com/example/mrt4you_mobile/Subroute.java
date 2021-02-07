package com.example.mrt4you_mobile;

import java.util.LinkedList;
import java.util.List;

public class Subroute {
    private String line;
    private int noOfStations;
    private int noOfMins;
    private String direction;
    private List<Node> stations = new LinkedList<>();

    public Subroute(String line, String direction) {
        this.line = line;
        this.direction = direction;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public int getNoOfStations() {
        return noOfStations;
    }

    public void setNoOfStations(int noOfStations) {
        this.noOfStations = noOfStations;
    }

    public int getNoOfMins() {
        return noOfMins;
    }

    public void setNoOfMins(int noOfMins) {
        this.noOfMins = noOfMins;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<Node> getStations() {
        return stations;
    }

    public void setStations(List<Node> stations) {
        this.stations = stations;
    }


}
