package patterns.structural.adapter;

import java.util.*;
import java.util.function.Consumer;

//This adapter converts vectors to iterables of points with a cache
class Point {
    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line{
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return start.equals(line.start) && end.equals(line.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}

class VectorObject extends ArrayList<Line>{
}

class VectorRectangle extends VectorObject{
    public VectorRectangle(int x, int y, int width, int height)
    {
        add(new Line(new Point(x,y), new Point(x+width, y) ));
        add(new Line(new Point(x+width,y), new Point(x+width, y+height) ));
        add(new Line(new Point(x,y), new Point(x, y+height) ));
        add(new Line(new Point(x,y+height), new Point(x+width, y+height) ));
    }
}

//Solution:
class LineToPointAdapter implements Iterable<Point>{
    private static int count = 0;
    private static Map<Integer, List<Point>> cache = new HashMap<>();
    private final int hash;

    public LineToPointAdapter(Line line){
        hash = line.hashCode();
        if (cache.get(hash) != null)
            return;
        System.out.println(
                String.format("%d: Generating points for line [%d,%d]-[%d,%d] (no caching)",
                        ++count, line.start.x, line.start.y, line.end.x, line.end.y));

        ArrayList<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0)
        {
            for (int y = top; y <= bottom; ++y)
            {
                points.add(new Point(left, y));
            }
        }
        else if (dy == 0)
        {
            for (int x = left; x <= right; ++x)
            {
                points.add(new Point(x, top));
            }
        }

        cache.put(hash, points);
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        cache.get(hash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return cache.get(hash).spliterator();
    }

    @Override
    public Iterator<Point> iterator() {
        return cache.get(hash).iterator();
    }
}

class Demo{
    private final static List<VectorObject> vectorObjects = new ArrayList(
                    Arrays.asList(
                            new VectorRectangle(1,1,10,10),
                            new VectorRectangle(3,3,6,6)
                    )
    );

    // Imagine this is the only function provided by an API to draw stuff.
    // It only processes points, but your whole work is built on vectors. you build then an adapter!!!
    public static void drawPoint(Point p){
        System.out.println(".");
    }

    private static void draw(){
        for (VectorObject vo: vectorObjects){
            for (Line line : vo){
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                adapter.forEach(Demo::drawPoint);
            }
        }
    }

    //With the first adapter solution, since you are creating only temporary objects, if you do
    // draw twice it will take twice the effort. How do we improve this?
    //That is why we implemented the cache.
    public static void main(String[] args) {
        draw();
        draw();
    }
}