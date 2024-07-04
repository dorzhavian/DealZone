# Buy-Sell

Buy-Sell is a Java-based e-commerce platform designed to simulate the functionality of online marketplaces like Amazon and eBay. This project was developed as part of an object-oriented programming course.

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
- **Product Management**: Sellers can add products, and buyers can add products to their cart.
- **Order Processing**: Buyers can place orders, and the system maintains order history.
- **Modular Design**: Designed with modularity and readability in mind.

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
    - `Main.java` - Entry point of the application.
    - `Buyer.java` - Handles buyer-specific functionality.
    - `Seller.java` - Handles seller-specific functionality.
    - `Product.java` - Manages product-related attributes and methods.
    - `Cart.java` - Handles order processing, cart-specific functionality.
    - `Manager.java` - Central class for managing the system.
- `resources/` - Resource files for the project.
- `docs/` - Documentation related files.

## Contributing
Contributions are welcome! Please submit a pull request and ensure your code adheres to the coding standards.

## License
This project is licensed under the MIT License. See the LICENSE file for more details.