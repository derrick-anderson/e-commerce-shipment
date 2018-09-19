package com.solstice.ecommerceshipment.domain;

import com.fasterxml.jackson.annotation.JsonRawValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;
    private Long accountId;
    private Long addressId;
    private LocalDate shippedDate = LocalDate.now();
    private LocalDate deliveryDate;
    @Transient
    @JsonRawValue
    private String lineItems;

    public Shipment() {
    }

    public Shipment(Long accountId, Long addressId, LocalDate deliveryDate) {
        this.accountId = accountId;
        this.addressId = addressId;
        this.deliveryDate = deliveryDate;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getLineItems() {
        return lineItems;
    }

    public void setLineItems(String lineItems) {
        this.lineItems = lineItems;
    }
}
