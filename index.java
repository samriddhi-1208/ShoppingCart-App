import java.util.*;

interface Payment {
    void pay(double amount);
}

class Product {
    private String name;
    private double price;
    private int quantity;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    
    public double getTotalPrice() {
        return price * quantity;
    }
}

abstract class Discount {
    abstract double applyDiscount(List<Product> products);
}

class FestiveDiscount extends Discount {
  
    @Override
    double applyDiscount(List<Product> products) {
        double total = 0;
        for(Product p : products) {
            total += p.getTotalPrice();
        }
        return total * 0.9; 
    }
}

class BulkDiscount extends Discount {

    @Override
    double applyDiscount(List<Product> products) {
        double total = 0;
        for(Product p : products) {
            double price = p.getTotalPrice();
            if(p.getQuantity() > 5) {
                price *= 0.8;  
            }
            total += price;
        }
        return total;
    }
}

class PaymentImpl implements Payment {
    @Override
    public void pay(double amount) {
        System.out.printf("Total Amount Payable: %.2f\n", amount);
    }
}

public class ShoppingCart {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = Integer.parseInt(sc.nextLine());
        List<Product> products = new ArrayList<>();
        
        for(int i=0; i<N; i++) {
            String[] parts = sc.nextLine().split("\\s+");
            String name = parts[0];
            double price = Double.parseDouble(parts[1]);
            int quantity = Integer.parseInt(parts[2]);
            products.add(new Product(name, price, quantity));
        }
        
        String discountType = sc.nextLine().trim().toLowerCase();
        
        Discount discount;
        if(discountType.equals("festive")) {
            discount = new FestiveDiscount();
        } else if(discountType.equals("bulk")) {
            discount = new BulkDiscount();
        } else {
            System.out.println("Invalid discount type");
            sc.close();
            return;
        }
        
        for(Product p : products) {
            System.out.printf("Product: %s, Price: %.2f, Quantity: %d\n",
                              p.getName(), p.getPrice(), p.getQuantity());
        }
        
        double finalAmount = discount.applyDiscount(products);
        
        Payment payment = new PaymentImpl();
        payment.pay(finalAmount);
        
        sc.close();
    }
}
