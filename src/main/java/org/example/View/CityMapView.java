

package org.example.View;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.example.Controller.CityController;
import org.example.model.*;



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
