# Buy-Sell

Buy-Sell is a Java-based e-commerce platform designed to simulate the functionality of online marketplaces. This project was developed as part of an object-oriented programming course.

## Table of Contents
- [Description](#description)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## Description
The Buy-Sell platform allows users to register as buyers or sellers, manage products, and process orders. It emphasizes a modular and maintainable code structure using object-oriented principles.

## Features
- **User Management**: Handle registration and management of buyers and sellers.
- **Product Management**: Sellers can add products under categories (CHILDREN, ELECTRONIC, OFFICE, CLOTHES) and set special packaging for products.
- **Order Processing**: Buyers can add products to their cart, make payments, and view order history.
- **Exception Handling**: Robust handling for invalid usernames, passwords, and other exceptions.
- **Shopping Cart**: Each buyer has a current shopping cart and a history of past carts.
- **Detailed Menu**: Interactive menu with the following options:
  - `0) Exit`
  - `1) Add seller`
  - `2) Add buyer`
  - `3) Add item for seller`
  - `4) Add item for buyer`
  - `5) Payment for buyer`
  - `6) Buyer's details`
  - `7) Seller's details`
  - `8) Products by category`
  - `9) Replace current cart with cart from history`

## Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/dorzhavian/Buy-Sell.git
    ```
2. Open the project in IntelliJ IDEA or your preferred Java IDE.

## Usage
1. Run the `Main` class to start the application.
2. Use the provided menu to navigate through the features, such as user registration, product management, and order placement.

## Project Structure
- `src/` - Contains all source code.
  - `Main.java` - Entry point of the application, contains the main menu logic.
  - `Buyer.java` - Handles buyer-specific functionality.
  - `Seller.java` - Handles seller-specific functionality.
  - `User.java` - Abstract class that `Buyer` and `Seller` extend from.
  - `Product.java` - Manages product-related attributes and methods.
  - `Manager.java` - Central class for managing the system, implements `Manageable` interface.
  - `Manageable.java` - Interface defining management operations.
  - `Compare.java` - Utility class for comparison operations.
  - `Category.java` - Enum defining product categories.
- `resources/` - Resource files for the project.
- `docs/` - Documentation related files.

## Contributing
Contributions are welcome! Please submit a pull request and ensure your code adheres to the coding standards.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.
