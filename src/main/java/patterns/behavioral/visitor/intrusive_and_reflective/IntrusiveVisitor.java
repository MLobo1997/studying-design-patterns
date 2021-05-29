package patterns.behavioral.visitor.intrusive_and_reflective;

abstract class Expression{
    public abstract void print(StringBuilder sb);
}

class DoubleExpression extends Expression {
    public double value;

    public DoubleExpression(double value){
        this.value = value;
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
    public void print(StringBuilder sb) {
        sb.append("(");
        left.print(sb);
        sb.append("+");
        right.print(sb);
        sb.append(")");
    }
}

class ExpressionPrinter
{
    //this is a reflective visitor
    //There is no verification that the case is implemented... this sucks
    public static void print(Expression e, StringBuilder sb){
        if (e.getClass() == DoubleExpression.class){ //This getClass is very slow!!
            sb.append(((DoubleExpression) e).value);
        } else if(e.getClass() == AdditionExpression.class){
            AdditionExpression ae = (AdditionExpression) e;
            sb.append("(");
            print(ae.left, sb);
            sb.append("+");
            print(ae.right, sb);
            sb.append(")");

        }
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
        StringBuilder stringBuilder = new StringBuilder();
        //additionExpression.print(stringBuilder);
        ExpressionPrinter.print(additionExpression, stringBuilder);
        System.out.println(stringBuilder);
    }
}
