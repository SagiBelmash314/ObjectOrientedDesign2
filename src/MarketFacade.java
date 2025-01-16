import java.util.*;

public class MarketFacade {
    private BuyerManager buyerManager;
    private SellerManager sellerManager;
    private static MarketFacade instance;

    private MarketFacade(){
        this.buyerManager = new BuyerManager();
        this.sellerManager = new SellerManager();
    }
    public static MarketFacade getInstance() {
       if(instance == null)
           instance = new MarketFacade();
       return instance;
    }

    public void generateExamples() {
        buyerManager.addBuyer(BuyerFactory.createBuyer("Momo", "1", AddressFactory.createAddress("1", 1, "1", "1")));
        buyerManager.addBuyer(BuyerFactory.createBuyer("Lolo", "1", AddressFactory.createAddress("1", 1, "1", "1")));
        buyerManager.addBuyer(BuyerFactory.createBuyer("loLO", "1", AddressFactory.createAddress("1", 1, "1", "1")));
        sellerManager.addSeller(SellerFactory.createSeller("Bobo", "1"));
        sellerManager.addSeller(SellerFactory.createSeller("dODO", "1"));
        sellerManager.addSeller(SellerFactory.createSeller("Dodo", "1"));
        sellerManager.getSeller("Bobo").addProduct(ProductFactory.createProduct("YoYo", 10, Category.CHILD, 5));
        buyerManager.getBuyer("Momo").addItemToCart(sellerManager.getSeller("Bobo").getProductByName("YoYo"));
    }

    public void addSeller() {
        Seller seller = SellerFactory.createSeller();
        if (seller == null) return;
        sellerManager.addSeller(seller);
        System.out.println(seller.getName() + " got added to the system as a seller.");
    }

    public void addBuyer() {
        String name;
        do {
            name = UserInput.getBuyerNameFromUser();
            if (name.isEmpty()) return;
            if (!buyerManager.buyerExists(name)) {
                break;
            }
        } while (true);
        String password = UserInput.getPasswordFromUser();
        buyerManager.addBuyer(BuyerFactory.createBuyer(name, password, UserInput.getBuyerAddressFromUser()));
        System.out.println(name + " got added to the system as a buyer.");
    }

    public void addProductToSeller() {
        if (!sellerManager.areThereSellers()) {
            System.out.println("There are no sellers yet.");
            return;
        }
        Seller seller = chooseSeller();
        if (seller == null) return;
        String productName = UserInput.getProductNameFromUser();
        while (sellerManager.productExists(seller, productName)) {
            System.out.println("This product is already in the sellers list. Please choose another:");
            productName = UserInput.getProductNameFromUser();
        }
        seller.addProduct(ProductFactory.createProduct(productName));
    }

    public void addProductBuyer() {
        if (!buyerManager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        if (!sellerManager.areThereSellers()) {
            System.out.println("There are no sellers yet, so there are no products to choose from.");
            return;
        }
        Buyer buyer = chooseBuyer();
        if (buyer == null) return;
        Seller seller = chooseSeller();
        if (seller == null) return;
        seller.printProducts();
        String productName;
        do {
            productName = UserInput.getProductNameFromUser();
            if (productName.equals(".")) {
                return;
            }
            if (chooseProduct(seller, productName)) {
                break;
            }
            System.out.println("You chose a product that this seller does not offer.\n" +
                    "Please choose another or enter \".\" to return to the main menu.");
        } while (true);
        buyer.addItemToCart(seller.getProductByName(productName));
        System.out.println("The item was added to the buyer's cart.");
    }

    public void orderPayment() {
        if (!buyerManager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        Buyer buyer = chooseBuyer();
        if (buyer == null) return;
        buyer.purchase();
    }

    public void printBuyers() {
        buyerManager.printBuyers();
    }

    public void printSellers() {
        sellerManager.printSellers();
    }

    private Seller chooseSeller() {
        String name;
        do {
            name = UserInput.getSellerNameFromUser();
            if (name.isEmpty()) return null;
            if (sellerManager.sellerExists(name)) {
                break;
            } else {
                System.out.println("This seller doesn't exist, please choose another: ");
            }
        } while (true);
        return sellerManager.getSeller(name);
    }

    private Buyer chooseBuyer() {
        String name;
        do {
            name = UserInput.getBuyerNameFromUser();
            if (name.isEmpty()) return null;
            if (buyerManager.buyerExists(name)) {
                break;
            } else {
                System.out.println("This buyer doesn't exist, please choose another: ");
            }
        } while (true);
        return buyerManager.getBuyer(name);
    }

    public boolean chooseProduct(Seller seller, String productName) {
        for (Product p : seller.getProducts()) {
            if (p.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void printByCategory() {
        Product[] products = sellerManager.getItemsByCategory(UserInput.getCategoryFromUser());
        for (int i = 1; i < products.length + 1; i++) {
            System.out.println(i + ") " + products[i - 1].getName());
        }
    }

    public void createCartFromHistory() {
        if (!buyerManager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        Buyer buyer;
        do {
            buyer = chooseBuyer();
            if (buyer == null) return;
            String answer = UserInput.getUserChoice("This buyer doesn't have previous orders, do you want to choose another buyer? (Y/N)");
            if (answer.equals("N")) {
                return;
            }
        } while (!buyer.hasPrevOrders());
        if (!buyer.getShoppingCart().isEmpty()) {
            String answer = UserInput.getUserChoice("The selected buyer already has a cart, do you wish to replace it with a previous order? (Y/N)");
            if (answer.equals("N")) {
                return;
            }
        }
        int numOfOrders = buyer.printOrderHistory(), orderNum = UserInput.getOrderNumberFromUser(numOfOrders);
        if (orderNum == 0) {
            return;
        }
        buyer.setCart(buyer.getPrevOrder(orderNum - 1));
        System.out.println("Buyer's cart has been updated");
    }

    public void printNames() {
        ArrayList<String> names = buyerManager.getArrayNames();
        System.out.println("\nBuyer Names:\n");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("\n");
    }

    public void printNamesNoDoubles() {
        HashMap<String, Integer> names = buyerManager.getLinkedHashmapNames();
        System.out.println("\nBuyer Names:\n");
        for (Map.Entry<String, Integer> entry : names.entrySet()) {
            System.out.println(entry.getKey() + "......" + entry.getValue());
        }
        System.out.println("\n");
    }

    public void printNameOccurrences(Scanner sc) {
        System.out.print("\nPlease enter the string: ");
        String name = sc.next().toLowerCase();
        HashMap<String, Integer> names = buyerManager.getLinkedHashmapNames();
        if (names.containsKey(name)) {
            System.out.println("The number of times '" + name + "' appears in the original array is " + names.get(name) + ".\n");
        } else {
            System.out.println("'" + name + "' does not appear in the original array.");
        }
    }

    public void printReversedDoubles() {
        HashMap<String, Integer> names = buyerManager.getLinkedHashmapNames(); // The names we saved without duplicates
        ArrayList<String> doublesList = new ArrayList<>();
        ListIterator<String> iterator = doublesList.listIterator();
        for (String key : names.keySet()) {
            iterator.add(key);
            iterator.add(key);
        }
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }

    public void printNamesSortedByLength() {
        TreeSet<Buyer> tree = buyerManager.getBuyerTreeSet();
        for (Buyer buyer : tree) {
            System.out.println(buyer.getName().toUpperCase());
        }
    }

}





