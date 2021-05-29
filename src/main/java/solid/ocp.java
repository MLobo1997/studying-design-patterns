package solid;


import java.util.List;
import java.util.stream.Stream;

// Open closed principle + `Specification` design pattern
// OCP - code that is open for extension but closed for modification.
enum Color
{
    RED, GREEN, BLUE
}

enum Size
{
    SMALL, MEDIUM, LARGE, HUGE
}

class Product {
    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

// This approach is not great cuz you need to create a method for each combination
class ProductFilter
{
    public Stream<Product> filterByColor(List<Product> products, Color color){
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size){
        return products.stream().filter(p -> p.size == size);
    }

    public Stream<Product> filtersBySizeAndColor(List<Product> products, Size size, Color color) {
        return products.stream().filter(p -> p.size == size && p.color == color);
    }
}

interface Specification<T>{
    boolean isSatisfied(T item);
}

class AndSpecification<T> implements Specification<T>{
    private final Specification<T> first;
    private final Specification<T> second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

interface Filter<T>{
    Stream<T> filter(List<T> items, Specification<T> spec);
}

class ColorSpecification implements Specification<Product>{
    private final Color color;

    public ColorSpecification(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return (item.color == this.color);
    }
}

class SizeSpecification implements Specification<Product>{
    private final Size size;

    public SizeSpecification(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return (item.size == this.size);
    }
}

class BetterFilter implements Filter<Product>{
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(spec::isSatisfied);
    }
}
class Demo1{
    public static void main(String[] args){
        Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
        Product house = new Product("House", Color.RED, Size.LARGE);

        List<Product> products = List.of(apple, tree, house);
        ProductFilter pf = new ProductFilter();
        System.out.println("Green products(old):");
        pf.filterByColor(products, Color.GREEN).forEach(p -> System.out.println(" - " + p.name + " is green"));

        BetterFilter bf = new BetterFilter();
        System.out.println("Green products(new):");
        bf.filter(products, new ColorSpecification(Color.GREEN)).forEach(p -> System.out.println(" - " + p.name + " is green"));

        Specification<Product> spec = new AndSpecification<Product>(new ColorSpecification(Color.GREEN), new SizeSpecification(Size.LARGE));
        System.out.println("Green and large products(new):");
        bf.filter(products, spec).forEach(p -> System.out.println(" - " + p.name + " is green and large"));

    }
}
