import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void checkout(Customer customer, Cart cart) {
        if (cart.getCartItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty.");
        }

        double balance = customer.getBalance();
        double subTotal = cart.getTotalPrice();
        double shippingFees = 0.0;

        // Check stock and expiration
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            if (cartItem.getQuantity() > product.getQuantity()) {
                throw new IllegalArgumentException(product.getName() + " quantity exceeds available stock.");
            }
            if (product.isExpired()) {
                throw new IllegalArgumentException(product.getName() + " product is expired.");
            }
        }

        // Get shippable products
        List<CartItem> shippableProducts = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().isShippable()) {
                shippableProducts.add(cartItem);
            }
        }

        ShippingService shippingService = new ShippingService(shippableProducts);
        if (!shippableProducts.isEmpty()) {
            shippingFees = shippingService.getShippingFees();
        }

        double totalAmount = subTotal + shippingFees;
        if (totalAmount > balance) {
            throw new IllegalStateException("Customer's balance is insufficient.");
        }

        if (!shippableProducts.isEmpty()) {
            shippingService.printShippingNotice();
            System.out.println();
        }

        // Print receipt
        System.out.println("** Checkout receipt **");
        List<String> names = new ArrayList<>();
        List<String> prices = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            double totalProductPrice = cartItem.getQuantity() * product.getPrice();
            String name = cartItem.getQuantity() + "x " + product.getName();
            String price = String.format("%.2f", totalProductPrice);
            names.add(name);
            prices.add(price);
        }

        int maxNameWidth = names.stream().mapToInt(String::length).max().orElse(0);
        int maxPriceWidth = prices.stream().mapToInt(String::length).max().orElse(0);

        for (int i = 0; i < names.size(); i++) {
            System.out.printf("%-" + (maxNameWidth + 2) + "s %" + maxPriceWidth + "s\n",
                    names.get(i), prices.get(i));
        }

        System.out.println("----------------------------");
        System.out.printf("%-" + (maxNameWidth + 2) + "s %" + maxPriceWidth + ".2f\n", "Subtotal", subTotal);
        System.out.printf("%-" + (maxNameWidth + 2) + "s %" + maxPriceWidth + ".2f\n", "Shipping", shippingFees);
        System.out.printf("%-" + (maxNameWidth + 2) + "s %" + maxPriceWidth + ".2f\n", "Amount", totalAmount);

        // Update customer balance
        customer.setBalance(balance - totalAmount);
        System.out.printf("%-" + (maxNameWidth + 2) + "s %.2f\n", "Remaining balance", customer.getBalance());

        // Update stock
        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
        }
    }

    public static void main(String[] args) {
        // SECTION 1 - Success Case
        try {
            System.out.println("===== SECTION 1: Success Checkout =====");
            Customer customer1 = new Customer("Ahmed", 1000);
            Cart cart1 = new Cart();

            Product cheese = new Product.ProductBuilder("Cheese", 100, 10)
                    .setWeight(200.0)
                    .setExpiration(false)
                    .build();

            Product biscuits = new Product.ProductBuilder("Biscuits", 150, 5)
                    .setWeight(700.0)
                    .setExpiration(false)
                    .build();

            cart1.add(cheese, 2);
            cart1.add(biscuits, 1);

            checkout(customer1, cart1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // SECTION 2 - Product Expired
        try {
            System.out.println("===== SECTION 2: Product Expired =====");
            Customer customer2 = new Customer("Mona", 1000);
            Cart cart2 = new Cart();

            Product expiredMilk = new Product.ProductBuilder("Milk", 80, 10)
                    .setWeight(1000.0)
                    .setExpiration(true) // Expired = true
                    .build();

            cart2.add(expiredMilk, 1);

            checkout(customer2, cart2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // SECTION 3 - Quantity exceeds stock
        try {
            System.out.println("===== SECTION 3: Exceeds Quantity =====");
            Customer customer3 = new Customer("Youssef", 1000);
            Cart cart3 = new Cart();

            Product tv = new Product.ProductBuilder("TV", 300, 1)
                    .setWeight(10000.0)
                    .build();

            cart3.add(tv, 2); // Only 1 in stock

            checkout(customer3, cart3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // SECTION 4 - Insufficient Balance
        try {
            System.out.println("===== SECTION 4: Insufficient Balance =====");
            Customer customer4 = new Customer("Ali", 900); // Low balance
            Cart cart4 = new Cart();

            Product laptop = new Product.ProductBuilder("Laptop", 900, 3)
                    .setWeight(2000.0)
                    .build();

            cart4.add(laptop, 1); // 900

            checkout(customer4, cart4);
            // Put shipping fees in consideration,
            // so to make the payment successful customer balance should be at least 930.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // SECTION 5 - Empty Cart
        try {
            System.out.println("===== SECTION 5: Empty Cart =====");
            Customer customer5 = new Customer("Nada", 500);
            Cart cart5 = new Cart();

            checkout(customer5, cart5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println();

        // SECTION 6 - Non-shippable product
        try {
            System.out.println("===== SECTION 6: No Shipping Required =====");
            Customer customer6 = new Customer("Ziad", 500);
            Cart cart6 = new Cart();

            Product scratchCard = new Product.ProductBuilder("Mobile Scratch Card", 50, 10)
                    .build(); // no weight => not shippable

            cart6.add(scratchCard, 2); // 100

            checkout(customer6, cart6);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
