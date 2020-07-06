package com.dunzo.model.ingredient;

import java.util.Objects;

public class Quantity {

    private final double value;
    private final QuantityUnit quantityUnit;

    public Quantity(double value, QuantityUnit quantityUnit) {
        this.value = value;
        this.quantityUnit = quantityUnit;
    }

    public Quantity add(Quantity quantity) throws Exception {

        if (quantity.quantityUnit != this.quantityUnit) {
            throw new Exception("Cant add things of different unit");
        }

        return new Quantity(this.value + quantity.value, this.quantityUnit);
    }

    public Quantity subtract(Quantity quantity) throws Exception {

        if (quantity.quantityUnit != this.quantityUnit) {
            throw new Exception("Cant subtract things of different unit");
        }

        return new Quantity(Math.abs(this.value - quantity.value), this.quantityUnit);
    }

    public boolean isGreater(Quantity quantity) throws Exception {
        if (quantity.quantityUnit != this.quantityUnit) {
            throw new Exception("Cant subtract things of different unit");
        }

        return this.value > quantity.value;
    }

    public double getValue() {
        return value;
    }

    public QuantityUnit getQuantityUnit() {
        return quantityUnit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Double.compare(quantity.value, value) == 0 &&
                quantityUnit == quantity.quantityUnit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, quantityUnit);
    }
}
