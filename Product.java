import java.util.Optional;

public class Product implements Shippable {
    private String name;
    private double price;
    private int quantity;
    private Optional<Boolean> expired = Optional.empty();
    private Optional<Double> weight = Optional.empty();

    public Product(ProductBuilder builder) {
        this.name = builder.name;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.expired = builder.expired;
        this.weight = builder.weight;
    }

    public boolean isShippable() {
        return weight.isPresent();
    }

    public boolean isExpirable() {
        return expired.isPresent();
    }

    public boolean isExpired() {
        return expired.orElse(false);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getWeight() {
        return weight.orElseThrow(() -> new IllegalStateException("Product is not shippable"));
    }

    // Builder Pattern used to separate required and optional attributes,
    // making product creation cleaner and preventing unnecessary nulls in expiration and shipping (weight).
    public class ProductBuilder {
        private String name;
        private double price;
        private int quantity;
        private Optional<Boolean> expired = Optional.empty();
        private Optional<Double> weight = Optional.empty();

        public ProductBuilder(String name, double price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public ProductBuilder setWeight(Double weight) {
            this.weight = Optional.of(weight);
            return this;
        }

        public ProductBuilder setExpiration(Boolean isExpired) {
            this.expired = Optional.of(isExpired);
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}