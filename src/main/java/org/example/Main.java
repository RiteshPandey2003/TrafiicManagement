package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.Controller.CityController;
import org.example.View.CityMapView;
import org.example.model.CityGraph;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);   // ✅ REQUIRED
    }

    @Override
    public void start(Stage stage) {

        // MODEL
        CityGraph graph = new CityGraph();

        graph.addIntersection(1, 100, 100);   // North-West
        graph.addIntersection(2, 300, 100);   // North
        graph.addIntersection(3, 500, 100);   // North-East

        graph.addIntersection(4, 100, 300);   // West
        graph.addIntersection(5, 300, 300);   // Center
        graph.addIntersection(6, 500, 300);   // East

        graph.addIntersection(7, 100, 500);   // South-West
        graph.addIntersection(8, 300, 500);   // South
        graph.addIntersection(9, 500, 500);   // South-East

        // Horizontal roads (top)
        graph.addRoad(1, 2, 10);
        graph.addRoad(2, 3, 10);

       // Horizontal roads (middle)
        graph.addRoad(4, 5, 8);
        graph.addRoad(5, 6, 8);

        // Horizontal roads (bottom)
        graph.addRoad(7, 8, 12);
        graph.addRoad(8, 9, 12);

        // Vertical roads (left)
        graph.addRoad(1, 4, 15);
        graph.addRoad(4, 7, 15);

        // Vertical roads (center)
        graph.addRoad(2, 5, 10);
        graph.addRoad(5, 8, 10);

        // Vertical roads (right)
        graph.addRoad(3, 6, 15);
        graph.addRoad(6, 9, 15);

        // Diagonal shortcuts
        graph.addRoad(1, 5, 20);
        graph.addRoad(5, 9, 20);
        graph.addRoad(3, 5, 20);
        graph.addRoad(5, 7, 20);


        // CONTROLLER
        CityController controller = new CityController(graph);

        // VIEW
        CityMapView view = new CityMapView(graph, controller);
        controller.setView(view);

        // SCENE
        Scene scene = new Scene(view.getRoot(), 800, 600);

        stage.setTitle("Smart City Simulation");
        stage.setScene(scene);
        stage.show();
    }
}
