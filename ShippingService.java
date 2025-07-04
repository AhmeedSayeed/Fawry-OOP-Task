import java.util.ArrayList;
import java.util.List;

public class ShippingService {
    private List<CartItem> products = new ArrayList<>();

    public ShippingService(List<CartItem> products) {
        this.products = products;
    }

    public void printShippingNotice() {
        System.out.println("** Shipping Notice **");
        double totalWeight = 0.0;
        List<String> names = new ArrayList<>();
        List<String> weights = new ArrayList<>();

        for (CartItem cartItem : products) {
            Product product = cartItem.getProduct();
            double totalProductWeight = cartItem.getQuantity() * product.getWeight();
            totalWeight += totalProductWeight;
            String name = cartItem.getQuantity() + "x " + product.getName();
            String weight = (totalProductWeight >= 1000) ?  totalProductWeight / 1000 + "kg" : totalProductWeight + "g";
            names.add(name);
            weights.add(weight);
        }
        int maxNameWidth = names.stream().mapToInt(String::length).max().orElse(0);

        for (int i = 0; i < products.size(); i++) {
            System.out.printf("%-" + (maxNameWidth + 2) + "s %s\n", names.get(i), weights.get(i));
        }
        String totalStr = (totalWeight >= 1000)
                ? String.format("%.1fkg", totalWeight / 1000)
                : String.format("%.0fg", totalWeight);

        System.out.printf("Total package weight  %s\n", totalStr);
    }
}
