package patterns.structural.decorator.dynamic;

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
class ColoredShape implements Shape{
    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return String.format("%s has color %s", shape.info(), color);
    }
}

class TransparentShape implements Shape{
    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return String.format("%s has %s%% of transparency", shape.info(), transparency);
    }
}

class Demo1{
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.info());

        ColoredShape blueSquare = new ColoredShape(new Square(3), "blue");
        System.out.println(blueSquare.info());

        TransparentShape greenTransparentCircle = new TransparentShape(
                new ColoredShape(
                        new Circle(5), "green"),
                50
        );
        System.out.println(greenTransparentCircle.info());
        //The major problem is that you cannot do this:
        //greenTransparentCircle.resize(2);
    }
}