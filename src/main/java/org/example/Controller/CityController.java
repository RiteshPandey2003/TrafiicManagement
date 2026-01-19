package org.example.Controller;

import javafx.scene.input.MouseButton;
import org.example.View.CityMapView;
import org.example.model.CityGraph;
import org.example.model.Intersection;

import java.awt.event.MouseEvent;
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


    public void onIntersectionClicked(Intersection intersection, javafx.scene.input.MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            intersection.setPolluted(!intersection.isPolluted());
        }

        if (e.getButton() == MouseButton.SECONDARY) {
            intersection.setCrowded(!intersection.isCrowded());
        }

        view.drawCity();
    }
}
