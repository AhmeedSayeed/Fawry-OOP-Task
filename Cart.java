import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Cart {
    private List<CartItem> cartItems = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException(product.getName() + " Requested quantity exceeds available stock.");
        }
        for (CartItem cartItem : cartItems) {
            if(cartItem.getProduct().equals(product)) {
                int newQuantity = cartItem.getQuantity() + quantity;
                if (newQuantity > product.getQuantity()) {
                    throw new IllegalArgumentException(product.getName() + " Requested quantity exceeds available stock.");
                }
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public void remove(Product product) {
        cartItems.removeIf(cartItem -> cartItem.getProduct().equals(product));
    }

    // Increase product quantity with some value (quantity parameter).
    public void increaseQuantity(Product product, int quantity) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().equals(product)) {
                int newQuantity = cartItem.getQuantity() + quantity;
                if (newQuantity > product.getQuantity()) {
                    throw new IllegalArgumentException(product.getName() + " Requested quantity exceeds available stock.");
                }
                cartItem.setQuantity(newQuantity);
                return;
            }
        }
        throw new IllegalArgumentException("Product not found in cart.");
    }

    // Decrease product quantity with some value (quantity parameter) and remove it safely by iterator if reached zero,
    // throw error if negative
    public void decreaseQuantity(Product product, int quantity) {
        Iterator<CartItem> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItem cartItem = iterator.next();
            if (cartItem.getProduct().equals(product)) {
                int currentQuantity = cartItem.getQuantity();
                int newQuantity = currentQuantity - quantity;
                if (newQuantity > 0) {
                    cartItem.setQuantity(newQuantity);
                }
                else if (newQuantity == 0) {
                    iterator.remove();
                }
                else {
                    throw new IllegalArgumentException("Cannot decrease more than the current quantity.");
                }
                return;
            }
        }
        throw new IllegalArgumentException(product.getName() + " Product not found in cart.");
    }


    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
}
