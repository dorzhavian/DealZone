package Models;
import Enums.Category;
import Managers.Manager;


public class Factory {

    public void initFactory(Manager manager, int sellerIndex, int buyerIndex ) {

        // Adding 10 sellers with real names and realistic passwords
        manager.addSeller("Jack", "J@ck2024!");
        manager.addSeller("Dor", "DorPass@123");
        manager.addSeller("Tal", "Tal2024Secure");
        manager.addSeller("Maya", "Maya@Home2024");
        manager.addSeller("Avi", "AviPassword#45");
        manager.addSeller("Nina", "Nina@P@ssw0rd");
        manager.addSeller("Lior", "LiorPass2024!");
        manager.addSeller("Sara", "Sara_Secure2024");
        manager.addSeller("Yoni", "Yoni2024!Pass");
        manager.addSeller("Orly", "Orly_P@ss123");

        // adding buyers with addresses and passwords

        // Adding 10 buyers with usernames, realistic passwords, and addresses
        manager.addBuyer("Jack", "J@ck2024!", new Address("Main St", "123", "Los Angeles", "California"));
        manager.addBuyer("Dor", "DorPass@123", new Address("Oak Rd", "456", "San Francisco", "California"));
        manager.addBuyer("Tal", "Tal2024Secure", new Address("Pine Ave", "789", "New York", "New York"));
        manager.addBuyer("Maya", "Maya@Home2024", new Address("Maple St", "101", "Chicago", "Illinois"));
        manager.addBuyer("Avi", "AviPassword#45", new Address("Cedar Blvd", "202", "Houston", "Texas"));
        manager.addBuyer("Nina", "Nina@P@ssw0rd", new Address("Elm St", "303", "Miami", "Florida"));
        manager.addBuyer("Lior", "LiorPass2024!", new Address("Birch St", "404", "Phoenix", "Arizona"));
        manager.addBuyer("Sara", "Sara_Secure2024", new Address("Spruce Dr", "505", "Seattle", "Washington"));
        manager.addBuyer("Yoni", "Yoni2024!Pass", new Address("Cypress Way", "606", "Denver", "Colorado"));
        manager.addBuyer("Orly", "Orly_P@ss123", new Address("Willow Ln", "707", "Boston", "Massachusetts"));


        // adding products to sellers

        // Seller 0 products
        manager.addProductSeller(sellerIndex, "TV", 325.00, Category.ELECTRONIC, 15.00);  // Special price 15
        manager.addProductSeller(sellerIndex, "Shirt", 50.00, Category.CLOTHES, 0);        // Special price 0
        manager.addProductSeller(sellerIndex, "Office Chair", 120.00, Category.OFFICE, 10.00); // Special price 10
        manager.addProductSeller(sellerIndex, "Toy Car", 15.00, Category.CHILDREN, 0);     // Special price 0

// Seller 1 products
        manager.addProductSeller(sellerIndex + 1, "Laptop", 950.00, Category.ELECTRONIC, 50.00); // Special price 50
        manager.addProductSeller(sellerIndex + 1, "Jacket", 75.00, Category.CLOTHES, 0);       // Special price 0
        manager.addProductSeller(sellerIndex + 1, "Desk Lamp", 45.00, Category.OFFICE, 5.00);  // Special price 5
        manager.addProductSeller(sellerIndex + 1, "Puzzle", 25.00, Category.CHILDREN, 10.00);  // Special price 10

// Seller 2 products (Same as Seller 0)
        manager.addProductSeller(sellerIndex + 2, "TV", 330.00, Category.ELECTRONIC, 18.00);  // Special price 18 (Same as Seller 0 but with different price)
        manager.addProductSeller(sellerIndex + 2, "Jeans", 60.00, Category.CLOTHES, 0);         // Special price 0
        manager.addProductSeller(sellerIndex + 2, "Printer", 150.00, Category.OFFICE, 0);      // Special price 0
        manager.addProductSeller(sellerIndex + 2, "Doll", 20.00, Category.CHILDREN, 8.00);    // Special price 8

// Seller 3 products
        manager.addProductSeller(sellerIndex + 3, "Headphones", 120.00, Category.ELECTRONIC, 0);   // Special price 0
        manager.addProductSeller(sellerIndex + 3, "Sweater", 80.00, Category.CLOTHES, 15.00);    // Special price 15
        manager.addProductSeller(sellerIndex + 3, "Desk Chair", 130.00, Category.OFFICE, 0);     // Special price 0
        manager.addProductSeller(sellerIndex + 3, "Building Blocks", 30.00, Category.CHILDREN, 5.00); // Special price 5

// Seller 4 products (Same as Seller 1 and 9)
        manager.addProductSeller(sellerIndex + 4, "Tablet", 400.00, Category.ELECTRONIC, 0);  // Special price 0
        manager.addProductSeller(sellerIndex + 4, "Scarf", 35.00, Category.CLOTHES, 10.00);  // Special price 10
        manager.addProductSeller(sellerIndex + 4, "Whiteboard", 60.00, Category.OFFICE, 12.00); // Special price 12
        manager.addProductSeller(sellerIndex + 4, "Action Figure", 18.00, Category.CHILDREN, 0); // Special price 0

// Seller 5 products
        manager.addProductSeller(sellerIndex + 5, "Smartwatch", 220.00, Category.ELECTRONIC, 30.00); // Special price 30
        manager.addProductSeller(sellerIndex + 5, "T-shirt", 25.00, Category.CLOTHES, 5.00);        // Special price 5
        manager.addProductSeller(sellerIndex + 5, "Office Organizer", 40.00, Category.OFFICE, 0);   // Special price 0
        manager.addProductSeller(sellerIndex + 5, "Board Game", 28.00, Category.CHILDREN, 10.00);   // Special price 10

// Seller 6 products
        manager.addProductSeller(sellerIndex + 6, "Speakers", 180.00, Category.ELECTRONIC, 40.00);  // Special price 40
        manager.addProductSeller(sellerIndex + 6, "Pants", 65.00, Category.CLOTHES, 0);             // Special price 0
        manager.addProductSeller(sellerIndex + 6, "Monitor", 250.00, Category.OFFICE, 20.00);       // Special price 20
        manager.addProductSeller(sellerIndex + 6, "Lego Set", 40.00, Category.CHILDREN, 15.00);     // Special price 15

// Seller 7 products (Same as Seller 8)
        manager.addProductSeller(sellerIndex + 7, "Game Console", 350.00, Category.ELECTRONIC, 0);  // Special price 0
        manager.addProductSeller(sellerIndex + 7, "Hat", 20.00, Category.CLOTHES, 10.00);          // Special price 10
        manager.addProductSeller(sellerIndex + 7, "Filing Cabinet", 100.00, Category.OFFICE, 0);    // Special price 0
        manager.addProductSeller(sellerIndex + 7, "Toy Train", 18.00, Category.CHILDREN, 5.00);     // Special price 5

// Seller 8 products (Same as Seller 7)
        manager.addProductSeller(sellerIndex + 8, "Camera", 500.00, Category.ELECTRONIC, 100.00);  // Special price 100
        manager.addProductSeller(sellerIndex + 8, "Shoes", 90.00, Category.CLOTHES, 25.00);        // Special price 25
        manager.addProductSeller(sellerIndex + 8, "Desk Organizer", 45.00, Category.OFFICE, 0);    // Special price 0
        manager.addProductSeller(sellerIndex + 8, "Toy Helicopter", 35.00, Category.CHILDREN, 0);   // Special price 0

// Seller 9 products (Same as Seller 4)
        manager.addProductSeller(sellerIndex + 9, "Smart TV", 800.00, Category.ELECTRONIC, 50.00);  // Special price 50
        manager.addProductSeller(sellerIndex + 9, "Blouse", 40.00, Category.CLOTHES, 0);            // Special price 0
        manager.addProductSeller(sellerIndex + 9, "Ergonomic Chair", 200.00, Category.OFFICE, 30.00); // Special price 30
        manager.addProductSeller(sellerIndex + 9, "Kids' Book", 12.00, Category.CHILDREN, 0);       // Special price 0


        // adding products to carts

        // Buyer 0
        manager.addProductBuyer(buyerIndex, sellerIndex, 0);  // Buyer 0 buys TV from Seller 0
        manager.addProductBuyer(buyerIndex, sellerIndex + 1, 1);  // Buyer 0 buys Jacket from Seller 1

        // Buyer 1
        manager.addProductBuyer(buyerIndex + 1, sellerIndex + 2, 2);  // Buyer 1 buys Printer from Seller 2
        manager.addProductBuyer(buyerIndex + 1, sellerIndex + 3, 3);  // Buyer 1 buys Desk Chair from Seller 3
        manager.addProductBuyer(buyerIndex + 1, sellerIndex + 4, 0);  // Buyer 1 buys Tablet from Seller 4

        // Buyer 2
        manager.addProductBuyer(buyerIndex + 2, sellerIndex + 5, 1);  // Buyer 2 buys T-shirt from Seller 5
        manager.addProductBuyer(buyerIndex + 2, sellerIndex + 6, 2);  // Buyer 2 buys Monitor from Seller 6

        // Buyer 3
        manager.addProductBuyer(buyerIndex + 3, sellerIndex + 7, 0);  // Buyer 3 buys Game Console from Seller 7
        manager.addProductBuyer(buyerIndex + 3, sellerIndex + 8, 1);  // Buyer 3 buys Shoes from Seller 8
        manager.addProductBuyer(buyerIndex + 3, sellerIndex + 9, 2);  // Buyer 3 buys Ergonomic Chair from Seller 9

        // Buyer 4
        manager.addProductBuyer(buyerIndex + 4, sellerIndex, 2);  // Buyer 4 buys Office Chair from Seller 0
        manager.addProductBuyer(buyerIndex + 4, sellerIndex + 1, 0);  // Buyer 4 buys Laptop from Seller 1

        // Buyer 5
        manager.addProductBuyer(buyerIndex + 5, sellerIndex + 2, 1);  // Buyer 5 buys Jeans from Seller 2
        manager.addProductBuyer(buyerIndex + 5, sellerIndex + 3, 3);  // Buyer 5 buys Building Blocks from Seller 3

        // Buyer 6
        manager.addProductBuyer(buyerIndex + 6, sellerIndex + 4, 2);  // Buyer 6 buys Whiteboard from Seller 4
        manager.addProductBuyer(buyerIndex + 6, sellerIndex + 5, 0);  // Buyer 6 buys Smartwatch from Seller 5

        // Buyer 7
        manager.addProductBuyer(buyerIndex + 7, sellerIndex + 6, 1);  // Buyer 7 buys Pants from Seller 6
        manager.addProductBuyer(buyerIndex + 7, sellerIndex + 7, 0);  // Buyer 7 buys Hat from Seller 7
        manager.addProductBuyer(buyerIndex + 7, sellerIndex + 8, 2);  // Buyer 7 buys Desk Organizer from Seller 8

        // Buyer 8
        manager.addProductBuyer(buyerIndex + 8, sellerIndex + 9, 1);  // Buyer 8 buys Blouse from Seller 9
        manager.addProductBuyer(buyerIndex + 8, sellerIndex, 3);  // Buyer 8 buys Toy Car from Seller 0

        // Buyer 9
        manager.addProductBuyer(buyerIndex + 9, sellerIndex + 1, 2);  // Buyer 9 buys Desk Lamp from Seller 1
        manager.addProductBuyer(buyerIndex + 9, sellerIndex + 4, 1);  // Buyer 9 buys Scarf from Seller 4
        manager.addProductBuyer(buyerIndex + 9, sellerIndex + 9, 0);  // Buyer 9 buys Smart TV from Seller 9

    }
}
