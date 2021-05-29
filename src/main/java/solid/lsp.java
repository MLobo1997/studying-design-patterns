package solid;

// LSP - Liskop Substitution  Principle - you should be able to substitute a base class for a sub class without breaking
class Rectangle {
    protected int width, height;

    public Rectangle() {
    }

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getArea(){
        return this.width * this.height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    // good alternative
    public boolean isSquare(){
        return width == height;
    }
}

//Also good alternative
class RectangleFactory{
    public  static Rectangle newRectangle(int width, int height){
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side){
        return newRectangle(side, side);
    }
}

// Not good
class Square extends Rectangle{
    public Square() {
    }

    public Square(int size){
        this.height = this.width = size;
    }

    @Override //this violates LSP. Makes sense for rectangle but not square
    public void setWidth(int width) {
        super.setWidth(width);
        super.setHeight(width);
    }

    @Override
    public void setHeight(int height) {
        super.setWidth(height);
        super.setHeight(height);
    }
}

class Demo02{
    static void useIt(Rectangle r){
        int width = r.getWidth();
        r.setHeight(10);
        System.out.println("Expected area of " + (width * 10) + ", got " + r.getArea());
    }

    public static void main(String[] args){
        Rectangle rc = new Rectangle(2,3);
        useIt(rc);

        Square sq = new Square();
        sq.setWidth(5);
        useIt(sq);

    }
}
