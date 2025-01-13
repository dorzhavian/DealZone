# README 2.0 (OOD Assignments)

## Overview
This project manages products in a marketplace using various features implemented in Java, you can read the main README for more details about this project that create for OOP course. This README for Assignment 1 in OOD project.
## Features

### Array Management
- **Product Array**: All products are stored in an array in the `Manager` class (line 17).
    - `numOfProduct`: Integer representing the number of products.
    - `type`: String representing the name of each product.

### Menu Options Added in this assignment

#### Case 10: Automatic Object Tester
- **Input**: `10` in the menu (line 311 in `Main`).
- **Action**: Initializes an automatic tester using `initFactory(Manager managerFacade)` in the `Factory` class.
- **Objects Created**: Buyers, Sellers, Products.

#### Case 99: Print Product Names
- **Input**: `99` in the menu (line 317 in `Main`).
- **Action**: Prints all product names using the `printProductsName()` method (line 211 in `Manager`).

#### Case 100: Convert to Map
- **Input**: `100` in the menu (line 321 in `Main`).
- **Action**: Creates a map from the product array without duplicate names using the `productsToLinkedMap()` method (line 298 in `Manager`).
    - **Key**: Product name.
    - **Value**: Count of occurrences in the array.
- **Note**: Products must exist. Use Case 10 to add an automatic tester.

#### Case 101: Query Product Count
- **Input**: `101` in the menu (line 329 in `Main`).
- **Action**: Creates a map (as in Case 100) and takes user input to print how many times the input exists in the product array.

#### Case 102: Create and Modify Lists
- **Input**: `102` in the menu (line 339 in `Main`).
- **Action**:
    - Creates LinkedSet from products array with productsNameToLinkedSet() (line 326 in `Manager`).
    - Generates two `ArrayLists`: one from the map keys and another (`doubledNames`) with product names added twice.
    - Uses two `ListIterator` objects: one to add products and another to print the `doubledNames` list in reverse using `hasPrevious()`.

#### Case 103: Create TreeSet
- **Input**: `103` in the menu (line 357 in `Main`).
- **Action**:
    - Creates a `TreeSet` using the `productsToTree()` method (line 312 in `Manager`) with a lambda expression to compare product name lengths.
    - Prints the `TreeSet` using an iterator.

## Requirements
- Java Development Kit (JDK) version 11 or higher.
- IDE or text editor for Java development.

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/dorzhavian/DealZone.git
   ```
2. Open the project in your preferred Java IDE.
3. Compile the code:
   ```bash
   javac DealZone.java
   ```
4. Run the program:
   ```bash
   java Main
   ```

## Usage
- Follow the on-screen menu options to interact with the application.
- Input the desired case number to execute the corresponding functionality.

## License
This project is licensed under the [MIT License](LICENSE).

## Contributions
Contributions are welcome! Please fork the repository and create a pull request for review.

## Contact
For questions or support, contact dorzhavian@gmail.com / bananiasaf@gmail.com
