package xyz.v2my.easymodeling;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class OrderLine {

    private String id;

    private String sku;

    private BigDecimal unitPrice;

    private BigDecimal amount;

    private BigDecimal taxRate;

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
