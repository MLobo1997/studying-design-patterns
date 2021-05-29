package solid;

import javax.print.Doc;
import java.io.PrintStream;

// ISP Interface segregation principle - how to split interfaces to smaller interfaces
class Document {
}

// Not good
interface Machine{
    void print(Document d);
    void fax(Document d);
    void scan(Document d);
}

class MultiFunctionPrinter implements Machine{
    @Override
    public void print(Document d) {
    }

    @Override
    public void fax(Document d) {
    }

    @Override
    public void scan(Document d) {
    }
}

// What if we only want to create a method that only prints?
class OldFashionedPrinter implements Machine{
    @Override
    public void print(Document d) {
    }

    @Override
    public void fax(Document d) {
        // throw new Exception(); NOT GOOD
    }

    @Override
    public void scan(Document d) {
        // LEAVE EMPTY!?!?
    }
}
// YAGNI = You ain't going to need it. Don't waste code on things you don't need. K.I.S.S.

// From here on is all good
interface Printer{
    void print(Document d);
}

interface Scanner{
    void scan(Document d);
}


class JustAPrinter implements Printer{
    @Override
    public void print(Document d) {

    }
}

class Photocopier implements Printer, Scanner{
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

interface MultiFunctionDevice extends Printer, Scanner{}

// A more fancy approach that uses the `Decorator` design pattern is:
class MultiFunctionMachine implements MultiFunctionDevice{
    private Printer printer;
    private Scanner scanner;

    public MultiFunctionMachine(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document d) {
        this.printer.print(d);
    }

    @Override
    public void scan(Document d) {
        this.scanner.scan(d);
    }
}

// An interface should have the least amount of code possible