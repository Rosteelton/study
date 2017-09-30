package hh;

import java.util.ArrayList;
import java.util.Collections;

public class TropicalIsland {


    public Integer[][] island;
    public int height;
    public int width;
    public Point[] pointsIsland;
    public int amountOfWater;


    public TropicalIsland(Integer[][] island) {
        this.island = island;
        this.height = island.length;
        this.width = island[0].length;
        this.pointsIsland = this.toPoints();
    }


    public static int getWaterVolume(Integer[][] island) {
        TropicalIsland isl = new TropicalIsland(island);

//        for (int i = 0; i < isl.pointsIsland.length; i++) {
//            Point element = isl.pointsIsland[i];
//            if (!element.isBorder) {
//                int neighborsMinValue = Collections.min(isl.getNeighbors(element)).value;
//                if (element.value < neighborsMinValue) {
//                    isl.pointsIsland[i] =
//                            new Point(
//                                    isl.pointsIsland[i].x,
//                                    isl.pointsIsland[i].y,
//                                    neighborsMinValue,
//                                    isl.pointsIsland[i].isBorder,
//                                    isl.pointsIsland[i].isProcessed);
//                } else if (???){
//                    ???
//                } else{
//                    ???
//                }
//
//
//            }
//        }
        return 0;
    }

    public ArrayList<Point> getNeighbors(Point point) {
        ArrayList<Point> neighbors = new ArrayList<>(4);
        if (point.y - 1 >= 0) {
            neighbors.add(this.pointsIsland[(((point.y - 1) * width) + point.x)]);
        }
        if (point.y + 1 < height) {
            neighbors.add(this.pointsIsland[(((point.y + 1) * width) + point.x)]);
        }
        if (point.x + 1 < width) {
            neighbors.add(this.pointsIsland[((point.y * width) + point.x + 1)]);
        }

        if (point.x - 1 >= 0) {
            neighbors.add(this.pointsIsland[((point.y * width) + point.x - 1)]);
        }

        return neighbors;
    }

    public Point[] toPoints() {
        Point[] points = new Point[width * height];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1) {
                    points[j * width + i] = (new Point(j, i, island[i][j], true, false));
                } else {
                    points[j * width + i] = (new Point(j, i, island[i][j], false, false));
                }
            }
        }
        return points;
    }

    public void printIsland() {
        System.out.println("height: " + height + "\n" +
                "width: " + width + "\n");
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.printf("%4d ", this.island[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        final Integer[][] matrix = {
                {5, 3, 4, 5},
                {6, 2, 1, 4},
                {3, 1, 1, 4},
                {8, 5, 4, 3}
        };

        TropicalIsland island1 = new TropicalIsland(matrix);

        //test neighbors
        Point point = island1.pointsIsland[9];
        System.out.println(point);
        System.out.println(island1.getNeighbors(point));

        System.out.println();

        Point point2 = island1.pointsIsland[0];
        System.out.println(point2);
        System.out.println(island1.getNeighbors(point2));

    }

    public static class Point implements Comparable<Point> {
        public int x;
        public int y;
        public int value;
        public boolean isBorder;
        public boolean isProcessed;

        public Point(int x, int y, int value, boolean isBorder, boolean isProcessed) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.isBorder = isBorder;
            this.isProcessed = isProcessed;
        }

        @Override
        public int compareTo(Point o) {
            return this.value - o.value;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", value=" + value +
                    ", isBorder=" + isBorder +
                    ", isProcessed=" + isProcessed +
                    '}';
        }
    }

}
