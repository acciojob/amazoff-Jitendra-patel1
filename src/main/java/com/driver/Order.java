package com.driver;



import java.util.Arrays;
import java.util.List;


public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
      this.id=id;
      this.deliveryTime=convertTime(deliveryTime);
        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public void setId(String id) {
        this.id = id;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime){
        this.deliveryTime=convertTime(deliveryTime);
    }

    public static  int convertTime(String deliveryTime){
        List<String> list =Arrays.asList(deliveryTime.split(":")); //convert array to arraylist;
        int HH=Integer.parseInt(list.get((0)));
        int MM=Integer.parseInt(list.get(1));
        return HH*60+MM;
    }
    //you also get delverytime as string because you give delivery time as string


}
