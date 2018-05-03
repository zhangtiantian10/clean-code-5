package practice3;

import java.math.BigDecimal;
import java.util.List;

public class Order {

    private List<OrderLineItem> orderLineItemList;
    private List<BigDecimal> discounts;
    private BigDecimal tax;

    public Order(List<OrderLineItem> orderLineItemList, List<BigDecimal> discounts) {
        this.orderLineItemList = orderLineItemList;
        this.discounts = discounts;
        this.tax = new BigDecimal(0.1);
    }

    public BigDecimal calculate() {
        BigDecimal subTotal;

        subTotal = calculateSubTotal();

        subTotal = subTotal.subtract(calculateDiscounts());

        BigDecimal tax = subTotal.multiply(this.tax);

        return subTotal.add(tax);
    }

    private BigDecimal calculateDiscounts() {
        return discounts.stream()
            .reduce(BigDecimal::add).get();
    }

    private BigDecimal calculateSubTotal() {
        return orderLineItemList.stream()
            .map(orderLineItem -> orderLineItem.getPrice())
            .reduce(BigDecimal::add).get();
    }
}
