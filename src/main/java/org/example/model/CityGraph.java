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


    public PathResult dijkstra(int source, int destination) {

        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));

        for (int id : getAllIntersectionsIds()) {
            dist.put(id, Double.POSITIVE_INFINITY);
        }

        dist.put(source, 0.0);
        pq.add(new int[]{source, 0});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();
            int u = curr[0];

            if (u == destination) break;

            for (Road road : getRoads(u)) {

                int v = road.getTo();

                double baseDistance = road.getDistance();

                // 🚦 NEW LOGIC
                double penalty = getIntersectionPenalty(v);

                double totalWeight = baseDistance + penalty;

                double alt = dist.get(u) + totalWeight;

                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                    pq.add(new int[]{v, (int) alt});
                }
            }
        }

        // 🔁 Path reconstruction
        List<Integer> path = new ArrayList<>();
        Integer step = destination;

        while (step != null) {
            path.add(0, step);
            step = prev.get(step);
        }

        return new PathResult(path, dist.get(destination));
    }


    private double getIntersectionPenalty(int intersectionId) {
        Intersection i = intersections.get(intersectionId);

        double penalty = 0;

        if (i.isCrowded()) {
            penalty += 20;
        }

        if (i.isPolluted()) {
            penalty += 40;
        }

        return penalty;
    }


}
