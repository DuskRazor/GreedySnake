package com.kali;

public class Snake {
    private int x;
    private int y;
    private Direction dir;
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final int SPEED = 2;

    public Snake(int x, int y,Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getSPEED() {
        return SPEED;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
