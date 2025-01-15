public class ProductFactory {
    public static Product createProduct(String name, float price, Category category, float packagePric) {
        return new Product(name, price, category, packagePric);
    }
    public static Product createProduct(Product other) {
        return new Product(other);
    }
}
