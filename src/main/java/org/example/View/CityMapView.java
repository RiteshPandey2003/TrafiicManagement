package org.example.View;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.Controller.CityController;
import org.example.model.CityGraph;
import org.example.model.Intersection;
import org.example.model.Road;


import java.awt.*;
import java.util.List;

public class CityMapView extends Canvas {

    private final Pane root;
    private final CityGraph cityGraph;
    private CityController controller;

    public CityMapView(CityGraph cityGraph, CityController controller) {
        this.cityGraph = cityGraph;
        this.controller = controller;
        this.root = new Pane();
        drawCity();
    }

    public Pane getRoot() {
        return root;
    }

    public void setController(CityController controller) {
        this.controller = controller;
    }

    public void drawCity() {
        drawRoads();
        drawIntersections();
    }

    private void drawRoads() {

        for (int from : cityGraph.getAllIntersectionsIds()) {

            Intersection a = cityGraph.getIntersection(from);
            List<Road> roads = cityGraph.getRoads(from);

            for (Road road : roads) {

                // avoid duplicate lines
                if (road.getFrom() < road.getTo()) {

                    Intersection b = cityGraph.getIntersection(road.getTo());

                    Line line = new Line(
                            a.getX(), a.getY(),
                            b.getX(), b.getY()
                    );

                    line.setStroke(Color.GRAY);
                    line.setStrokeWidth(2);

                    root.getChildren().add(line);
                }
            }
        }
    }

    private void drawIntersections() {

        for (Intersection intersection : cityGraph.getAllIntersections()) {

            Circle circle = new Circle(
                    intersection.getX(),
                    intersection.getY(),
                    6
            );

            circle.setFill(Color.DODGERBLUE);

            circle.setOnMouseClicked(e ->
                    controller.onIntersectionClicked(intersection)
            );

            root.getChildren().add(circle);
        }
    }


}
