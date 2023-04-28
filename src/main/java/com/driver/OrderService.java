package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        orderRepository.addPartner(partner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
       Optional<Order> orderopt= orderRepository.getOrderById(orderId);
       Optional<DeliveryPartner> partneropt= orderRepository.getPartnerById(partnerId);
       if(orderopt.isPresent() && partneropt.isPresent()){
           orderRepository.addOrderPartnerPair(orderId,partnerId);
       }
    }

    public Order getOrderById(String orderId) throws RuntimeException {
        Optional<Order> orderopt=orderRepository.getOrderById(orderId);
        if(orderopt.isPresent()){
            return orderopt.get();
        }
        else{
            throw new  RuntimeException();
        }
    }

    public DeliveryPartner getPartnerById(String partnerId)  throws RuntimeException{
        Optional<DeliveryPartner> deliveryopt = orderRepository.getPartnerById(partnerId);
        if(deliveryopt.isPresent()){
            return deliveryopt.get();
        }
        else {
            throw new RuntimeException();
        }
    }

    public Integer getOrdercountForPartner(String partnerId) {

        Integer count=orderRepository.getOrdercountForPartner(partnerId);
        return count;

    }

    public List<String> getOrderList(String partnerId) {
        List<String> list =orderRepository.getOrderList(partnerId);
        return list;
    }

    public List<String> getAllList() {
        List<String> list =orderRepository.getAllList();
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer count=orderRepository.getCountOfUnassignedOrders();
        return count;
    }

    public Integer getCountLeftGivenTime(String time, String partnerId) {
        Integer count=orderRepository.getCountLeftGivenTime(time,partnerId);
        return count;
    }

    public String getLastDeliveryTime(String partnerId) {
        String count=orderRepository.getLastDeliveryTime(partnerId);
        return count;
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);

    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
