//package org.example.View;
//
//
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//import org.example.Controller.CityController;
//import org.example.model.CityGraph;
//import org.example.model.Intersection;
//import org.example.model.PathResult;
//import org.example.model.Road;
//
//
//import java.awt.*;
//import java.util.List;
//
//public class CityMapView extends Canvas {
//
//    private final Pane root;
//    private final CityGraph cityGraph;
//    private CityController controller;
//
//    public CityMapView(CityGraph cityGraph, CityController controller) {
//        this.cityGraph = cityGraph;
//        this.controller = controller;
//        this.root = new Pane();
//        drawCity();
//    }
//
//    public Pane getRoot() {
//        return root;
//    }
//
//    public void setController(CityController controller) {
//        this.controller = controller;
//    }
//
//    public void drawCity() {
//        root.getChildren().clear();
//        drawRoads();
//        drawIntersections();
//
//    }
//
//    private void drawRoads() {
//
//        for (int from : cityGraph.getAllIntersectionsIds()) {
//
//
//            Intersection a = cityGraph.getIntersection(from);
//            List<Road> roads = cityGraph.getRoads(from);
//
//
//
//            for (Road road : roads) {
//
//                // avoid duplicate lines
//                if (road.getFrom() < road.getTo()) {
//
//                    Intersection b = cityGraph.getIntersection(road.getTo());
//
//                    Line line = new Line(
//                            a.getX(), a.getY(),
//                            b.getX(), b.getY()
//                    );
//
//                    line.setStroke(Color.GRAY);
//                    line.setStrokeWidth(2);
//
//                    root.getChildren().add(line);
//                }
//            }
//        }
//    }
//
//    private void drawPath() {
//        PathResult path = controller.getCurrentPath();
//        if (path == null) return;
//
//        List<Integer> nodes = path.getPath();
//
//        for (int i = 0; i < nodes.size() - 1; i++) {
//            Intersection a = cityGraph.getIntersection(nodes.get(i));
//            Intersection b = cityGraph.getIntersection(nodes.get(i + 1));
//
//            Line line = new Line(a.getX(), a.getY(), b.getX(), b.getY());
//            line.setStroke(Color.GOLD);
//            line.setStrokeWidth(4);
//            root.getChildren().add(line);
//        }
//    }
//
//
//
//    private void drawIntersections() {
//
//        for (Intersection intersection : cityGraph.getAllIntersections()) {
//
//            Circle circle = new Circle(
//                    intersection.getX(),
//                    intersection.getY(),
//                    6
//            );
//
//
//            if (controller.getSource() != null && intersection.id == controller.getSource()) {
//                circle.setFill(Color.YELLOW);
//
//            } else if (controller.getDestination() != null && intersection.id == controller.getDestination()) {
//                circle.setFill(Color.ORANGE);
//
//            } else if (intersection.isPolluted() && intersection.isCrowded()) {
//                circle.setFill(Color.PURPLE);
//
//            } else if (intersection.isPolluted()) {
//                circle.setFill(Color.GREEN);
//
//            } else if (intersection.isCrowded()) {
//                circle.setFill(Color.RED);
//
//            } else {
//                circle.setFill(Color.DODGERBLUE);
//            }
//
//            circle.setOnMouseClicked(e ->
//                    controller.onIntersectionClicked(intersection)
//            );
//
//            root.getChildren().add(circle);
//        }
//    }
//
//}


package org.example.View;

import javafx.animation.KeyFrame;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.Controller.CityController;
import org.example.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class CityMapView {

    private final Pane root = new Pane();
    private final CityGraph cityGraph;
    private final CityController controller;
    private List<Integer> animatedPath = new ArrayList<>();
    private List<Integer> path = new ArrayList<>();


    public CityMapView(CityGraph cityGraph, CityController controller) {
        this.cityGraph = cityGraph;
        this.controller = controller;
        drawCity();
    }

    public Pane getRoot() {
        return root;
    }

    public void drawCity() {
        root.getChildren().clear();
        drawRoads();
        drawIntersections();
    }

//    private void drawRoads() {
//
//        List<Integer> path =
//                controller.getCurrentPath() == null
//                        ? null
//                        : controller.getCurrentPath().getPath();
//
//        for (int from : cityGraph.getAllIntersectionsIds()) {
//            Intersection a = cityGraph.getIntersection(from);
//
//            for (Road road : cityGraph.getRoads(from)) {
//
//                if (road.getFrom() < road.getTo()) {
//
//                    Intersection b = cityGraph.getIntersection(road.getTo());
//                    Line line = new Line(a.getX(), a.getY(), b.getX(), b.getY());
//
//                    if (isRoadInPath(from, road.getTo(), path)) {
//                        line.setStroke(Color.RED);
//                        line.setStrokeWidth(5);   // 🔥 BOLD
//                    } else {
//                        line.setStroke(Color.GRAY);
//                        line.setStrokeWidth(2);
//                    }
//
//                    root.getChildren().add(line);
//                }
//            }
//        }
//    }

    public void setPath(List<Integer> path) {
        this.path = path;
        drawCity();
    }

    private void drawRoads() {

        for (int from : cityGraph.getAllIntersectionsIds()) {

            Intersection a = cityGraph.getIntersection(from);

            for (Road road : cityGraph.getRoads(from)) {

                if (road.getFrom() < road.getTo()) {

                    int to = road.getTo();
                    Intersection b = cityGraph.getIntersection(to);

                    Line line = new Line(
                            a.getX(), a.getY(),
                            b.getX(), b.getY()
                    );

                    if (isPathEdge(from, to)) {
                        line.setStroke(Color.RED);
                        line.setStrokeWidth(5); // 🔥 BOLD
                    } else {
                        line.setStroke(Color.GRAY);
                        line.setStrokeWidth(2);
                    }

                    root.getChildren().add(line);
                }
            }
        }
    }

    private boolean isPathEdge(int u, int v) {

        if (path == null || path.size() < 2) return false;

        for (int i = 0; i < path.size() - 1; i++) {

            int a = path.get(i);
            int b = path.get(i + 1);

            if ((a == u && b == v) || (a == v && b == u)) {
                return true;
            }
        }
        return false;
    }


    public void animatePath(List<Integer> path) {

        animatedPath.clear();
        drawCity(); // clear old path

        Timeline timeline = new Timeline();

        for (int i = 0; i < path.size() - 1; i++) {

            int from = path.get(i);
            int to = path.get(i + 1);

            int index = i;

            KeyFrame frame = new KeyFrame(
                    javafx.util.Duration.millis(300 * (i + 1)),
                    e -> {
                        animatedPath.add(from);
                        animatedPath.add(to);
                        drawCity();
                    }
            );

            timeline.getKeyFrames().add(frame);
        }

        timeline.play();
    }




    private boolean isRoadInPath(int a, int b, List<Integer> path) {
        if (path == null) return false;

        for (int i = 0; i < path.size() - 1; i++) {
            int x = path.get(i);
            int y = path.get(i + 1);

            if ((x == a && y == b) || (x == b && y == a)) {
                return true;
            }
        }
        return false;
    }

    private void drawIntersections() {

        for (Intersection intersection : cityGraph.getAllIntersections()) {

            Circle circle = new Circle(
                    intersection.getX(),
                    intersection.getY(),
                    6
            );

            if (controller.getSource() != null &&
                    intersection.getId() == controller.getSource()) {

                circle.setFill(Color.YELLOW);

            } else if (controller.getDestination() != null &&
                    intersection.getId() == controller.getDestination()) {

                circle.setFill(Color.ORANGE);

            } else if (intersection.isPolluted() && intersection.isCrowded()) {

                circle.setFill(Color.PURPLE);

            } else if (intersection.isPolluted()) {

                circle.setFill(Color.GREEN);

            } else if (intersection.isCrowded()) {

                circle.setFill(Color.RED);

            } else {
                circle.setFill(Color.DODGERBLUE);
            }

            circle.setOnMouseClicked(e ->
                    controller.selectIntersection(intersection.getId())
            );

            root.getChildren().add(circle);
        }
    }

}
