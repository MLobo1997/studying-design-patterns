package patterns.structural.bridge;

// A Bridge is a mechanism that decouples an interface from an implementation.
// It is useful to avoid having to create an explosively big number of classes, e.g.,
// in situations where the code you are writing depends on the OS and you need to create implementations for each


//Shape -> Circle, Square
//Rendering -> Vectorized, rasterized
//VectorCircle, VectorSquare, RasterCircle......
//If you were to create every combination of shape and rendering methods the number of classes would explode in cartesian multiplication
interface Renderer{
    void renderCircle(float radius);
}

class VectorRenderer implements Renderer
{
    @Override
    public void renderCircle(float radius) {
        System.out.printf("Drawing a circle of format %s%n", radius);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.printf("Drawing pixels of a circle with radius %s%n", radius);
    }
}

abstract class Shape{
    protected Renderer renderer;

    public Shape(Renderer renderer){
        this.renderer = renderer;
    }

    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle extends Shape {
    public float radius;

    public Circle(Renderer renderer) {
        super(renderer);
    }

    public Circle(Renderer renderer, float radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}


class Demo{
    public static void main(String[] args) {

        RasterRenderer rasterRenderer = new RasterRenderer();
        VectorRenderer vectorRenderer = new VectorRenderer();
        Circle circle = new Circle(rasterRenderer, 5);
        circle.draw();
        circle.resize(2);
        circle.draw();
    }
}