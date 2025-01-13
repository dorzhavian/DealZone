package Models;
import Enums.Category;
import Managers.ManagerFacade;


public class Factory {

    // FACTORY FUNCTIONS FOR CREATE OBJECTS :
    // need also for cart ?

    public static Buyer createBuyer (String username, String password, Address address) {
            return new Buyer(username, password, address);
    }

    public static Seller createSeller (String username, String password) {
            return new Seller(username, password);
    }

    public static Product createProduct (String name, double price, Category category) {
            return new Product(name, price, category);
    }

    public static ProductSpecialPackage createProductSpecialPackage (String name, double price, Category category, double specialPackagePrice) {
            return new ProductSpecialPackage(name, price, category, specialPackagePrice);
    }

    public static Product createProductForBuyer(Product product){
        return new Product(product);
    }

    public static ProductSpecialPackage createProductSpecialPackageForBuyer(Product product, double specialPackagePrice){
        return new ProductSpecialPackage(product, specialPackagePrice);
    }

    public static Address createAddress (String street, String houseName, String city, String state) {
            return new Address(street, houseName, city, state);
    }


    public void hardcodedFactory(ManagerFacade managerFacade, int sellerIndex, int buyerIndex ) {

        // Adding 10 sellers with real names and realistic passwords
        managerFacade.addSeller("Jack", "J@ck2024!");
        managerFacade.addSeller("Dor", "DorPass@123");
        managerFacade.addSeller("Tal", "Tal2024Secure");
        managerFacade.addSeller("Maya", "Maya@Home2024");
        managerFacade.addSeller("Avi", "AviPassword#45");
        managerFacade.addSeller("Nina", "Nina@P@ssw0rd");
        managerFacade.addSeller("Lior", "LiorPass2024!");
        managerFacade.addSeller("Sara", "Sara_Secure2024");
        managerFacade.addSeller("Yoni", "Yoni2024!Pass");
        managerFacade.addSeller("Orly", "Orly_P@ss123");


        // adding buyers with addresses and passwords

        // Adding 10 buyers with usernames, realistic passwords, and addresses
        managerFacade.addBuyer("Jack", "J@ck2024!", createAddress("Main St", "123", "Los Angeles", "California"));
        managerFacade.addBuyer("Dor", "DorPass@123", createAddress("Oak Rd", "456", "San Francisco", "California"));
        managerFacade.addBuyer("Tal", "Tal2024Secure", createAddress("Pine Ave", "789", "New York", "New York"));
        managerFacade.addBuyer("Maya", "Maya@Home2024", createAddress("Maple St", "101", "Chicago", "Illinois"));
        managerFacade.addBuyer("Avi", "AviPassword#45", createAddress("Cedar Blvd", "202", "Houston", "Texas"));
        managerFacade.addBuyer("Nina", "Nina@P@ssw0rd", createAddress("Elm St", "303", "Miami", "Florida"));
        managerFacade.addBuyer("Lior", "LiorPass2024!", createAddress("Birch St", "404", "Phoenix", "Arizona"));
        managerFacade.addBuyer("Sara", "Sara_Secure2024", createAddress("Spruce Dr", "505", "Seattle", "Washington"));
        managerFacade.addBuyer("Yoni", "Yoni2024!Pass", createAddress("Cypress Way", "606", "Denver", "Colorado"));
        managerFacade.addBuyer("Orly", "Orly_P@ss123", createAddress("Willow Ln", "707", "Boston", "Massachusetts"));


        // adding products to sellers

        // Seller 0 products
        managerFacade.addProductSeller(sellerIndex, "TV", 325.00, Category.ELECTRONIC, 15.00);  // Special price 15
        managerFacade.addProductSeller(sellerIndex, "Shirt", 50.00, Category.CLOTHES, 0);        // Special price 0
        managerFacade.addProductSeller(sellerIndex, "Office Chair", 120.00, Category.OFFICE, 10.00); // Special price 10
        managerFacade.addProductSeller(sellerIndex, "Toy Car", 15.00, Category.CHILDREN, 0);     // Special price 0

// Seller 1 products
        managerFacade.addProductSeller(sellerIndex + 1, "Laptop", 950.00, Category.ELECTRONIC, 50.00); // Special price 50
        managerFacade.addProductSeller(sellerIndex + 1, "Jacket", 75.00, Category.CLOTHES, 0);       // Special price 0
        managerFacade.addProductSeller(sellerIndex + 1, "Desk Lamp", 45.00, Category.OFFICE, 5.00);  // Special price 5
        managerFacade.addProductSeller(sellerIndex + 1, "tv", 25.00, Category.CHILDREN, 10.00);  // Special price 10

// Seller 2 products (Same as Seller 0)
        managerFacade.addProductSeller(sellerIndex + 2, "TV", 330.00, Category.ELECTRONIC, 18.00);  // Special price 18 (Same as Seller 0 but with different price)
        managerFacade.addProductSeller(sellerIndex + 2, "HAt", 60.00, Category.CLOTHES, 0);         // Special price 0
        managerFacade.addProductSeller(sellerIndex + 2, "Printer", 150.00, Category.OFFICE, 0);      // Special price 0
        managerFacade.addProductSeller(sellerIndex + 2, "Doll", 20.00, Category.CHILDREN, 8.00);    // Special price 8

// Seller 3 products
        managerFacade.addProductSeller(sellerIndex + 3, "Headphones", 120.00, Category.ELECTRONIC, 0);   // Special price 0
        managerFacade.addProductSeller(sellerIndex + 3, "Sweater", 80.00, Category.CLOTHES, 15.00);    // Special price 15
        managerFacade.addProductSeller(sellerIndex + 3, "Desk Chair", 130.00, Category.OFFICE, 0);     // Special price 0
        managerFacade.addProductSeller(sellerIndex + 3, "Building Blocks", 30.00, Category.CHILDREN, 5.00); // Special price 5

// Seller 4 products (Same as Seller 1 and 9)
        managerFacade.addProductSeller(sellerIndex + 4, "Tablet", 400.00, Category.ELECTRONIC, 0);  // Special price 0
        managerFacade.addProductSeller(sellerIndex + 4, "Scarf", 35.00, Category.CLOTHES, 10.00);  // Special price 10
        managerFacade.addProductSeller(sellerIndex + 4, "Whiteboard", 60.00, Category.OFFICE, 12.00); // Special price 12
        managerFacade.addProductSeller(sellerIndex + 4, "Action Figure", 18.00, Category.CHILDREN, 0); // Special price 0

// Seller 5 products
        managerFacade.addProductSeller(sellerIndex + 5, "Smartwatch", 220.00, Category.ELECTRONIC, 30.00); // Special price 30
        managerFacade.addProductSeller(sellerIndex + 5, "headphones", 25.00, Category.CLOTHES, 5.00);        // Special price 5
        managerFacade.addProductSeller(sellerIndex + 5, "Office Organizer", 40.00, Category.OFFICE, 0);   // Special price 0
        managerFacade.addProductSeller(sellerIndex + 5, "Board Game", 28.00, Category.CHILDREN, 10.00);   // Special price 10

// Seller 6 products
        managerFacade.addProductSeller(sellerIndex + 6, "Speakers", 180.00, Category.ELECTRONIC, 40.00);  // Special price 40
        managerFacade.addProductSeller(sellerIndex + 6, "HAT", 65.00, Category.CLOTHES, 0);             // Special price 0
        managerFacade.addProductSeller(sellerIndex + 6, "Monitor", 250.00, Category.OFFICE, 20.00);       // Special price 20
        managerFacade.addProductSeller(sellerIndex + 6, "Headphones", 40.00, Category.CHILDREN, 15.00);     // Special price 15

// Seller 7 products (Same as Seller 8)
        managerFacade.addProductSeller(sellerIndex + 7, "Camera", 350.00, Category.ELECTRONIC, 0);  // Special price 0
        managerFacade.addProductSeller(sellerIndex + 7, "Hat", 20.00, Category.CLOTHES, 10.00);          // Special price 10
        managerFacade.addProductSeller(sellerIndex + 7, "Filing Cabinet", 100.00, Category.OFFICE, 0);    // Special price 0
        managerFacade.addProductSeller(sellerIndex + 7, "Toy Train", 18.00, Category.CHILDREN, 5.00);     // Special price 5

// Seller 8 products (Same as Seller 7)
        managerFacade.addProductSeller(sellerIndex + 8, "Camera", 500.00, Category.ELECTRONIC, 100.00);  // Special price 100
        managerFacade.addProductSeller(sellerIndex + 8, "Shoes", 90.00, Category.CLOTHES, 25.00);        // Special price 25
        managerFacade.addProductSeller(sellerIndex + 8, "Desk Organizer", 45.00, Category.OFFICE, 0);    // Special price 0
        managerFacade.addProductSeller(sellerIndex + 8, "Toy Helicopter", 35.00, Category.CHILDREN, 0);   // Special price 0

// Seller 9 products (Same as Seller 4)
        managerFacade.addProductSeller(sellerIndex + 9, "TV", 800.00, Category.ELECTRONIC, 50.00);  // Special price 50
        managerFacade.addProductSeller(sellerIndex + 9, "jeans", 40.00, Category.CLOTHES, 0);            // Special price 0
        managerFacade.addProductSeller(sellerIndex + 9, "Ergonomic Chair", 200.00, Category.OFFICE, 30.00); // Special price 30
        managerFacade.addProductSeller(sellerIndex + 9, "Kids' Book", 12.00, Category.CHILDREN, 0);       // Special price 0


        // adding products to carts

        // Buyer 0
        managerFacade.addProductBuyer(buyerIndex, sellerIndex, 0);  // Buyer 0 buys TV from Seller 0
        managerFacade.addProductBuyer(buyerIndex, sellerIndex + 1, 1);  // Buyer 0 buys Jacket from Seller 1

        // Buyer 1
        managerFacade.addProductBuyer(buyerIndex + 1, sellerIndex + 2, 2);  // Buyer 1 buys Printer from Seller 2
        managerFacade.addProductBuyer(buyerIndex + 1, sellerIndex + 3, 3);  // Buyer 1 buys Desk Chair from Seller 3
        managerFacade.addProductBuyer(buyerIndex + 1, sellerIndex + 4, 0);  // Buyer 1 buys Tablet from Seller 4

        // Buyer 2
        managerFacade.addProductBuyer(buyerIndex + 2, sellerIndex + 5, 1);  // Buyer 2 buys T-shirt from Seller 5
        managerFacade.addProductBuyer(buyerIndex + 2, sellerIndex + 6, 2);  // Buyer 2 buys Monitor from Seller 6

        // Buyer 3
        managerFacade.addProductBuyer(buyerIndex + 3, sellerIndex + 7, 0);  // Buyer 3 buys Game Console from Seller 7
        managerFacade.addProductBuyer(buyerIndex + 3, sellerIndex + 8, 1);  // Buyer 3 buys Shoes from Seller 8
        managerFacade.addProductBuyer(buyerIndex + 3, sellerIndex + 9, 2);  // Buyer 3 buys Ergonomic Chair from Seller 9

        // Buyer 4
        managerFacade.addProductBuyer(buyerIndex + 4, sellerIndex, 2);  // Buyer 4 buys Office Chair from Seller 0
        managerFacade.addProductBuyer(buyerIndex + 4, sellerIndex + 1, 0);  // Buyer 4 buys Laptop from Seller 1

        // Buyer 5
        managerFacade.addProductBuyer(buyerIndex + 5, sellerIndex + 2, 1);  // Buyer 5 buys Jeans from Seller 2
        managerFacade.addProductBuyer(buyerIndex + 5, sellerIndex + 3, 3);  // Buyer 5 buys Building Blocks from Seller 3

        // Buyer 6
        managerFacade.addProductBuyer(buyerIndex + 6, sellerIndex + 4, 2);  // Buyer 6 buys Whiteboard from Seller 4
        managerFacade.addProductBuyer(buyerIndex + 6, sellerIndex + 5, 0);  // Buyer 6 buys Smartwatch from Seller 5

        // Buyer 7
        managerFacade.addProductBuyer(buyerIndex + 7, sellerIndex + 6, 1);  // Buyer 7 buys Pants from Seller 6
        managerFacade.addProductBuyer(buyerIndex + 7, sellerIndex + 7, 0);  // Buyer 7 buys Hat from Seller 7
        managerFacade.addProductBuyer(buyerIndex + 7, sellerIndex + 8, 2);  // Buyer 7 buys Desk Organizer from Seller 8

        // Buyer 8
        managerFacade.addProductBuyer(buyerIndex + 8, sellerIndex + 9, 1);  // Buyer 8 buys Blouse from Seller 9
        managerFacade.addProductBuyer(buyerIndex + 8, sellerIndex, 3);  // Buyer 8 buys Toy Car from Seller 0

        // Buyer 9
        managerFacade.addProductBuyer(buyerIndex + 9, sellerIndex + 1, 2);  // Buyer 9 buys Desk Lamp from Seller 1
        managerFacade.addProductBuyer(buyerIndex + 9, sellerIndex + 4, 1);  // Buyer 9 buys Scarf from Seller 4
        managerFacade.addProductBuyer(buyerIndex + 9, sellerIndex + 9, 0);  // Buyer 9 buys Smart TV from Seller 9

    }
}
