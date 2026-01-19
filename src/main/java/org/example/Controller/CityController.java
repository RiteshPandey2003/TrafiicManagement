package org.example.Controller;

import javafx.scene.input.MouseButton;
import org.example.View.CityMapView;
import org.example.model.CityGraph;
import org.example.model.Intersection;


import java.util.Timer;
import java.util.TimerTask;

public class CityController {

    private CityGraph cityGraph;
    private Integer source;
    private Integer destination;
    private Timer trafficTimer;

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

    public void changeTraffic(int from, int to, int level) {
        cityGraph.updateTraffic(from, to, level);
        view.drawCity(); // optional refresh
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

                javafx.application.Platform.runLater(() ->
                        view.drawCity()
                );
            }
        }, 0, 3000);
    }

    public void stopTrafficSimulation() {
        if (trafficTimer != null) {
            trafficTimer.cancel();
        }
    }


    public void onIntersectionClicked(Intersection intersection) {
        selectIntersection(intersection.getId());
        view.drawCity();
    }


    public Integer getSource() {
        return source;
    }

    public Integer getDestination() {
        return destination;
    }
}
