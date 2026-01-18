package org.example.model;


//intersection is node from intersection there are many roads that can connect one node to another node.


public class Intersection {
    public int id;
    double x; //for java FX
    double y; //for javaFX
    boolean isPolluted;
    boolean isCrowded;

    public Intersection(int id, double x, double y) {
        this.id = id;
        this.isPolluted = false;
        this.isCrowded = false;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public boolean isPolluted() {
        return isPolluted;
    }

    public void setPolluted(boolean polluted) {
        this.isPolluted = polluted;
    }

    public boolean isCrowded() {
        return isCrowded;
    }

    public void setCrowded(boolean crowded) {
        this.isCrowded = crowded;
    }


    public  double getX() {
        return x;
    }

    public  double getY() {
        return y;
    }
}
