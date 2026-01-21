package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Controller.CityController;
import org.example.View.CityMapView;
import org.example.model.CityGraph;

public class Main extends Application {

    private static final int GRID_SIZE = 12;     // 🔥 increase for BIGGER city
    private static final int GAP = 60;            // distance between intersections
    private static final int START_X = 80;
    private static final int START_Y = 80;

    @Override
    public void start(Stage stage) {

        CityGraph graph = new CityGraph();

        int id = 1;

        // 🔹 Create intersections
        int[][] ids = new int[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                double x = START_X + col * GAP;
                double y = START_Y + row * GAP;

                graph.addIntersection(id, x, y);
                ids[row][col] = id;
                id++;
            }
        }

        // 🔹 Create roads
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                int current = ids[row][col];

                // Right road
                if (col < GRID_SIZE - 1) {
                    graph.addRoad(current, ids[row][col + 1], 10);
                }

                // Bottom road
                if (row < GRID_SIZE - 1) {
                    graph.addRoad(current, ids[row + 1][col], 10);
                }
            }
        }

        // CONTROLLER
        CityController controller = new CityController(graph);

        // VIEW
        CityMapView view = new CityMapView(graph, controller);
        controller.setView(view);

        Scene scene = new Scene(view.getRoot(), 900, 900);

        stage.setTitle("Smart City – Large Graph");
        stage.setScene(scene);
        stage.show();

        controller.startTrafficSimulation();
    }

    public static void main(String[] args) {
        launch();
    }
}
