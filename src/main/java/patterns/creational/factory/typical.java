package patterns.creational.factory;

enum CoordinateSystem{
    CARTESIAN,
    POLAR
}

class Point {
    private double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //This would not be allowed cuz its the same signature
    //public Point(double rho, double theta){
        //x = rho * Math.cos(theta);
        //x = rho * Math.sin(theta);
    //}

    //This is the horrific possible solution. The documentation will be complicated.
    //private Point(double a, double b, CoordinateSystem cs){
        //switch (cs){
            //case CARTESIAN:
                //this.x = x;
                //this.y = y;
                //break;
            //case POLAR:
                //x = a * Math.cos(b);
                //x = a * Math.sin(b);
        //}
    //}

    //These are factory methods!! It is better to separate into classes.
    //public static Point newCartesianPoint(double x, double y){
        //return new Point(x, y);
    //}

    //public static Point newPolarPoint(double rho, double theta){
        //double x = rho * Math.cos(theta);
        //double y = rho * Math.sin(theta);
        //return new Point(rho, theta);
    //}

    public static class Factory{
        public static Point newCartesianPoint(double x, double y){
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta){
            double x = rho * Math.cos(theta);
            double y = rho * Math.sin(theta);
            return new Point(rho, theta);
        }
    }
}

//If you build it here you have to make the Point constructor not private (which might be okay)
//class PointFactory{
//}

class Demo
{
    public static void main(String[] args) {
       //Point point = Point.newPolarPoint(2, 3);

        Point point = Point.Factory.newCartesianPoint(3, 4);
    }
}