package patterns.structural.decorator.staticdecorator;

import java.util.function.Supplier;

interface Shape {
    String info();
}

class Circle implements Shape{
    private float radius;

    public Circle() {
    }

    public Circle(float radius) {
        this.radius = radius;
    }

    void resize(float factor){
        radius *= factor;
    }

    @Override
    public String info() {
        return String.format("A circle of radius %s", radius);
    }
}

class Square implements Shape{
    private float side;

    public Square() {
    }

    public Square(float side) {
        this.side = side;
    }

    @Override
    public String info() {
        return String.format("Square of side %s", side);
    }
}

//THE DECORATORS
class ColoredShape<T extends Shape> implements Shape{
    private Shape shape;
    private String color;

    public ColoredShape(Supplier<? extends T> constructor, String color){
        shape = constructor.get();
        this.color = color;
    }

    @Override
    public String info() {
        return String.format("%s has color %s", shape.info(), color);
    }
}

class TransparentShape<T extends Shape> implements Shape{
    private Shape shape;
    private int transparent ;

    public TransparentShape(Supplier<? extends T> constructor, int transparent){
        shape = constructor.get();
        this.transparent = transparent;
    }

    @Override
    public String info() {
        return String.format("%s has transparency %s", shape.info(), transparent);
    }
}
class Demo1{
    public static void main(String[] args) {
        ColoredShape<Square> blueSquare = new ColoredShape<>(() -> new Square(20), "blue");

        System.out.println(blueSquare.info());

        TransparentShape<ColoredShape<Circle>> myCircle = new TransparentShape<>(() ->
                new ColoredShape<>(() ->
                        new Circle(5), "green"), 60);
        System.out.println(myCircle.info());
        //Still cant do
        //myCircle.resize()
    }
}
