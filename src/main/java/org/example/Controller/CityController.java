package org.example.Controller;


import org.example.View.CityMapView;
import org.example.model.CityGraph;
import org.example.model.Intersection;
import org.example.model.PathResult;


import java.util.Timer;
import java.util.TimerTask;

public class CityController {

    private CityGraph cityGraph;
    private Integer source;
    private Integer destination;
    private Timer trafficTimer;
    private PathResult currentPath;

    private CityMapView view;

    public CityController(CityGraph cityGraph) {
        this.cityGraph = cityGraph;
    }

    public void setView(CityMapView view) {
        this.view = view;
    }

    public void selectIntersection(int id) {
        if (source == null) {
            source = id;
            System.out.println("SOURCE selected: " + source);

        } else if (destination == null) {
            destination = id;
            System.out.println("DESTINATION selected: " + destination);
            computeShortestPath();
        } else {
            reset();
            source = id;
            System.out.println("RESET — new SOURCE selected: " + source);
        }

        printSelectionState();
    }

    private void printSelectionState() {
        System.out.println("Current State -> source=" + source + ", destination=" + destination);
    }




    public void reset() {
        source = null;
        destination = null;
    }

    public void startTrafficSimulation() {
        trafficTimer = new Timer(true);

        trafficTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                for (Intersection i : cityGraph.getAllIntersections()) {

                    int traffic = (int) (Math.random() * 100);


                    i.setCrowded(traffic > 60);
                    i.setPolluted(traffic > 90);
                }

                javafx.application.Platform.runLater(() -> {

                    view.drawCity();

                    // 🔥 LIVE ROUTE UPDATE
                    if (source != null && destination != null) {
                        computeShortestPath();
                    }
                });
            }
        }, 0, 3000);
    }




    public Integer getSource() {
        return source;
    }

    public Integer getDestination() {
        return destination;
    }

    public PathResult getCurrentPath() { return currentPath; }

    private void computeShortestPath() {

        if (source == null || destination == null) return;

        currentPath = cityGraph.dijkstra(source, destination);

        System.out.println("Shortest Path = " + currentPath.getPath());


        view.setPath(currentPath.getPath());
    }

}
