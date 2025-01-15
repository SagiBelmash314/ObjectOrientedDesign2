public class BuyerFactory {
    public static Buyer createBuyer(String name, String password, Address address) {
        return new Buyer(name, password, address);
    }
}
