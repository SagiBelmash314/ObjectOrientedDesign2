public class SellerFactory {
    public static Seller createSeller(String name, String password) {
        return new Seller(name,password);
    }

}
