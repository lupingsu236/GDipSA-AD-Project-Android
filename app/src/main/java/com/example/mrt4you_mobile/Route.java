package com.example.mrt4you_mobile;

import java.util.ArrayList;
import java.util.List;

public class Route {
    private int totalTime;
    private List<Subroute> subroutes = new ArrayList<>();
    private List<Node> interchanges = new ArrayList<>();
    private String path;

    public Route(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public List<Subroute> getSubroutes() {
        return subroutes;
    }

    public void setSubroutes(List<Subroute> subroutes) {
        this.subroutes = subroutes;
    }

    public List<Node> getInterchanges() {
        return interchanges;
    }

    public void setInterchanges(List<Node> interchanges) {
        this.interchanges = interchanges;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
