package org.example.model;

import java.util.List;

public class PathResult{
    private final List<Integer> path;
    private final double totalCost;

    public PathResult(List<Integer> path, double totalCost) {
        this.path = path;
        this.totalCost = totalCost;
    }

    public List<Integer> getPath() {
        return path;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
