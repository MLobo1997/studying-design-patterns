package patterns.behavioral.visitor.acyclic;

interface Visitor{}

interface ExpressionVisitor extends Visitor{
   void visit(Expression obj);
}

interface DoubleExpressionVisitor extends Visitor{
    void visit(DoubleExpression obj);
}

interface AdditionExpressionVisitor extends Visitor{
    void visit(AdditionExpression obj);
}


abstract class Expression{
    public void accept(Visitor visitor){
        if (visitor instanceof ExpressionVisitor){
            ExpressionVisitor visitor1 = (ExpressionVisitor) visitor;
            visitor1.visit(this);
        }
    }
}

class ExpressionPrinter implements DoubleExpressionVisitor, AdditionExpressionVisitor{
    private StringBuilder sb = new StringBuilder();

    @Override
    public void visit(DoubleExpression e) {
        sb.append(e.value);
    }

    //It is called double dispatch because of this. We first use accept which forces going to visit of the respective class
    @Override
    public void visit(AdditionExpression e) {
        sb.append("(");
        e.left.accept(this);
        sb.append("+");
        e.right.accept(this);
        sb.append(")");
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value){
        this.value = value;
    }

    @Override
    public void accept(Visitor visitor){
        if (visitor instanceof DoubleExpressionVisitor){
            DoubleExpressionVisitor visitor1 = (DoubleExpressionVisitor) visitor;
            visitor1.visit(this);
        }
    }
}

class AdditionExpression extends Expression {
    public Expression left, right;

    public AdditionExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(Visitor visitor){
        if (visitor instanceof AdditionExpressionVisitor){
            AdditionExpressionVisitor visitor1 = (AdditionExpressionVisitor) visitor;
            visitor1.visit(this);
        }
    }

}

/*
class ExpressionCalculator implements ExpressionVisitor{
    public double result;

    @Override
    public void visit(DoubleExpression e) {
        result = e.value;
    }

    @Override
    public void visit(AdditionExpression e) {
        e.left.accept(this);
        double r1 = this.result;
        e.right.accept(this);
        double r2 = this.result;
        result = r1 + r2;
    }
}
*/

class Demo{
    public static void main(String[] args) {
        AdditionExpression additionExpression = new AdditionExpression(
                new DoubleExpression(1),
                new AdditionExpression(
                        new DoubleExpression(2),
                        new DoubleExpression(3)
                )
        );
        ExpressionPrinter expressionPrinter = new ExpressionPrinter();
        expressionPrinter.visit(additionExpression);
        System.out.println(expressionPrinter);

        //ExpressionCalculator expressionCalculator = new ExpressionCalculator();
        //expressionCalculator.visit(additionExpression);
        //System.out.println(expressionCalculator.result);
    }
}
