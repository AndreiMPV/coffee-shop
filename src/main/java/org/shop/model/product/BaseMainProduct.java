package org.shop.model.product;

import org.shop.model.bonus.BonusType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseMainProduct  {
    private final List<BaseExtraProduct> extraProducts = new ArrayList<>();
    private final String productName;
    private final ProductGroup productGroup;
    private final PortionSizeType portionSizeType;
    private final PortionVolumeType portionVolumeType;
    private final BigDecimal initialCost;

    private BonusType bonus;
    private BigDecimal discountAmount = BigDecimal.ZERO;

    public BaseMainProduct(String productName, ProductGroup productGroup,
                           PortionSizeType portionSizeType, PortionVolumeType portionVolumeType,
                           double initialCost) {
        this.productName = productName;
        this.productGroup = productGroup;
        this.initialCost = BigDecimal.valueOf(initialCost);
        this.portionSizeType = portionSizeType;
        this.portionVolumeType = portionVolumeType;
    }

    public PortionSizeType getPortionSizeType() {
        return portionSizeType;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(Optional.ofNullable(portionSizeType)
                        .map(PortionSizeType::name)
                        .map(String::toLowerCase).orElse(""))
                .append(productName)
                .append(" ")
                .append(Optional.ofNullable(portionVolumeType)
                        .map(PortionVolumeType::name)
                        .map(String::toLowerCase).orElse(""))
                .append(extraProducts.stream().map(BaseExtraProduct::getProductName)
                        .collect(Collectors.joining(" with ")));

        return sb.toString();
    }

    public void addExtraProduct(BaseExtraProduct extraProduct) {
        extraProducts.add(extraProduct);
    }

    public List<BaseExtraProduct> getExtraProducts() {
        return extraProducts;
    }

    public enum PortionSizeType {
        SMALL, MEDIUM, LARGE;
    }

    public enum PortionVolumeType {
        L_0_25(0.25, "l");

        private double volume;
        private String measure;

        PortionVolumeType(double volume, String measure) {
            this.volume = volume;
            this.measure = measure;
        }

        @Override
        public String toString() {
            return this.volume + Optional.ofNullable(this.measure).orElse("");
        }
    }

}
