package org.example.Controller;

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

    private CityMapView view; // ✅ SINGLE VIEW REFERENCE

    public CityController(CityGraph cityGraph) {
        this.cityGraph = cityGraph;
    }

    public void setView(CityMapView view) {
        this.view = view; // ✅ assigned properly
    }

    public void selectIntersection(int id) {
        if (source == null) {
            source = id;
            System.out.println("Source selected: " + id);
        } else if (destination == null) {
            destination = id;
            System.out.println("Destination selected: " + id);
        } else {
            reset();
            source = id;
            System.out.println("Source reset: " + id);
        }
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
                int randomTraffic = (int) (Math.random() * 20);
                cityGraph.updateTraffic(1, 2, randomTraffic);

                // JavaFX UI update must be on FX thread
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
        System.out.println("Intersection clicked: " + intersection.getId());

        intersection.setPolluted(!intersection.isPolluted());

        view.drawCity(); // ✅ SAFE
    }
}
