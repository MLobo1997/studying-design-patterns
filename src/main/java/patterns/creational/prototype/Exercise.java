package patterns.creational.prototype;

class Point
{
    public int x, y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Point(Point other){
        this(other.x, other.y);
    }
}

class Line
{
    public Point start, end;

    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    public Line(Line other){
        this(new Point(other.start), new Point(other.end));
    }

    public Line deepCopy()
    {
        return new Line(this);
    }
}


