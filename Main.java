// Sagi Belmash 324227271
// Yair Vinshststocks 214616781
// Instructor: Pini Shlomi

import java.util.*;

public class Main {

    private static Scanner reader;
    private static Manager manager;


    public static void main(String[] args) {
        run();
        reader.close();
    }

    public static void run() {
        reader = new Scanner(System.in);
        manager = new Manager();


        menu();
    }

    public static void menu() throws InputMismatchException {
        int input = 0;

        do {
            System.out.println();
            System.out.println("""
                    Choose one of the following options:
                    0: Exit
                    1: Add seller
                    2: Add buyer
                    3: Add product to seller
                    4: Add product to buyer
                    5: Order payment for buyer
                    6: Show details of all buyers
                    7: Show details of all sellers
                    8: Show every product of a specific category
                    9: Create new cart for buyer from previous cart
                    99: Print buyers' names by original order
                    100: Print buyers' names by original order with no duplicates with number of occurrences
                    101: Print number of occurrences of a specific name in the buyers array
                    102: Print the buyer's names with doubles in reverse
                    103: Print the buyer's names sorted by name length
                    """);
            boolean acceptableAnswer = false;
            while (!acceptableAnswer) {
                try {
                    input = reader.nextInt();
                    acceptableAnswer = true;
                } catch (InputMismatchException e) {
                    System.out.println("Input must be an integer, please enter an answer with the correct type:");
                    reader.nextLine();
                }
            }
            switch (input) {
                case -1: // Generate automatically buyers, sellers, and product for testing
                    generateExamples();
                    break;
                case 0: // Exit
                    System.out.println("Goodbye!");
                    break;
                case 1: // Add seller
                    addSeller();
                    break;
                case 2: // Add buyer
                    addBuyer();
                    break;
                case 3: // Add product to seller
                    addProductToSeller();
                    break;
                case 4: // Add product to buyer
                    addProductBuyer();
                    break;
                case 5: // Order payment for buyer
                    orderPayment();
                    break;
                case 6: // Show details of all buyers
                    printBuyers();
                    break;
                case 7: // Show details of all sellers
                    printSellers();
                    break;
                case 8: // Print items of the same category
                    printByCategory();
                    break;
                case 9: // Re-create a buyer's previous cart
                    createCartFromHistory();
                    break;
                case 99: // Print buyers' names in the order of the buyers' array
                    printNames();
                    break;
                case 100: // Print buyers' names in the order of the buyers' array without doubles
                    printNamesNoDoubles();
                    break;
                case 101: // Print number of occurrences of a certain name in the buyers' array
                    printNameOccurrences();
                    break;
                case 102: // print the array with double names in reverse
                    printReversedDoubles();
                    break;
                case 103: // Print the buyer's names sorted by name length
                    printNamesSortedByLength();
                    break;
                default:
                    System.out.println("Invalid option, please try again");
            }
        } while (input != 0);
    }

    private static void generateExamples() {
        manager.addBuyer(BuyerFactory.createBuyer("Momo", "1", AddressFactory.createAddress("1", 1, "1", "1")));
        manager.addBuyer(BuyerFactory.createBuyer("Lolo", "1",AddressFactory.createAddress("1", 1, "1", "1")));
        manager.addBuyer(BuyerFactory.createBuyer("loLO", "1", AddressFactory.createAddress("1", 1, "1", "1")));
        manager.addSeller(SellerFactory.createSeller("Bobo", "1"));
        manager.addSeller(SellerFactory.createSeller("dODO", "1"));
        manager.addSeller(SellerFactory.createSeller("Dodo", "1"));
        manager.getSeller("Bobo").addProduct(ProductFactory.createProduct("YoYo", 10, Category.CHILD, 5));
        manager.getBuyer("Momo").addItemToCart(manager.getSeller("Bobo").getProductByName("YoYo"));
    }

    private static void addSeller() {
        System.out.println("Please enter the name of the seller or enter \".\" to return to the main menu: ");
        String name;
        do {
            name = reader.next();
            if (name.equals(".")) {
                return;
            }
            if (!manager.isExisting(name)) {
                break;
            }
            System.out.println("Seller is already in the system.\n" +
                    "Please enter a different seller or enter \".\" to return to the main menu:");
        } while (true);
        System.out.println("Please enter a password: ");
        String password = reader.next();
        manager.addSeller(SellerFactory.createSeller(name, password));
        System.out.println(name + " got added to the system as a seller.");
    }

    private static void addBuyer() {
        System.out.println("Please enter the name of the buyer or enter \".\" to return to the main menu: ");
        String name;
        do {
            name = reader.next();
            if (name.equals(".")) {
                return;
            }
            if (!manager.isExisting(name)) {
                break;
            }
            System.out.println("Buyer is already in the system.\n" +
                    "Please enter a different buyer or enter \".\" to return to the main menu:");
        } while (true);
        System.out.println("Please enter a password: ");
        String password = reader.next();
        System.out.println("Please enter buyer's street name: ");
        String street = reader.next();
        System.out.println("Please enter buyer's building number: ");
        boolean acceptableAnswer = false;
        while (!acceptableAnswer) {
            try {
                int building = reader.nextInt();
                System.out.println("Please enter buyer's city: ");
                acceptableAnswer = true;
                String city = reader.next();
                System.out.println("Please enter buyer's country: ");
                String country = reader.next();
                manager.addBuyer(BuyerFactory.createBuyer(name, password, AddressFactory.createAddress(street, building, city, country)));
                System.out.println(name + " got added to the system as a buyer.");
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    private static void addProductToSeller() {
        if (!manager.areThereSellers()) {
            System.out.println("There are no sellers yet.");
            return;
        }
        Seller seller = chooseSeller();
        System.out.println("Please enter the name of the product: ");
        String productName = reader.next();
        while (manager.productExists(seller, productName)) {
            System.out.println("This product is already in the sellers list. Please choose another:");
            productName = reader.next();
        }
        System.out.println("Please enter the price of the product: ");
        boolean acceptableAnswer = false;
        float price = 0;
        while (!acceptableAnswer) {
            try {
                price = reader.nextFloat();
                acceptableAnswer = true;
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
        Category category = selectCategory();
        System.out.println("Please enter the price of the package (if there is no special package enter 0):");
        acceptableAnswer = false;
        while (!acceptableAnswer) {
            try {
                float packagePrice = reader.nextFloat();
                seller.addProduct(ProductFactory.createProduct(productName, price, category, packagePrice));
//                manager.sort();
                acceptableAnswer = true;
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }


    private static void addProductBuyer() {
        if (!manager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        if (!manager.areThereSellers()) {
            System.out.println("There are no sellers yet, so there are no products to choose from.");
            return;
        }
        Buyer buyer = chooseBuyer();
        Seller seller = chooseSeller();
        seller.printProducts();
        System.out.println("Please enter the name of the desired product:");
        String productName;
        do {
            productName = reader.next();
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

    private static void orderPayment() {
        if (!manager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        Buyer buyer = chooseBuyer();
        buyer.purchase();
    }

    private static void printBuyers() {
        manager.printBuyers();
    }

    private static void printSellers() {
        manager.printSellers();
    }

    private static Seller chooseSeller() {
        System.out.println("Please enter the name of the seller: ");
        String name;
        do {
            name = reader.next();
            if (manager.sellerExists(name)) {
                break;
            } else {
                System.out.println("This seller doesn't exist, please choose another: ");
            }
        } while (true);
        return manager.getSeller(name);
    }

    private static Buyer chooseBuyer() {
        System.out.println("Please enter the name of the buyer: ");
        String name;
        do {
            name = reader.next();
            if (manager.buyerExists(name)) {
                break;
            } else {
                System.out.println("This buyer doesn't exist, please choose another: ");
            }
        } while (true);
        return manager.getBuyer(name);
    }

    private static boolean chooseProduct(Seller seller, String productName) {
        for (Product p : seller.getProducts()) {
            if (p.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public static Category selectCategory() {
        System.out.println("Select a category:");
        for (Category category : Category.values()) {
            System.out.println((category.ordinal() + 1) + ") " + category);
        }
        int categoryIndex = 0;
        boolean acceptableAnswer = false;
        while (!acceptableAnswer) {
            try {
                categoryIndex = reader.nextInt();
                while (categoryIndex < 1 || categoryIndex > 4) {
                    System.out.println("The input should be between 1-4, please select again:");
                    categoryIndex = reader.nextInt();
                }
                acceptableAnswer = true;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
        return Category.values()[categoryIndex - 1];
    }

    private static void printByCategory() {
        Product[] products = manager.getItemsByCategory(selectCategory());
        for (int i = 1; i < products.length + 1; i++) {
            System.out.println(i + ") " + products[i - 1].getName());
        }
    }

    private static void createCartFromHistory() {
        if (!manager.areThereBuyers()) {
            System.out.println("There are no buyers yet.");
            return;
        }
        Buyer buyer = chooseBuyer();
        while (!buyer.hasPrevOrders()) {
            System.out.println("This buyer doesn't have previous orders, do you want to choose another buyer? (Y/N)");
            String answer = reader.next();
            while (!answer.equals("Y") && !answer.equals("N")) {
                System.out.println("Please enter \"Y\" or \"N\":");
                answer = reader.next();
            }
            if (answer.equals("N")) {
                return;
            }
            buyer = chooseBuyer();
        }
        if (!buyer.getShoppingCart().isEmpty()) {
            System.out.println("The selected buyer already has a cart, do you wish to replace it with a previous order? (Y/N)");
            String answer = reader.next();
            while (!answer.equals("Y") && !answer.equals("N")) {
                System.out.println("Please enter \"Y\" or \"N\":");
                answer = reader.next();
            }
            if (answer.equals("N")) {
                return;
            }
        }
        int numOfOrders = buyer.printOrderHistory();
        System.out.println("Please select an order to be the buyer's new cart, if you wish to cancel enter \"0\":");
        boolean acceptableAnswer = false;
        while (!acceptableAnswer) {
            try {
                int orderNum = reader.nextInt();
                while (orderNum < 0 || orderNum > numOfOrders) {
                    System.out.println("This option is not available, please choose from the options above:");
                    orderNum = reader.nextInt();
                }
                if (orderNum == 0) {
                    return;
                }
                buyer.setCart(buyer.getPrevOrder(orderNum - 1));
                System.out.println("Buyer's cart has been updated");
                acceptableAnswer = true;
            } catch (InputMismatchException e) {
                System.out.println("Input must be an integer, please enter an answer with the correct type:");
                reader.nextLine();
            }
        }
    }

    private static void printNames() {
        ArrayList<String> names = manager.getArrayNames();
        System.out.println("\nBuyer Names:\n");
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("\n");
    }

    private static void printNamesNoDoubles() {
        HashMap<String, Integer> names = manager.getLinkedHashmapNames();
        System.out.println("\nBuyer Names:\n");
        for (Map.Entry<String, Integer> entry : names.entrySet()) {
            System.out.println(entry.getKey() + "......" + entry.getValue());
        }
        System.out.println("\n");
    }

    private static void printNameOccurrences() {
        System.out.print("\nPlease enter the string: ");
        String name = reader.next().toLowerCase();
        HashMap<String, Integer> names = manager.getLinkedHashmapNames();
        if (names.containsKey(name)) {
            System.out.println("The number of times '" + name + "' appears in the original array is " + names.get(name) + ".\n");
        } else {
            System.out.println("'" + name + "' does not appear in the original array.");
        }
    }

    private static void printReversedDoubles() {
        HashMap<String, Integer> names = manager.getLinkedHashmapNames(); // The names we saved without duplicates
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

    private static void printNamesSortedByLength() {
        TreeSet<Buyer> tree = manager.getBuyerTreeSet();
        for (Buyer buyer : tree) {
            System.out.println(buyer.getName().toUpperCase());
        }
    }

}



