import java.util.*;

public class Manager {
    private Seller[] sellers;
    private int numOfSellers;
    private Buyer[] buyers;
    private int numOfBuyers;

    public Manager() {
        sellers = new Seller[0];
        numOfSellers = 0;
        buyers = new Buyer[0];
        numOfBuyers = 0;
    }

    public boolean sellerExists(String name) {
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean buyerExists(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean productExists(Seller seller, String productName) {
        for (Product p : seller.getProducts()) {
            if (p.getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExisting(String name) {
        return (sellerExists(name) || buyerExists(name));
    }

    public void addSeller(Seller seller) {
        expandSellers();
        sellers[numOfSellers++] = seller;
    }

    public Seller getSeller(String name) {
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i].getName().equals(name)) {
                return sellers[i];
            }
        }
        return null;
    }

    public void addBuyer(Buyer buyer) {
        expandBuyers();
        buyers[numOfBuyers++] = buyer;
    }

    public Buyer getBuyer(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return buyers[i];
            }
        }
        return null;
    }

    public void printBuyers() {
        for (int i = 0; i < numOfBuyers; i++) {
            System.out.println(buyers[i] + "\n");
        }
    }

    public void printSellers() {
        for (int i = 0; i < numOfSellers; i++) {
            System.out.println(sellers[i]);
            sellers[i].printProducts();
            System.out.println();
        }
    }

    public boolean areThereBuyers() {
        return numOfBuyers > 0;
    }

    public boolean areThereSellers() {
        return numOfSellers > 0;
    }

    public void expandSellers() {
        if (sellers.length == 0) {
            sellers = new Seller[1];
        } else if (sellers.length == numOfSellers) {
            sellers = Arrays.copyOf(sellers, sellers.length * 2);
        }
    }

    public void expandBuyers() {
        if (buyers.length == 0) {
            buyers = new Buyer[1];
        } else if (buyers.length == numOfBuyers) {
            buyers = Arrays.copyOf(buyers, buyers.length * 2);
        }
    }

    public Product[] getItemsByCategory(Category c) {
        Product[] products = new Product[0];
        int count = 0;
        for (int i = 0; i < numOfSellers; i++) {
            if (sellers[i] != null) {
                for (int j = 0; j < sellers[i].getProducts().length; j++) {
                    Product p = sellers[i].getProducts()[j];
                    if (c.equals(p.getCategory())) {
                        if (products.length == 0) {
                            products = new Product[1];
                        } else if (products.length == count) {
                            products = Arrays.copyOf(products, products.length * 2);
                        }
                        products[count++] = p;
                    }
                }
            }
        }
        return products;
    }

    public ArrayList<String> getArrayNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Buyer b : buyers) {
            if (b != null) {
                names.add(b.getName());
            }
        }
        return names;
    }

    public LinkedHashMap<String, Integer> getLinkedHashmapNames() {
        LinkedHashMap<String, Integer> names = new LinkedHashMap<>();
        for (Buyer b : buyers) {
            if (b != null) {
                String name = b.getName().toLowerCase();
                if (names.containsKey(name)) {
                    names.put(name, names.get(name) + 1);
                } else
                    names.put(name, 1);
            }
        }
        return names;
    }

    public TreeSet<Buyer> getBuyerTreeSet() {
        TreeSet<Buyer> buyersTree = new TreeSet<>((b1, b2) -> {
            int x = b1.getName().length() - b2.getName().length();
            if (b1.getName().equalsIgnoreCase(b2.getName())) return 0;
            if (x == 0) return 1;
            return x;
        });
        for (Buyer b : buyers) {
            if (b != null) {

                buyersTree.add(b);
            }
        }
        return buyersTree;
    }
}
