public class AddressFactory {
    public static Address createAddress(String street, int building, String city, String country) {
        return new Address(street,building,city,country);
    }
}
