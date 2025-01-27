import java.util.*;

public class BuyerManager {
    private Buyer[] buyers;
    private int numOfBuyers;
    private LinkedHashSet<Observer> observers;

    public BuyerManager() {
        buyers = new Buyer[0];
        numOfBuyers = 0;
        observers = new LinkedHashSet<>();
        attachObservers();
    }

    private void attachObservers() {
        observers.add(new Action1());
        observers.add(new Action2());
    }

    public boolean areThereBuyers() {
        return numOfBuyers > 0;
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


    public boolean buyerExists(String name) {
        for (int i = 0; i < numOfBuyers; i++) {
            if (buyers[i].getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addBuyer(Buyer buyer) {
        expandBuyers();
        buyers[numOfBuyers++] = buyer;
    }

    public void expandBuyers() {
        if (buyers.length == 0) {
            buyers = new Buyer[1];
        } else if (buyers.length == numOfBuyers) {
            buyers = Arrays.copyOf(buyers, buyers.length * 2);
        }
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

    public Iterator<?> iterator() {
        return new ConcreteIterator();
    }

    private class ConcreteIterator implements Iterator<Buyer> {
        int curr = 0;

        @Override
        public boolean hasNext() {
            if (numOfBuyers > curr) {
                return true;
            }
            for (Observer o : observers) {
                if (this instanceof ListIterator) {
                    o.update("My ListIterator ended!");
                }
                else o.update("My Iterator ended!");
            }
            return false;
        }

        @Override
        public Buyer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return buyers[curr++];
        }
    }

    public ListIterator<?> listIterator() {
        return new ConcreteListIterator();
    }

    private class ConcreteListIterator extends ConcreteIterator implements ListIterator<Buyer> {

        @Override
        public boolean hasPrevious() {
            if (curr > 0)
                return true;
            for (Observer o : observers) {
                o.update("My ListIterator ended");
            }
            return false;
        }

        @Override
        public Buyer previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            curr--;
            return buyers[curr];
        }

        @Override
        public int nextIndex() {
            return curr + 1;
        }

        @Override
        public int previousIndex() {
            return curr - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(Buyer buyer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(Buyer buyer) {
            throw new UnsupportedOperationException();
        }
    }

}
