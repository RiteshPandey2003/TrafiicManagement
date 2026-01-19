package org.example.model;

//Road is a Edge that connect one node to another node;
// edge take from (start node) and to (end Node) Distance (weight) and trafic;
public class Road {
    int from;
    int to;
    int distance;
    int trafficLevel; //dynamic

    public Road(int from, int to, int distance, int trafficLevel) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.trafficLevel = trafficLevel;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getDistance() {
        return distance;
    }

    public int getTrafficLevel() {
        return trafficLevel;
    }

    public void setTrafficLevel(int trafficLevel) {
        this.trafficLevel = trafficLevel;
    }

    //final cost used by algorithms
    public int getWeight() {
        return distance + trafficLevel;
    }

    @Override
    public String toString(){
        return "[ " + from + " " + to + " " + distance + " " + trafficLevel +" ]";
    }
}
