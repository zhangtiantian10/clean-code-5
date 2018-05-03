package practice2;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Receipt {

    public Receipt() {
        tax = new BigDecimal(0.1);
        tax = tax.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private BigDecimal tax;

    public double CalculateGrandTotal(List<Product> products, List<OrderItem> items) {
        BigDecimal subTotal = calculateSubtotal(products, items);

        subTotal = products.stream()
            .map(product -> getReducePrice(items, product))
            .reduce(subTotal, BigDecimal::subtract);

        BigDecimal taxTotal = subTotal.multiply(tax);
        BigDecimal grandTotal = subTotal.add(taxTotal);

        return grandTotal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    private BigDecimal getReducePrice(List<OrderItem> items, Product product) {
        OrderItem curItem = findOrderItemByProduct(items, product);

        return product.getPrice()
            .multiply(product.getDiscountRate())
            .multiply(new BigDecimal(curItem.getCount()));
    }


    private OrderItem findOrderItemByProduct(List<OrderItem> items, Product product) {
        OrderItem curItem = null;

        Optional<OrderItem> optional = items.stream()
            .filter(orderItem -> orderItem.getCode() == product.getCode())
            .findFirst();

        if (optional.isPresent()) {
            curItem = optional.get();
        }

        return curItem;
    }

    private BigDecimal calculateSubtotal(List<Product> products, List<OrderItem> items) {

        return products.stream()
            .map(product -> getItemPrice(items, product))
            .reduce(BigDecimal::add).get();
    }

    private BigDecimal getItemPrice(List<OrderItem> items, Product product) {
        OrderItem item = findOrderItemByProduct(items, product);
        return product.getPrice().multiply(new BigDecimal(item.getCount()));
    }
}
