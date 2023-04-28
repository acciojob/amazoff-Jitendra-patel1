package com.driver;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

public class DeliveryPartner {

    private String id;
    private int numberOfOrders;

    public DeliveryPartner(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Integer getNumberOfOrders(){
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public void setId(String id) {
        this.id = id;
    }
}