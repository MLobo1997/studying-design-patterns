package patterns.structural.bridge;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

// A Bridge is a mechanism that decouples an interface from an implementation.
// It is useful to avoid having to create an explosively big number of classes, e.g.,
// in situations where the code you are writing depends on the OS and you need to create implementations for each


//Shape -> Circle, Square
//Rendering -> Vectorized, rasterized
//VectorCircle, VectorSquare, RasterCircle......
//If you were to create every combination of shape and rendering methods the number of classes would explode in cartesian multiplication
interface Renderer1 {
    void renderCircle(float radius);
}

class VectorRenderer1 implements Renderer1
{
    @Override
    public void renderCircle(float radius) {
        System.out.printf("Drawing a circle of format %s%n", radius);
    }
}

class RasterRenderer1 implements Renderer1 {
    @Override
    public void renderCircle(float radius) {
        System.out.printf("Drawing pixels of a circle with radius %s%n", radius);
    }
}

abstract class Shape1 {
    protected Renderer1 renderer;

    public Shape1(Renderer1 renderer){
        this.renderer = renderer;
    }

    public abstract void draw();
    public abstract void resize(float factor);
}

class Circle1 extends Shape1 {
    public float radius;

    //USES DEPENDENCY INJECTION FRAMEWORK
    @Inject
    public Circle1(Renderer1 renderer) {
        super(renderer);
    }

    public Circle1(Renderer1 renderer, float radius) {
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

class ShapeModule extends AbstractModule{
    @Override
    protected void configure() {
        //This is the only point in our application where we have to specify.
        bind(Renderer1.class).to(RasterRenderer1.class);
    }
}


class Demo1{
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ShapeModule());
        Circle1 instance = injector.getInstance(Circle1.class);
        instance.radius = 3;
        instance.draw();
        instance.resize(2);
        instance.draw();
    }
}