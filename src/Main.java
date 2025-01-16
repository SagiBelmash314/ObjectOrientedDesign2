
import java.util.*;

public class Main {
    private static Scanner reader;
    private static MarketFacade market;


    public static void main(String[] args) {
        run();
        reader.close();
    }

    public static void run() {
        reader = new Scanner(System.in);
        market = MarketFacade.getInstance();
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
                    market.generateExamples();
                    break;
                case 0: // Exit
                    System.out.println("Goodbye!");
                    break;
                case 1: // Add seller
                    market.addSeller();
                    break;
                case 2: // Add buyer
                    market.addBuyer();
                    break;
                case 3: // Add product to seller
                    market.addProductToSeller();
                    break;
                case 4: // Add product to buyer
                    market.addProductBuyer();
                    break;
                case 5: // Order payment for buyer
                    market.orderPayment();
                    break;
                case 6: // Show details of all buyers
                    market.printBuyers();
                    break;
                case 7: // Show details of all sellers
                    market.printSellers();
                    break;
                case 8: // Print items of the same category
                    market.printByCategory();
                    break;
                case 9: // Re-create a buyer's previous cart
                    market.createCartFromHistory();
                    break;
                case 99: // Print buyers' names in the order of the buyers' array
                    market.printNames();
                    break;
                case 100: // Print buyers' names in the order of the buyers' array without doubles
                    market.printNamesNoDoubles();
                    break;
                case 101: // Print number of occurrences of a certain name in the buyers' array
                    market.printNameOccurrences(reader);
                    break;
                case 102: // print the array with double names in reverse
                    market.printReversedDoubles();
                    break;
                case 103: // Print the buyer's names sorted by name length
                    market.printNamesSortedByLength();
                    break;
                default:
                    System.out.println("Invalid option, please try again");
            }
        } while (input != 0);
    }
}
