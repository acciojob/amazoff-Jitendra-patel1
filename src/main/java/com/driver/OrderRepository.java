package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private Map<String, Order> orderMap = new HashMap<>(); //only access in repo class
    private Map<String, DeliveryPartner> partnerMap = new HashMap<>();
    private Map<String, String> pairwithorderandpartner = new HashMap<>();     // key : orderid , value : partnerid
    private Map<String, HashSet<String>> partnerToOrder = new HashMap<>();      // key : partnerid , value : HashSet<orderid>

    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void addPartner(DeliveryPartner partner) {
        partnerMap.put(partner.getId(), partner);
    }

    public Optional<Order> getOrderById(String orderId) {
        if (orderMap.containsKey(orderId)) {
            return Optional.of(orderMap.get(orderId));
        } else {
            return Optional.empty();
        }

    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if (partnerMap.containsKey(partnerId)) {
            return Optional.of(partnerMap.get(partnerId));
        } else {
            return Optional.empty();
        }
    }

    public void addOrderPartnerPair(String orderId, String partnerId) { //you also can add contion if order allready assign the don't assign them
        HashSet<String> currentOrder = new HashSet<>();
        if (partnerToOrder .containsKey(partnerId)) {
            currentOrder = partnerToOrder .get(partnerId);
        }
        currentOrder.add(orderId);
        partnerToOrder .put(partnerId, currentOrder);
        DeliveryPartner partner = partnerMap.get(partnerId);
        partner.setNumberOfOrders(currentOrder.size());

        pairwithorderandpartner.put(orderId, partnerId);
    }

    public Integer getOrdercountForPartner(String partnerId) {
        int count = 0;

        if (partnerToOrder .containsValue(partnerId)) {
            count = partnerToOrder .get(partnerId).size();
        }
        return count;
    }

    public List<String> getOrderList(String partnerId) {
        HashSet<String> orderList = null;
        if(partnerToOrder .containsKey(partnerId)){
            orderList=partnerToOrder .get(partnerId);
        }
        return new ArrayList<>(orderList);
    }

    public List<String> getAllList() {
        List<String> list = new ArrayList<>(orderMap.keySet());
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        Integer countOfOrders = 0;

        List<String> list = new ArrayList<>(orderMap.keySet());

        for(String st : list){
            if(!pairwithorderandpartner.containsKey(st))
                countOfOrders += 1;
        }

        return countOfOrders;
    }

    public Integer getCountLeftGivenTime(String time, String partnerId) {
        int countOrder=0;
        int total=Order.convertTime(partnerId);
        if(partnerToOrder .containsKey(partnerId)){
          HashSet<String> set = partnerToOrder .get(partnerId);
          for(String st:set){
              if(orderMap.containsKey(st)){
                  Order order =orderMap.get(st);
                  if(total<order.getDeliveryTime())
                      countOrder++;
              }
          }
        }
        return countOrder;
    }

    public String getLastDeliveryTime(String partnerId) {
        String countleftOrder=null;
        int delivery_time = 0;

        if(partnerMap.containsKey(partnerId))
        {
            HashSet<String> list = partnerToOrder.get(partnerId);

            for(String st : list)
            {
                if(orderMap.containsKey(st))
                {
                    Order order = orderMap.get(st);

                    if(delivery_time < order.getDeliveryTime())
                        delivery_time = order.getDeliveryTime();
                }
            }
        }
        StringBuilder str = new StringBuilder();

        int hr = delivery_time / 60;                 // calculate hour
        if(hr < 10)
            str.append(0).append(hr);
        else
            str.append(hr);

        str.append(":");

        int min = delivery_time - (hr*60);          // calculate minutes
        if(min < 10)
            str.append(0).append(min);
        else
            str.append(min);

//        str.append(min);

        return str.toString();
    }

    public void deletePartnerById(String partnerId) {
        HashSet<String> list = new HashSet<>();

        if(partnerToOrder.containsKey(partnerId))
        {
            list = partnerToOrder.get(partnerId);

            for (String st : list) {
//                orderMap.remove(st);

                if (pairwithorderandpartner .containsKey(st))
                    pairwithorderandpartner .remove(st);
            }

            partnerToOrder.remove(partnerId);
        }

        if(partnerMap.containsKey(partnerId)) {
            partnerMap.remove(partnerId);
        }
    }

    public void deleteOrderById(String orderId) {
        if(pairwithorderandpartner .containsKey(orderId))
        {
            String partnerId = pairwithorderandpartner .get(orderId);

            HashSet<String> list = partnerToOrder.get(partnerId);
            list.remove(orderId);
            partnerToOrder.put(partnerId,list);

            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(list.size());
        }

        if(orderMap.containsKey(orderId)) {
            orderMap.remove(orderId);
        }
    }
    }
