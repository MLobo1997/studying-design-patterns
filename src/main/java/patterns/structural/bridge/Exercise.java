package patterns.structural.bridge;

interface Renderer2{
    String renderTriangle(String name);
    String renderSquare(String name);
}

class VectorRenderer2 implements Renderer2{
    @Override
    public String renderTriangle(String name) {
        return String.format("Drawing %s as lines", name);
    }

    @Override
    public String renderSquare(String name) {
        return String.format("Drawing %s as lines", name);
    }
}

class RasterRenderer2 implements Renderer2{
    @Override
    public String renderTriangle(String name) {
        return String.format("Drawing %s as pixels", name);
    }

    @Override
    public String renderSquare(String name) {
        return String.format("Drawing %s as pixels", name);
    }
}

abstract class Shape2
{
    protected Renderer2 renderer;

    public Shape2(Renderer2 renderer) {
        this.renderer = renderer;
    }

    public abstract String getName();

    @Override
    public abstract String toString();
}

class Triangle extends Shape2
{

    public Triangle(Renderer2 renderer) {
        super(renderer);
    }

    @Override
    public String getName()
    {
        return "Triangle";
    }

    @Override
    public String toString() {
        return renderer.renderTriangle(getName());
    }
}

class Square extends Shape2
{
    public Square(Renderer2 renderer) {
        super(renderer);
    }

    @Override
    public String getName()
    {
        return "Square";
    }

    @Override
    public String toString() {
        return renderer.renderSquare(getName());
    }
}

// imagine VectorTriangle and RasterTriangle are here too

