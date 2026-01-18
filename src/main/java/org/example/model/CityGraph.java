package org.example.model;

//making a graph adjacency list

import java.util.*;

public class CityGraph {
     HashMap<Integer, List<Road>> adjList = new HashMap<>();
     HashMap<Integer, Intersection> intersections = new HashMap<>();

     public void addIntersection(int id, double x, double y){
         intersections.putIfAbsent(id, new Intersection(id,x,y));
         adjList.putIfAbsent(id, new ArrayList<>());
     }

     //bidirectional graph
     public void addRoad(int from, int to, int distance){
         if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
             throw new IllegalArgumentException("Intersection does not exist");
         }

         adjList.get(from).add(new Road(from, to, distance, 0));
         adjList.get(to).add(new Road(to, from, distance, 0));
     }

     public List<Road> getRoads(int id){
          return adjList.get(id);
     }


    public boolean containsIntersection(int id) {
        return adjList.containsKey(id);
    }

    public int size() {
        return adjList.size();
    }

    public Intersection getIntersection(int id) {
        return intersections.get(id);
    }

    public void setPollution(int id, boolean value) {
        Intersection i = intersections.get(id);
        if (i != null) {
            i.setPolluted(value);
        }
    }

    public void setCrowd(int id, boolean value) {
        Intersection i = intersections.get(id);
        if (i != null) {
            i.setCrowded(value);
        }
    }

    public boolean isPolluted(int id) {
        return intersections.get(id).isPolluted();
    }

    public boolean isCrowded(int id) {
        return intersections.get(id).isCrowded();
    }

    public void updateTraffic(int from, int to, int trafficLevel) {

        for (Road road : adjList.get(from)) {
            if (road.getTo() == to) {
                road.setTrafficLevel(trafficLevel);
            }
        }

        // since bidirectional
        for (Road road : adjList.get(to)) {
            if (road.getTo() == from) {
                road.setTrafficLevel(trafficLevel);
            }
        }
    }


    private int calculateDistance(Intersection a, Intersection b) {
        return (int) Math.sqrt(
                Math.pow(a.getX() - b.getX(), 2) +
                        Math.pow(a.getY() - b.getY(), 2)
        );
    }

    public Set<Integer> getAllIntersectionsIds() {
        return intersections.keySet();
    }

    public Collection<Intersection> getAllIntersections() {
        return intersections.values();
    }


}
