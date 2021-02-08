package com.example.mrt4you_mobile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Route implements Serializable {
    private int totalTime;
    private List<Subroute> subroutes = new ArrayList<>();
    private List<Node> interchanges = new ArrayList<>();
    private String start;
    private String end;
    private String path;

    public Route() { }
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
