package patterns.behavioral.visitor.double_dispatch;

interface ExpressionVisitor{
    void visit(DoubleExpression e);
    void visit(AdditionExpression e);
}

abstract class Expression{
    public abstract void print(StringBuilder sb);

    public abstract void accept(ExpressionVisitor visitor);
}

class ExpressionPrinter implements ExpressionVisitor{
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
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void print(StringBuilder sb) {
        sb.append(value);
    }
}

class AdditionExpression extends Expression {
    public Expression left, right;

    public AdditionExpression(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void print(StringBuilder sb) {
        sb.append("(");
        left.print(sb);
        sb.append("+");
        right.print(sb);
        sb.append(")");
    }
}

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

        ExpressionCalculator expressionCalculator = new ExpressionCalculator();
        expressionCalculator.visit(additionExpression);
        System.out.println(expressionCalculator.result);
    }
}
